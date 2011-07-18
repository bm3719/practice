// 1-5: Modify the temperature conversion program to print the table in reverse
// order, that is, from 300 degrees to 0.
#include <stdio.h>

int main() {
    float fahr, celsius;
    float lower = 0;     // Lower limit of temperature scale.
    float upper = 300;   // Upper limit.
    float step = 20;     // Step size.

    fahr = lower;
    for (fahr = upper; fahr >= lower; fahr -= step) {
        celsius = (5.0/9.0) * (fahr-32.0);
        printf("%3.0f %6.1f\n", fahr, celsius);
    }
    return(0);
}
