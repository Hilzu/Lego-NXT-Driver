
import java.util.ArrayList;

/**
 * Assorted tools that are used to support Lejos programs. These are reimplementations
 * of methods that are missing from the Lejos standard libraries.
 */
public class Tools {

    /**
     * Trims preceding and subsequent whitespaces.
     * @param s The string to be trimmed.
     * @return The string with whitespaces trimmed.
     */
    public static String trim(String s) {

        int count = s.length();
        int length = count;
        int start = 0;
        char[] val = s.toCharArray();

        while ((start < length) && (val[start] <= ' ')) {
            start++;
        }
        while ((start < length) && (val[length - 1] <= ' ')) {
            length--;
        }
        return ((start > 0) || (length < count)) ? s.substring(start, length) : s;
    }

    /**
     * Splits a String separated with <code>splitChar</code> to a String array.
     * @param splitStr String to split
     * @param splitChar Char to split from
     * @return  A String array with all the substrings.
     */
    public static String[] split(String splitStr, char splitChar) {
        ArrayList<String> substrings = new ArrayList<String>();

        int subStrStart = 0;
        for (int i = 0; i < splitStr.length(); i++) {
            if (splitStr.charAt(i) == splitChar) {
                String substring = splitStr.substring(subStrStart, i);
                if (substring.length() != 0) {
                    substrings.add(substring);
                }

                subStrStart = i + 1;
            }
        }

        String lastStr = splitStr.substring(subStrStart);
        if (lastStr.length() != 0) {
            substrings.add(lastStr);
        }

        String[] splitted = new String[substrings.size()];
        int i = 0;
        for (String string : substrings) {
            splitted[i++] = string;
        }

        return splitted;
    }
}
