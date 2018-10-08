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
        int max = A[0];
        int cache[] = new int[A.length + 1];
        int cacheInd = 0;

        for (int i = 0; i < 2 * A.length; i++) {
            int index = i;
            if (index >= A.length) {
                index -= A.length;
            }

            for (int j = cacheInd - 1; j >= 0; j--) {
                cache[j + 1] = cache[j] + A[index];
            }
            if (cacheInd < A.length) {
                cacheInd++;
            }

            cache[0] = A[index];

            for (int j = 0; j < cacheInd; j++) {
                if (max < cache[j]) {
                    max = cache[j];
                }
            }
        }

        return max;
    }
}
