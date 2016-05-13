package simpleedit;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utils.java
 *
 * @author Eugene Matiyuk (ematiyuk@gmail.com)
 * @year 2012
 */
public class Utils {

    public static File changeFileExtension(String fileName, String extension) {
        String tmpString = "";
        int lastPointPosition;
        int extensionLength = 0;

        for (int i = fileName.length() - 1; i >= 0; i--) {
            if (fileName.charAt(i) != '.') {
                extensionLength++;
            } else {
                break;
            }
        }
        lastPointPosition = (fileName.length() - 1) - extensionLength;

        for (int i = 0; i < fileName.length(); i++) {
            if (i != lastPointPosition) {
                tmpString += fileName.charAt(i);
            } else {
                return new File(tmpString.concat('.' + extension));
            }
        }
        return new File(tmpString.concat('.' + extension));
    }

    public static boolean validateForNumbersOnly(String input) {
        Pattern pattern = Pattern.compile("[0-9]");
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public static int getTextLinesCount(String text) {
        int lines = 1;
        for (int i = 0; i < text.length(); i++) {
            if ((text.charAt(i) == '\n') || (i == text.length())) {
                lines++;
            }
        }
        return lines;
    }

    /* Realization of the Knuth–Morris–Pratt string searching algorithm */
    public static ArrayList<Integer> kmpSearch(char[] text, char[] subStr) {
        ArrayList<Integer> indices = new ArrayList<Integer>();
        int subStrLength = subStr.length;
        int textLength = text.length;
        int[] prefixFunction = new int[subStrLength];
        prefixFunction[0] = -1;
        // prefix function calculating
        for (int i = 1; i < subStrLength; i++) {
            prefixFunction[i] = prefixFunction[i - 1] + 1;
            while (prefixFunction[i] > 0 && subStr[i - 1] != subStr[prefixFunction[i] - 1]) {
                prefixFunction[i] = prefixFunction[prefixFunction[i] - 1] + 1;
            }
        }
        // pattern matching
        for (int textIndex = 0, subStrIndex = 0; textIndex < textLength; textIndex++) {
            while (subStrIndex > 0 && subStr[subStrIndex] != text[textIndex]) {
                subStrIndex = prefixFunction[subStrIndex];
            }
            if (subStr[subStrIndex] == text[textIndex]) {
                subStrIndex++;
            }
            if (subStrIndex == subStrLength) {
                // pattern has been discovered on shift [textIndex - subStrLength + 1]
                indices.add(textIndex - subStrLength + 1);
                // looking for the next pattern entry
                subStrIndex = prefixFunction[subStrIndex - 1];
            }
        }
        return indices;
    }
}
