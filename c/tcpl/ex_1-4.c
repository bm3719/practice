// Write a program to print the corresponding Celsius to Fahrenheit table.
#include <stdio.h>

int main() {
    float fahr, cel, lower = -20, upper = 150, step = 10;

    printf("Celcius Fahrenheit\n");
    printf("------- ----------\n");
    for (cel = lower; cel <= upper; cel += step) {
        fahr = cel * (9.0 / 5.0) + 32.0;
        printf("    %3.0f        %3.0f\n", cel, fahr);
    }
    return(0);
}
