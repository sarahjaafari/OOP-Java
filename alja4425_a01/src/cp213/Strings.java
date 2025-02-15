package cp213;

/**
 * @author Sara Aljaafari 169044425
 * @version 2023-09-22
 */
public class Strings {
    // Constants
    public static final String VOWELS = "aeiouAEIOU";

    /**
     * Determines if string is a "palindrome": a word, verse, or sentence (such as
     * "Able was I ere I saw Elba") that reads the same backward or forward. Ignores
     * case, spaces, digits, and punctuation in the string parameter s.
     *
     * @param string a string
     * @return true if string is a palindrome, false otherwise
     */
    public static boolean isPalindrome(final String string) {
    	String cleanString = string.toLowerCase().replaceAll("[^a-zA-Z]", "");

        String reversed = new StringBuilder(cleanString).reverse().toString();

        return cleanString.equals(reversed);
    }

    /**
     * Determines if name is a valid Java variable name. Variables names must start
     * with a letter or an underscore, but cannot be an underscore alone. The rest
     * of the variable name may consist of letters, numbers and underscores.
     *
     * @param name a string to test as a Java variable name
     * @return true if name is a valid Java variable name, false otherwise
     */
    public static boolean isValid(final String name) {
    	String pattern = "^[a-zA-Z_][a-zA-Z0-9_]*$";

        return name.matches(pattern);
    }

    /**
     * Converts a word to Pig Latin. The conversion is:
     * <ul>
     * <li>if a word begins with a vowel, add "way" to the end of the word.</li>
     * <li>if the word begins with consonants, move the leading consonants to the
     * end of the word and add "ay" to the end of that. "y" is treated as a
     * consonant if it is the first character in the word, and as a vowel for
     * anywhere else in the word.</li>
     * </ul>
     * Preserve the case of the word - i.e. if the first character of word is
     * upper-case, then the new first character should also be upper case.
     *
     * @param word The string to convert to Pig Latin
     * @return the Pig Latin version of word
     */
    public static String pigLatin(String word) {
    	if (word == null || word.isEmpty()) {
            return word; // Return the input word if it's null or empty
        }

        // Check if the word starts with a vowel (case insensitive)
        boolean startsWithVowel = "aeiouAEIOU".indexOf(word.charAt(0)) != -1;

        // Preserve the case of the first character
        char firstChar = word.charAt(0);
        boolean isUpperCase = Character.isUpperCase(firstChar);

        if (startsWithVowel) {
            // If it starts with a vowel, add "way" to the end
            word += "way";
        } else {
            // If it starts with consonants, move the leading consonants to the end and add "ay"
            int firstVowelIndex = -1;
            for (int i = 1; i < word.length(); i++) {
                if ("aeiouAEIOU".indexOf(word.charAt(i)) != -1) {
                    firstVowelIndex = i;
                    break;
                }
            }

            if (firstVowelIndex != -1) {
                String leadingConsonants = word.substring(0, firstVowelIndex);
                String restOfWord = word.substring(firstVowelIndex);

                word = restOfWord + leadingConsonants + "ay";
            } else {
                // If there are no vowels in the word, simply add "ay" to the end
                word += "ay";
            }
        }

        // Preserve the case of the first character
        if (isUpperCase) {
            word = Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();
        }

        return word;
    }

}
