// 1-3: Modify the temperature conversion program to print a heading above the
// table.
#include <stdio.h>

// Print Fahrenheit-Celsius table for fahr = 0, 20, ..., 300; Floating-point
// version.
int main() {
    float fahr, celsius;
    float lower, upper, step;

    lower = 0;     // Lower limit of temperature scale.
    upper = 300;   // Upper limit.
    step = 20;     // Step size.

    fahr = lower;
    printf("Fahrenheit Celcius\n");
    printf("---------- -------\n");
    while (fahr <= upper) {
        celsius = (5.0/9.0) * (fahr-32.0);
        printf("       %3.0f  %6.1f\n", fahr, celsius);
        fahr = fahr + step;
    }
    return(0);
}
