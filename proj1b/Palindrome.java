public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new LinkedListDeque<Character>();
        for (int i = 0; i < word.length(); i++) {
            deque.addLast(word.charAt(i));
        }
        return deque;
    }

    public boolean isPalindrome(String word) {
        if (word.length() == 0 || word.length() == 1) {
            return true;
        }
        Deque<Character> deque = wordToDeque(word);
        while (!deque.isEmpty()) {
            Character first = deque.removeFirst();
            if (deque.isEmpty()) {
                return true;
            }
            Character last = deque.removeLast();
            if (!first.equals(last)) {
                return false;
            }
        }
        return true;
    }
}
