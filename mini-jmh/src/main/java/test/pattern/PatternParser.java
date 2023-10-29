/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache license, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the license for the specific language governing permissions and
 * limitations under the license.
 */
package test.pattern;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class PatternParser {
  static final String DISABLE_ANSI = "disableAnsi";
  static final String NO_CONSOLE_NO_ANSI = "noConsoleNoAnsi";

  private static final char ESCAPE_CHAR = '%';
  private static final int BUF_SIZE = 32;
  private static final int DECIMAL = 10;
  private final Map<String, Class<PatternConverter>> converterRules;

  public PatternParser(final String converterKey) {
    this(converterKey, null, null);
  }

  public PatternParser(final String converterKey, final Class<?> expected) {
    this(converterKey, expected, null);
  }

  public PatternParser(
      final String converterKey, final Class<?> expectedClass, final Class<?> filterClass) {
    final Map<String, Class<PatternConverter>> converters = new LinkedHashMap<>();
    Class datePatternConverterClass = DateTimeEncoder.class;
    converters.put("d", (Class<PatternConverter>) datePatternConverterClass);
    converters.put("date", (Class<PatternConverter>) datePatternConverterClass);
    Class loggerPatternConverterClass = LoggerPatternConverter.class;
    converters.put("c", (Class<PatternConverter>) loggerPatternConverterClass);
    converters.put("logger", (Class<PatternConverter>) loggerPatternConverterClass);

    Class threadNamePatternConverterClass = ThreadNamePatternConverter.class;
    converters.put("t", (Class<PatternConverter>) threadNamePatternConverterClass);
    converters.put("threadName", (Class<PatternConverter>) threadNamePatternConverterClass);

    Class levelPatternConverterClass = LevelPatternConverter.class;
    converters.put("p", (Class<PatternConverter>) levelPatternConverterClass);
    converters.put("level", (Class<PatternConverter>) levelPatternConverterClass);

    final Class lineSeparatorPatternConverter = LineSeparatorPatternConverter.class;
    converters.put("n", (Class<PatternConverter>) lineSeparatorPatternConverter);
    final Class messagePatternConverter = MessagePatternConverter.class;
    converters.put("m", (Class<PatternConverter>) messagePatternConverter);
    converters.put("msg", (Class<PatternConverter>) messagePatternConverter);
    converters.put("message", (Class<PatternConverter>) messagePatternConverter);

    converterRules = converters;
  }

  private static int extractConverter(
      final char lastChar,
      final String pattern,
      final int start,
      final StringBuilder convBuf,
      final StringBuilder currentLiteral) {
    int i = start;
    convBuf.setLength(0);

    // When this method is called, lastChar points to the first character of the
    // conversion word. For example:
    // For "%hello" lastChar = 'h'
    // For "%-5hello" lastChar = 'h'
    // System.out.println("lastchar is "+lastChar);
    if (!Character.isUnicodeIdentifierStart(lastChar)) {
      return i;
    }

    convBuf.append(lastChar);

    while (i < pattern.length() && Character.isUnicodeIdentifierPart(pattern.charAt(i))) {
      convBuf.append(pattern.charAt(i));
      currentLiteral.append(pattern.charAt(i));
      i++;
    }

    return i;
  }

  private static int extractOptions(
      final String pattern, final int start, final List<String> options) {
    int i = start;
    while (i < pattern.length() && pattern.charAt(i) == '{') {
      i++; // skip opening "{"
      final int begin = i; // position of first real char
      int depth = 1; // already inside one level
      while (depth > 0 && i < pattern.length()) {
        final char c = pattern.charAt(i);
        if (c == '{') {
          depth++;
        } else if (c == '}') {
          depth--;
          // TODO(?) maybe escaping of { and } with \ or %
        }
        i++;
      } // while

      if (depth > 0) { // option not closed, continue with pattern after closing bracket
        i = pattern.lastIndexOf('}');
        if (i == -1 || i < start) {
          // if no closing bracket could be found or there is no closing bracket behind the starting
          // character of our parsing process continue parsing after the first opening bracket
          return begin;
        }
        return i + 1;
      }

      options.add(pattern.substring(begin, i - 1));
    } // while

    return i;
  }

  private static boolean areValidNewInstanceParameters(Class<?>[] parameterTypes) {
    for (Class<?> clazz : parameterTypes) {
      /* if (!clazz.isAssignableFrom(Configuration.class)
          && !(clazz.isArray() && "[Ljava.lang.String;".equals(clazz.getName()))) {
        return false;
      }*/
    }
    return true;
  }

  public List<PatternFormatter> parse(final String pattern) {
    return parse(pattern, false, false, false);
  }

  public List<PatternFormatter> parse(
      final String pattern, final boolean alwaysWriteExceptions, final boolean noConsoleNoAnsi) {
    return parse(pattern, alwaysWriteExceptions, false, noConsoleNoAnsi);
  }

  public List<PatternFormatter> parse(
      final String pattern,
      final boolean alwaysWriteExceptions,
      final boolean disableAnsi,
      final boolean noConsoleNoAnsi) {
    final List<PatternFormatter> list = new ArrayList<>();
    final List<PatternConverter> converters = new ArrayList<>();
    final List<FormattingInfo> fields = new ArrayList<>();
    parse(pattern, converters, fields, disableAnsi, noConsoleNoAnsi, true);
    final Iterator<FormattingInfo> fieldIter = fields.iterator();
    boolean handlesThrowable = false;

    for (final PatternConverter converter : converters) {
      /*  if (converter instanceof NanoTimePatternConverter) {
        //
      }*/
      LogEventPatternConverter pc;
      if (converter instanceof LogEventPatternConverter) {
        pc = (LogEventPatternConverter) converter;
        handlesThrowable |= pc.handlesThrowable();
      } else {
        pc = SimpleLiteralPatternConverter.of("Strings.EMPTY");
      }

      FormattingInfo field;
      if (fieldIter.hasNext()) {
        field = fieldIter.next();
      } else {
        field = FormattingInfo.getDefault();
      }
      list.add(new PatternFormatter(pc, field));
    }
    if (alwaysWriteExceptions && !handlesThrowable) {
      /*final LogEventPatternConverter pc =
          ExtendedThrowablePatternConverter.newInstance(config, null);
      list.add(new PatternFormatter(pc, FormattingInfo.getDefault()));*/
    }
    return list;
  }

  public void parse(
      final String pattern,
      final List<PatternConverter> patternConverters,
      final List<FormattingInfo> formattingInfos,
      final boolean noConsoleNoAnsi,
      final boolean convertBackslashes) {
    parse(pattern, patternConverters, formattingInfos, false, noConsoleNoAnsi, convertBackslashes);
  }

  public void parse(
      final String pattern,
      final List<PatternConverter> patternConverters,
      final List<FormattingInfo> formattingInfos,
      final boolean disableAnsi,
      final boolean noConsoleNoAnsi,
      final boolean convertBackslashes) {
    Objects.requireNonNull(pattern, "pattern");

    final StringBuilder currentLiteral = new StringBuilder(BUF_SIZE);

    final int patternLength = pattern.length();
    ParserState state = ParserState.LITERAL_STATE;
    char c;
    int i = 0;
    FormattingInfo formattingInfo = FormattingInfo.getDefault();

    while (i < patternLength) {
      c = pattern.charAt(i++);

      switch (state) {
        case LITERAL_STATE:

          // In literal state, the last char is always a literal.
          if (i == patternLength) {
            currentLiteral.append(c);

            continue;
          }

          if (c == ESCAPE_CHAR) {
            // peek at the next char.
            switch (pattern.charAt(i)) {
              case ESCAPE_CHAR:
                currentLiteral.append(c);
                i++; // move pointer

                break;

              default:
                if (currentLiteral.length() != 0) {
                  patternConverters.add(
                      literalPattern(currentLiteral.toString(), convertBackslashes));
                  formattingInfos.add(FormattingInfo.getDefault());
                }

                currentLiteral.setLength(0);
                currentLiteral.append(c); // append %
                state = ParserState.CONVERTER_STATE;
                formattingInfo = FormattingInfo.getDefault();
            }
          } else {
            currentLiteral.append(c);
          }

          break;

        case CONVERTER_STATE:
          currentLiteral.append(c);

          switch (c) {
            case '0':
              // a '0' directly after the % sign indicates zero-padding
              formattingInfo =
                  new FormattingInfo(
                      formattingInfo.isLeftAligned(),
                      formattingInfo.getMinLength(),
                      formattingInfo.getMaxLength(),
                      formattingInfo.isLeftTruncate(),
                      true);
              break;

            case '-':
              formattingInfo =
                  new FormattingInfo(
                      true,
                      formattingInfo.getMinLength(),
                      formattingInfo.getMaxLength(),
                      formattingInfo.isLeftTruncate(),
                      formattingInfo.isZeroPad());
              break;

            case '.':
              state = ParserState.DOT_STATE;
              break;

            default:
              if (c >= '0' && c <= '9') {
                formattingInfo =
                    new FormattingInfo(
                        formattingInfo.isLeftAligned(),
                        c - '0',
                        formattingInfo.getMaxLength(),
                        formattingInfo.isLeftTruncate(),
                        formattingInfo.isZeroPad());
                state = ParserState.MIN_STATE;
              } else {
                i =
                    finalizeConverter(
                        c,
                        pattern,
                        i,
                        currentLiteral,
                        formattingInfo,
                        converterRules,
                        patternConverters,
                        formattingInfos,
                        disableAnsi,
                        noConsoleNoAnsi,
                        convertBackslashes);

                // Next pattern is assumed to be a literal.
                state = ParserState.LITERAL_STATE;
                formattingInfo = FormattingInfo.getDefault();
                currentLiteral.setLength(0);
              }
          } // switch

          break;

        case MIN_STATE:
          currentLiteral.append(c);

          if (c >= '0' && c <= '9') {
            // Multiply the existing value and add the value of the number just encountered.
            formattingInfo =
                new FormattingInfo(
                    formattingInfo.isLeftAligned(),
                    formattingInfo.getMinLength() * DECIMAL + c - '0',
                    formattingInfo.getMaxLength(),
                    formattingInfo.isLeftTruncate(),
                    formattingInfo.isZeroPad());
          } else if (c == '.') {
            state = ParserState.DOT_STATE;
          } else {
            i =
                finalizeConverter(
                    c,
                    pattern,
                    i,
                    currentLiteral,
                    formattingInfo,
                    converterRules,
                    patternConverters,
                    formattingInfos,
                    disableAnsi,
                    noConsoleNoAnsi,
                    convertBackslashes);
            state = ParserState.LITERAL_STATE;
            formattingInfo = FormattingInfo.getDefault();
            currentLiteral.setLength(0);
          }

          break;

        case DOT_STATE:
          currentLiteral.append(c);
          switch (c) {
            case '-':
              formattingInfo =
                  new FormattingInfo(
                      formattingInfo.isLeftAligned(),
                      formattingInfo.getMinLength(),
                      formattingInfo.getMaxLength(),
                      false,
                      formattingInfo.isZeroPad());
              break;

            default:
              if (c >= '0' && c <= '9') {
                formattingInfo =
                    new FormattingInfo(
                        formattingInfo.isLeftAligned(),
                        formattingInfo.getMinLength(),
                        c - '0',
                        formattingInfo.isLeftTruncate(),
                        formattingInfo.isZeroPad());
                state = ParserState.MAX_STATE;
              } else {
                state = ParserState.LITERAL_STATE;
              }
          }

          break;

        case MAX_STATE:
          currentLiteral.append(c);

          if (c >= '0' && c <= '9') {
            // Multiply the existing value and add the value of the number just encountered.
            formattingInfo =
                new FormattingInfo(
                    formattingInfo.isLeftAligned(),
                    formattingInfo.getMinLength(),
                    formattingInfo.getMaxLength() * DECIMAL + c - '0',
                    formattingInfo.isLeftTruncate(),
                    formattingInfo.isZeroPad());
          } else {
            i =
                finalizeConverter(
                    c,
                    pattern,
                    i,
                    currentLiteral,
                    formattingInfo,
                    converterRules,
                    patternConverters,
                    formattingInfos,
                    disableAnsi,
                    noConsoleNoAnsi,
                    convertBackslashes);
            state = ParserState.LITERAL_STATE;
            formattingInfo = FormattingInfo.getDefault();
            currentLiteral.setLength(0);
          }

          break;
      } // switch
    }

    // while
    if (currentLiteral.length() != 0) {
      patternConverters.add(literalPattern(currentLiteral.toString(), convertBackslashes));
      formattingInfos.add(FormattingInfo.getDefault());
    }
  }

  private PatternConverter createConverter(
      final String converterId,
      final StringBuilder currentLiteral,
      final Map<String, Class<PatternConverter>> rules,
      final List<String> options,
      final boolean disableAnsi,
      final boolean noConsoleNoAnsi) {
    String converterName = converterId;
    Class<PatternConverter> converterClass = null;

    if (rules == null) {

      return null;
    }
    for (int i = converterId.length(); i > 0 && converterClass == null; i--) {
      converterName = converterName.substring(0, i);
      converterClass = rules.get(converterName);
    }

    if (converterClass == null) {

      return null;
    }

    /*       if (AnsiConverter.class.isAssignableFrom(converterClass)) {
      options.add(DISABLE_ANSI + '=' + disableAnsi);
      options.add(NO_CONSOLE_NO_ANSI + '=' + noConsoleNoAnsi);
    } */
    // Work around the regression bug in Class.getDeclaredMethods() in Oracle Java in version >
    // 1.6.0_17:
    // http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6815786
    final Method[] methods = converterClass.getDeclaredMethods();
    Method newInstanceMethod = null;
    for (final Method method : methods) {
      if (Modifier.isStatic(method.getModifiers())
          && method.getDeclaringClass().equals(converterClass)
          && method.getName().equals("newInstance")
          && areValidNewInstanceParameters(method.getParameterTypes())) {
        if (newInstanceMethod == null) {
          newInstanceMethod = method;
        } else if (method.getReturnType().equals(newInstanceMethod.getReturnType())) {

          return null;
        }
      }
    }
    if (newInstanceMethod == null) {

      return null;
    }

    final Class<?>[] parmTypes = newInstanceMethod.getParameterTypes();
    final Object[] parms = parmTypes.length > 0 ? new Object[parmTypes.length] : null;

    if (parms != null) {
      int i = 0;
      boolean errors = false;
      for (final Class<?> clazz : parmTypes) {
        if (clazz.isArray() && clazz.getName().equals("[Ljava.lang.String;")) {
          final String[] optionsArray = options.toArray(new String[] {});
          parms[i] = optionsArray;
        } else {
          errors = true;
        }
        ++i;
      }
      if (errors) {
        return null;
      }
    }

    try {
      final Object newObj = newInstanceMethod.invoke(null, parms);

      if (newObj instanceof PatternConverter) {
        currentLiteral.delete(
            0, currentLiteral.length() - (converterId.length() - converterName.length()));

        return (PatternConverter) newObj;
      }

    } catch (final Exception ex) {

    }

    return null;
  }

  private int finalizeConverter(
      final char c,
      final String pattern,
      final int start,
      final StringBuilder currentLiteral,
      final FormattingInfo formattingInfo,
      final Map<String, Class<PatternConverter>> rules,
      final List<PatternConverter> patternConverters,
      final List<FormattingInfo> formattingInfos,
      final boolean disableAnsi,
      final boolean noConsoleNoAnsi,
      final boolean convertBackslashes) {
    int i = start;
    final StringBuilder convBuf = new StringBuilder();
    i = extractConverter(c, pattern, i, convBuf, currentLiteral);

    final String converterId = convBuf.toString();

    final List<String> options = new ArrayList<>();
    i = extractOptions(pattern, i, options);

    final PatternConverter pc =
        createConverter(converterId, currentLiteral, rules, options, disableAnsi, noConsoleNoAnsi);

    if (pc == null) {
      StringBuilder msg;

      /*      if (Strings.isEmpty(converterId)) {
        msg = new StringBuilder("Empty conversion specifier starting at position ");
      } else {
        msg = new StringBuilder("Unrecognized conversion specifier [");
        msg.append(converterId);
        msg.append("] starting at position ");
      }*/

      // msg.append(i);
      // msg.append(" in conversion pattern.");

      patternConverters.add(literalPattern(currentLiteral.toString(), convertBackslashes));
      formattingInfos.add(FormattingInfo.getDefault());
    } else {
      patternConverters.add(pc);
      formattingInfos.add(formattingInfo);

      if (currentLiteral.length() > 0) {
        patternConverters.add(literalPattern(currentLiteral.toString(), convertBackslashes));
        formattingInfos.add(FormattingInfo.getDefault());
      }
    }

    currentLiteral.setLength(0);

    return i;
  }

  // Create a literal pattern converter with support for substitutions if necessary
  private LogEventPatternConverter literalPattern(String literal, boolean convertBackslashes) {
    /*     if (config != null && LiteralPatternConverter.containsSubstitutionSequence(literal)) {
      return new LiteralPatternConverter(config, literal, convertBackslashes);
    } */
    return SimpleLiteralPatternConverter.of(literal, convertBackslashes);
  }

  private enum ParserState {
    LITERAL_STATE,

    CONVERTER_STATE,

    DOT_STATE,

    MIN_STATE,

    MAX_STATE;
  }
}
