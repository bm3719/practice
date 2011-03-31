/* -*- compile-command:"gcc condense.c -g -o condense"; -*-
 * Time-stamp: <2010-06-01 15:08:46 (bm3719)>
 * Found this one online at:
 * http://www.solipsys.co.uk/Writings/TestsForProgrammers_Part_1.html
 *
 * Here's the original problem:
 * 
 * Write a C routine with the following prototype:
 *   void condense_by_removing(
 *      char *z_terminated,
 *      char char_to_remove
 *   );
 * Your routine should modify the given zero-terminated string in place,
 * removing all instances of the given char.
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Return the char from the specified index in str, or a null termination char
// if it's passed str's length.
char char_shift(char *str, int ind) {
    return (ind < strlen(str)) ? str[ind] : '\0';
}

// This solves the problem.
char * condense_by_removing(char *z_terminated,
                            char char_to_remove) {
    int i, shift = 0;
    for (i = 0; i < strlen(z_terminated); i++) {
        // Handle previous char removals.
        if (shift) {
            z_terminated[i] = char_shift(z_terminated, i + shift);
        }
        // Find the next valid replacement for the current char if needed.
        while (z_terminated[i] == char_to_remove) {
            z_terminated[i] = char_shift(z_terminated, i + ++shift);
        }
    }
    return z_terminated;
}

int testf(char *str1, char *str2) {
    if (!strcmp(str1, str2)) {
        printf("Test passed.\n");
        return 1;
    } else {
        printf("Test failed, on %s vs. %s\n", str1, str2);
        return 0;
    }
}

int main(int argc, char **argv) {
    int result = 1;
    char testString1[] = "the quick brown dog...";
    // Basic test.
    result &= testf(condense_by_removing(testString1, 'o'), "the quick brwn dg...");
    // Test that the operation is destructive.
    result &= testf(testString1, "the quick brwn dg...");
    // Test multiple char removals in a row.
    char testString2[] = "cartoon toon";
    result &= testf(condense_by_removing(testString2, 'o'), "cartn tn");
    // Test input of nothing but removal chars.
    char testString3[] = "ooo";
    result &= testf(condense_by_removing(testString3, 'o'), "");
    // Test input of empty string.
    char testString4[] = "";
    result &= testf(condense_by_removing(testString4, 'o'), "");
    printf(result ? "All tests passed.\n" : "Some of the tests failed.\n");
    return !result;
}
