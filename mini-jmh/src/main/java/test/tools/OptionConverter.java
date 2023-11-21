package test.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 * A convenience class to convert property values to specific types.
 */
public final class OptionConverter {

  private static final String DELIM_START = "${";
  private static final char DELIM_STOP = '}';
  private static final int DELIM_START_LEN = 2;
  private static final int DELIM_STOP_LEN = 1;
  private static final int ONE_K = 1024;

  /**
   * OptionConverter is a static class.
   */
  private OptionConverter() {
  }

  public static String[] concatenateArrays(final String[] l, final String[] r) {
    final int len = l.length + r.length;
    final String[] a = new String[len];

    System.arraycopy(l, 0, a, 0, l.length);
    System.arraycopy(r, 0, a, l.length, r.length);

    return a;
  }

  public static String convertSpecialChars(final String s) {
    char c;
    final int len = s.length();
    final StringBuilder sbuf = new StringBuilder(len);

    int i = 0;
    while (i < len) {
      c = s.charAt(i++);
      if (c == '\\') {
        c = s.charAt(i++);
        switch (c) {
          case 'n':
            c = '\n';
            break;
          case 'r':
            c = '\r';
            break;
          case 't':
            c = '\t';
            break;
          case 'f':
            c = '\f';
            break;
          case 'b':
            c = '\b';
            break;
          case '"':
            c = '\"';
            break;
          case '\'':
            c = '\'';
            break;
          case '\\':
            c = '\\';
            break;
          default:
            // there is no default case.
        }
      }
      sbuf.append(c);
    }
    return sbuf.toString();
  }

  /**
   * If <code>value</code> is "true", then {@code true} is returned. If <code>value</code> is "false", then {@code false} is returned. Otherwise, <code>default</code> is returned.
   *
   * <p>Case of value is unimportant.
   *
   * @param value        The value to convert.
   * @param defaultValue The default value.
   * @return true or false, depending on the value and/or default.
   */
  public static boolean toBoolean(final String value, final boolean defaultValue) {
    if (value == null) {
      return defaultValue;
    }
    final String trimmedVal = value.trim();
    if ("true".equalsIgnoreCase(trimmedVal)) {
      return true;
    }
    if ("false".equalsIgnoreCase(trimmedVal)) {
      return false;
    }
    return defaultValue;
  }

  /**
   * Convert the String value to an int.
   *
   * @param value        The value as a String.
   * @param defaultValue The default value.
   * @return The value as an int.
   */
  public static int toInt(final String value, final int defaultValue) {
    if (value != null) {
      final String s = value;
      try {
        return Integer.parseInt(s);
      } catch (final NumberFormatException e) {
      }
    }
    return defaultValue;
  }

  /**
   * @param value        The size of the file as a String.
   * @param defaultValue The default value.
   * @return The size of the file as a long.
   */
  public static long toFileSize(final String value, final long defaultValue) {
    if (value == null) {
      return defaultValue;
    }

    String str = value.trim().toUpperCase(Locale.ENGLISH);
    long multiplier = 1;
    int index;

    if ((index = str.indexOf("KB")) != -1) {
      multiplier = ONE_K;
      str = str.substring(0, index);
    } else if ((index = str.indexOf("MB")) != -1) {
      multiplier = ONE_K * ONE_K;
      str = str.substring(0, index);
    } else if ((index = str.indexOf("GB")) != -1) {
      multiplier = ONE_K * ONE_K * ONE_K;
      str = str.substring(0, index);
    }
    try {
      return Long.parseLong(str) * multiplier;
    } catch (final NumberFormatException e) {
    }
    return defaultValue;
  }

  /**
   * Find the value corresponding to <code>key</code> in <code>props</code>. Then perform variable substitution on the found value.
   *
   * @param key   The key to locate.
   * @param props The properties.
   * @return The String after substitution.
   */
  public static String findAndSubst(final String key, final Properties props) {
    final String value = props.getProperty(key);
    if (value == null) {
      return null;
    }

    try {
      return substVars(value, props);
    } catch (final IllegalArgumentException e) {
      return value;
    }
  }

  /**
   * Perform variable substitution in string <code>val</code> from the values of keys found in the system propeties.
   *
   * <p>The variable substitution delimiters are <b>${</b> and <b>}</b>.
   *
   * <p>For example, if the System properties contains "key=value", then the call
   *
   * <pre>
   * String s = OptionConverter.substituteVars("Value of key is ${key}.");
   * </pre>
   *
   * <p>will set the variable <code>s</code> to "Value of key is value.".
   *
   * <p>If no value could be found for the specified key, then the <code>props</code> parameter is
   * searched, if the value could not be found there, then substitution defaults to the empty string.
   *
   * <p>For example, if system properties contains no value for the key "inexistentKey", then the
   * call
   *
   * <pre>
   * String s = OptionConverter.subsVars("Value of inexistentKey is [${inexistentKey}]");
   * </pre>
   *
   * <p>will set <code>s</code> to "Value of inexistentKey is []"
   *
   * <p>An {@link IllegalArgumentException} is thrown if <code>val</code> contains a start delimeter
   * "${" which is not balanced by a stop delimeter "}".
   *
   * @param val   The string on which variable substitution is performed.
   * @param props The properties to use for substitution.
   * @return The String after substitution.
   * @throws IllegalArgumentException if <code>val</code> is malformed.
   */
  public static String substVars(final String val, final Properties props) throws IllegalArgumentException {
    return substVars(val, props, new ArrayList<>());
  }

  private static String substVars(final String val, final Properties props, List<String> keys) throws IllegalArgumentException {

    final StringBuilder sbuf = new StringBuilder();

    int i = 0;
    int j;
    int k;

    while (true) {
      j = val.indexOf(DELIM_START, i);
      if (j == -1) {
        // no more variables
        if (i == 0) { // this is a simple string
          return val;
        }
        // add the tail string which contails no variables and return the result.
        sbuf.append(val.substring(i, val.length()));
        return sbuf.toString();
      }
      sbuf.append(val.substring(i, j));
      k = val.indexOf(DELIM_STOP, j);
      if (k == -1) {
        throw new IllegalArgumentException(" has no closing brace. Opening brace at position " + j + '.');
      }
      j += DELIM_START_LEN;
      final String key = val.substring(j, k);
      // first try in System properties
      String replacement = null; // PropertiesUtil.getProperties().getStringProperty(key, null);
      // then try props parameter
      if (replacement == null && props != null) {
        replacement = props.getProperty(key);
      }

      if (replacement != null) {

        // Do variable substitution on the replacement string
        // such that we can solve "Hello ${x2}" as "Hello p1"
        // the where the properties are
        // x1=p1
        // x2=${x1}
        if (!keys.contains(key)) {
          List<String> usedKeys = new ArrayList<>(keys);
          usedKeys.add(key);
          final String recursiveReplacement = substVars(replacement, props, usedKeys);
          sbuf.append(recursiveReplacement);
        } else {
          sbuf.append(replacement);
        }
      }
      i = k + DELIM_STOP_LEN;
    }
  }
}
