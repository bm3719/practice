$2 > maxrate { emp = $1; maxrate = $2 }
END { print emp, "is the highest paid employee at rate", maxrate }
