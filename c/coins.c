// -*- compile-command: "gcc coins.c -g -o coins" -*-
// coin permutation problem

#include <stdio.h>
#include <errno.h>

int COIN_TYPES[] = {1, 5, 10, 25};
int **coinmap;

void naive_recurse(int ind, ) {

}

int main(int argc, char ** argv) {
  if (argc != 2) {
    printf("usage: coins total\n");
    return(1);
  }

  // grab input parameter
  long input = strtol(argv[1], (char **)NULL, 10);
  if (errno == EINVAL) {
    printf("input error\n");
    return(1);
  }
  
  int i;
  printf("Coin values: ");
  for (i = 0; i < sizeof(COIN_TYPES) / sizeof(int); i++) {
    printf("%d ", COIN_TYPES[i]);
  }
  printf("\n");

  naive_recurse();
}

