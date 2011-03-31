// Just testing out valgrind.
// From: http://cs.ecs.baylor.edu/~donahoo/tools/valgrind/
#include <stdio.h>

int main() {
  char *p;

  // Allocation #1 of 19 bytes
  p = (char *) malloc(19);

  // Allocation #2 of 12 bytes
  p = (char *) malloc(12);
  free(p);

  // Allocation #3 of 16 bytes
  p = (char *) malloc(16);

  return 0;
}
