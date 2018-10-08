class Solution {
    public static void main(String[] args) {
        String result = new Solution().reverseOnlyLetters(args[0]);
        System.out.println(result);
    }

    public String reverseOnlyLetters(String input) {
        char[] s = input.toCharArray();
        char[] result = input.toCharArray();

        int len = input.length();
        int reverseInd = len - 1;
        for (int i = 0; i < len; i++) {
            if (('a' <= s[i] && s[i] <= 'z') || ('A' <= s[i] && s[i] <= 'Z')) {
                while (!(('a' <= result[reverseInd] && result[reverseInd] <= 'z') || ('A' <= result[reverseInd] && result[reverseInd] <= 'Z'))) {
                    reverseInd--;
                }
                result[reverseInd] = s[i];
                reverseInd--;
            }
        }
        return new String(result);
    }
}
