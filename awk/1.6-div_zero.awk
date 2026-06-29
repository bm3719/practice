# Finds employees paid greater than 30, and computes their average pay.

$2 > 30 { pay += $2 * $3; count++ }
END {
    if (count > 0)
    { print "Average pay for employees paid over 30/hr:", pay / count }
    else
    { print "No employees paid over 30/hr" }
}
