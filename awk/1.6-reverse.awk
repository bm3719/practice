# Print the input, but in reverse order, using arrays.

# { lines[NR] = $3 " " $2 " " $1 }
# END { for (i = 1; i <= NR; i++) {
#         { print lines[i] }
#     }
# }

# Oops, was supposed to print out the lines in reverse order, not the fields.

{ lines[NR] = $0 }
END { for (i = NR; i > 0; i--) {
        { print lines[i] }
    }
}
