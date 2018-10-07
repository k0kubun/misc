class Solution {
    public static void main(String[] args) {
        int numbers[] = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            numbers[i] = Integer.parseInt(args[i]);
        }
        int result = new Solution().maxSubarraySumCircular(numbers);
        System.out.println(result);
    }

    public int maxSubarraySumCircular(int[] A) {
        int max = 0;
        for (int start = 0; start < A.length; start++) {
            int sub[] = new int[A.length];
            int j = 0;
            for (int i = start; i < A.length + start; i++) {
                if (i < A.length) {
                    sub[j] = A[i];
                } else {
                    sub[j] = A[i - A.length];
                }
                j++;
            }
            int subMax = subMax(sub);
            if (max < subMax) {
                max = subMax;
            }
        }
        return max;
    }

    public int subMax(int[] sub) {
        int max = sub[0];
        int curSum = 0;
        for (int i = 0; i < sub.length; i++) {
            curSum += sub[i];
            if (max < curSum) {
                max = curSum;
            }
        }
        return max;
    }
}
