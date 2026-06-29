# Book example
# Run with: echo "100 .05 10" | awk -f 1.6-interest.awk
# or just:  awk -f 1.6-interest.awk # and enter lines

# interest1 - compute compound interest
#   input:  amount rate years
#   output: compounded value at the end of each year

{   i = 1
    while (i <= $3) {
        printf("\t%.2f\n", 1 * (1 + $2) ^ i)
        i++
    }
}
