package cp213;

/**
 * @author Sara Aljaafari 169044425
 * @version 2023-09-22
 */
public class Cipher {
    // Constants
    public static final String ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final int ALPHA_LENGTH = ALPHA.length();

    /**
     * Encipher a string using a shift cipher. Each letter is replaced by a letter
     * 'n' letters to the right of the original. Thus for example, all shift values
     * evenly divisible by 26 (the length of the English alphabet) replace a letter
     * with itself. Non-letters are left unchanged.
     *
     * @param s string to encipher
     * @param n the number of letters to shift
     * @return the enciphered string in all upper-case
     */
    public static String shift(final String s, final int n) {
    	if (s == null) {
            return null; 
        }

        StringBuilder result = new StringBuilder();

        for (char c : s.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                char shiftedChar = (char) (base + (c - base + n) % ALPHA_LENGTH);
                result.append(shiftedChar);
            } else {
                result.append(c); 
            }
        }

        return result.toString().toUpperCase();
    }

    /**
     * Encipher a string using the letter positions in ciphertext. Each letter is
     * replaced by the letter in the same ordinal position in the ciphertext.
     * Non-letters are left unchanged. Ex:
     *
     * <pre>
    Alphabet:   ABCDEFGHIJKLMNOPQRSTUVWXYZ
    Ciphertext: AVIBROWNZCEFGHJKLMPQSTUXYD
     * </pre>
     *
     * A is replaced by A, B by V, C by I, D by B, E by R, and so on. Non-letters
     * are ignored.
     *
     * @param s          string to encipher
     * @param ciphertext ciphertext alphabet
     * @return the enciphered string in all upper-case
     */
    public static String substitute(final String s, final String ciphertext) {
    	if (s == null || ciphertext == null) {
            return null; 
        }

        StringBuilder result = new StringBuilder();

        for (char c : s.toCharArray()) {
            if (Character.isLetter(c)) {
                int index = ALPHA.indexOf(Character.toUpperCase(c));

                if (index >= 0 && index < ciphertext.length()) {
                    char substitutedChar = ciphertext.charAt(index);

                    if (Character.isLowerCase(c)) {
                        substitutedChar = Character.toLowerCase(substitutedChar);
                    }

                    result.append(substitutedChar);
                }
            } else {
                result.append(c);
            }
        }

        return result.toString().toUpperCase();
    }
}
