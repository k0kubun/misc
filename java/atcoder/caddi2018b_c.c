#include <stdio.h>

int main(void) {
    long n;
    long p;
    scanf("%ld %ld", &n, &p);
    long remP = p;

    if (n == 1) {
        printf("%ld\n", p);
        return 0;
    }

    long answer = 1;

    long cnt2 = 0;
    while (remP % 2 == 0) {
        remP /= 2;
        cnt2++;
    }
    for (long j = 0; j < cnt2 / n; j++) {
        answer *= 2;
    }

    for (long i = 3; i <= remP && remP > 1; i += 2) { // prime
        long cnt = 0;
        while (remP % i == 0) {
            remP /= i;
            cnt++;
        }
        for (long j = 0; j < cnt / n; j++) {
            answer *= i;
        }
    }
    printf("%ld\n", answer);
    return 0;
}
