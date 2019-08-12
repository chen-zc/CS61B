public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        Deque<Character> d = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i += 1) {
            d.addLast(word.charAt(i));
        }
        return d;
    }

    public boolean isPalindrome(String word) {
        if (word.length() == 0 || word.length() == 1) {
            return true;
        }
        Palindrome p = new Palindrome();
        Deque<Character> d = p.wordToDeque(word);
        return helper(d);
    }

    private boolean helper(Deque d) {
        if (d.size() == 0 || d.size() == 1) {
            return true;
        }
        if (d.removeFirst() == d.removeLast()) {
            return helper(d);
        }
        else {
            return false;
        }
    }

    /** return true if the word is a palindrome according to the
     * character comparison test provided by the CharacterComparator
     * passed in as argument cc. */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word.length() == 0 || word.length() == 1) {
            return true;
        }
        Palindrome p = new Palindrome();
        Deque<Character> d = p.wordToDeque(word);
        return helper2(d);
    }

    private boolean helper2(Deque d) {
        if (d.size() == 0 || d.size() == 1) {
            return true;
        }
        CharacterComparator cc = new OffByOne();
        if (cc.equalChars((char) d.removeFirst(), (char) d.removeLast())) {
            return helper2(d);
        } else {
            return false;
        }
    }
}