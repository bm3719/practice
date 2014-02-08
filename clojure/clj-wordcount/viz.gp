reset
set term png truecolor
set boxwidth 0.5
set style fill solid
set output "histogram.png"
set xlabel "Words"
set ylabel "Count"
set grid
set boxwidth 0.95 relative
set style fill transparent solid 0.5 noborder
plot "out.dat" using 2: xtic(1) with boxes lc rgb"green" notitle
