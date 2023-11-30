package test.pattern;

import java.util.ArrayList;
import java.util.List;

public abstract class NameAbbreviator {

  private static final NameAbbreviator DEFAULT = new NOPAbbreviator();

  public static NameAbbreviator getAbbreviator(final String pattern) {
    if (pattern.length() > 0) {
      //  if pattern is just spaces and numbers then
      //     use MaxElementAbbreviator
      final String trimmed = pattern.trim();

      if (trimmed.isEmpty()) {
        return DEFAULT;
      }

      boolean isNegativeNumber;
      final String number;

      // check if number is a negative number
      if (trimmed.length() > 1 && trimmed.charAt(0) == '-') {
        isNegativeNumber = true;
        number = trimmed.substring(1);
      } else {
        isNegativeNumber = false;
        number = trimmed;
      }

      int i = 0;

      while (i < number.length() && number.charAt(i) >= '0' && number.charAt(i) <= '9') {
        i++;
      }

      //
      //  if all blanks and digits
      //
      if (i == number.length()) {
        return new MaxElementAbbreviator(Integer.parseInt(number), isNegativeNumber ? MaxElementAbbreviator.Strategy.DROP : MaxElementAbbreviator.Strategy.RETAIN);
      }

      final ArrayList<PatternAbbreviatorFragment> fragments = new ArrayList<>(5);
      char ellipsis;
      int charCount;
      int pos = 0;

      while (pos < trimmed.length() && pos >= 0) {
        int ellipsisPos = pos;

        if (trimmed.charAt(pos) == '*') {
          charCount = Integer.MAX_VALUE;
          ellipsisPos++;
        } else if (trimmed.charAt(pos) >= '0' && trimmed.charAt(pos) <= '9') {
          charCount = trimmed.charAt(pos) - '0';
          ellipsisPos++;
        } else {
          charCount = 0;
        }

        ellipsis = '\0';

        if (ellipsisPos < trimmed.length()) {
          ellipsis = trimmed.charAt(ellipsisPos);

          if (ellipsis == '.') {
            ellipsis = '\0';
          }
        }

        fragments.add(new PatternAbbreviatorFragment(charCount, ellipsis));
        pos = trimmed.indexOf('.', pos);

        if (pos == -1) {
          break;
        }

        pos++;
      }

      return new PatternAbbreviator(fragments);
    }

    //
    //  no matching abbreviation, return defaultAbbreviator
    //
    return DEFAULT;
  }

  public static NameAbbreviator getDefaultAbbreviator() {
    return DEFAULT;
  }

  public abstract void abbreviate(final String original, final StringBuilder destination);

  private static class NOPAbbreviator extends NameAbbreviator {

    public NOPAbbreviator() {
    }

    @Override
    public void abbreviate(final String original, final StringBuilder destination) {
      destination.append(original);
    }
  }

  private static class MaxElementAbbreviator extends NameAbbreviator {

    private final int count;
    private final Strategy strategy;

    public MaxElementAbbreviator(final int count, final Strategy strategy) {
      this.count = Math.max(count, strategy.minCount);
      this.strategy = strategy;
    }

    @Override
    public void abbreviate(final String original, final StringBuilder destination) {
      strategy.abbreviate(count, original, destination);
    }

    private enum Strategy {
      DROP(0) {
        @Override
        void abbreviate(final int count, final String original, final StringBuilder destination) {
          // If a path does not contain enough path elements to drop, none will be dropped.
          int start = 0;
          int nextStart;
          for (int i = 0; i < count; i++) {
            nextStart = original.indexOf('.', start);
            if (nextStart == -1) {
              destination.append(original);
              return;
            }
            start = nextStart + 1;
          }
          destination.append(original, start, original.length());
        }
      }, RETAIN(1) {
        @Override
        void abbreviate(final int count, final String original, final StringBuilder destination) {
          // We subtract 1 from 'len' when assigning to 'end' to avoid out of
          // bounds exception in return r.substring(end+1, len). This can happen if
          // precision is 1 and the category name ends with a dot.
          int end = original.length() - 1;

          for (int i = count; i > 0; i--) {
            end = original.lastIndexOf('.', end - 1);
            if (end == -1) {
              destination.append(original);
              return;
            }
          }
          destination.append(original, end + 1, original.length());
        }
      };

      final int minCount;

      Strategy(final int minCount) {
        this.minCount = minCount;
      }

      abstract void abbreviate(final int count, final String original, final StringBuilder destination);
    }
  }

  private static final class PatternAbbreviatorFragment {

    static final PatternAbbreviatorFragment[] EMPTY_ARRAY = {};

    private final int charCount;

    private final char ellipsis;

    PatternAbbreviatorFragment(final int charCount, final char ellipsis) {
      this.charCount = charCount;
      this.ellipsis = ellipsis;
    }

    int abbreviate(final String input, final int inputIndex, final StringBuilder buf) {
      // Note that indexOf(char) performs worse than indexOf(String) on pre-16 JREs
      // due to missing intrinsics for the character implementation. The difference
      // is a few nanoseconds in most cases, so we opt to give the jre as much
      // information as possible for best performance on new runtimes, with the
      // possibility that such optimizations may be back-ported.
      // See https://bugs.openjdk.java.net/browse/JDK-8173585
      int nextDot = input.indexOf('.', inputIndex);
      if (nextDot < 0) {
        buf.append(input, inputIndex, input.length());
        return nextDot;
      }
      if (nextDot - inputIndex > charCount) {
        buf.append(input, inputIndex, inputIndex + charCount);
        if (ellipsis != '\0') {
          buf.append(ellipsis);
        }
        buf.append('.');
      } else {
        // Include the period to reduce interactions with the buffer
        buf.append(input, inputIndex, nextDot + 1);
      }
      return nextDot + 1;
    }
  }

  private static final class PatternAbbreviator extends NameAbbreviator {

    private final PatternAbbreviatorFragment[] fragments;

    PatternAbbreviator(final List<PatternAbbreviatorFragment> fragments) {
      if (fragments.isEmpty()) {
        throw new IllegalArgumentException("fragments must have at least one element");
      }

      this.fragments = fragments.toArray(PatternAbbreviatorFragment.EMPTY_ARRAY);
    }

    @Override
    public void abbreviate(final String original, final StringBuilder destination) {
      // non-terminal patterns are executed once
      int originalIndex = 0;
      int iteration = 0;
      int originalLength = original.length();
      while (originalIndex >= 0 && originalIndex < originalLength) {
        originalIndex = fragment(iteration++).abbreviate(original, originalIndex, destination);
      }
    }

    PatternAbbreviatorFragment fragment(int index) {
      return fragments[Math.min(index, fragments.length - 1)];
    }
  }
}
