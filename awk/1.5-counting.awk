$3 > 15 { emp++ }
END { print emp, "employees worked more than 15 hours" }

## Crappier version I came up with before looking at book:
# BEGIN { emp = 0 }
# { for (i = 1; i <= NF; i++) { if ($3 > 15) { emp += 1 } } }
# END { printf("%d employees worked more than 15 hours\n", emp) }
