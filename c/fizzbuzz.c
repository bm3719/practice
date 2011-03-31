// Time-stamp: <2010-05-28 09:59:44 (bm3719)>
// FizzBuzz is a standard programmer baseline ability test.
//
// The problem: Write a program that prints the numbers from 1 to 100. But for
// multiples of three print "Fizz" instead of the number and for the multiples
// of five print "Buzz". For numbers which are multiples of both three and five
// print "FizzBuzz".

#include <stdio.h>
#include <stdlib.h>

int main(int argc, char * argv) {
    int i;
    for (i = 1; i < 101; i++) {
        if (((i % 3) == 0) && ((i % 5) == 0)) {
            printf("FizzBuzz ");
        } else if ((i % 3) == 0) {
            printf("Fizz ");
        } else if ((i % 5) == 0) {
            printf("Buzz ");
        } else {
            printf("%d ", i);
        }
    }
    printf("\n");
    return 0;
}
