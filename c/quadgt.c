/* -*- Mode: C; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4; compile-command:"gcc quadgt.c -g -o quadgt"; -*-
 * Time-stamp: <2010-06-11 13:40:41 (bm3719)>
 * Programmer: Bruce C. Miller - bm3719@gmail.com
 *
 * This is a port of QUADGT.BAS in C. I just slapped this together as fast as
 * possible to play the game since I couldn't find a copy of the BASIC version
 * online. Feel free to write a better version using anything here.
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

int main(int argc, char ** argv) {
  char * sInput;
  int iGuess[3];

  srand(time(NULL));

  printf("Do you want to see the instructions? (y/n): ");
  gets(sInput);
  if (strstr(sInput, "y") || strstr(sInput, "Y")) {  // if found
    printf("This game is played as follows:\n\n");
    printf("I will choose a 4 digit number. No two digits will be the same. Your job will\n");
    printf("be to figure out what digits I picked.\n\n");
    printf("Make your guess by entering a 4 digit number. I will respond with a 2 digit\n");
    printf("number. My response indicates two things. First, I tell you how many digits you\n");
    printf("guessed are in the number I chose. Second, I tell you how many digits we placed\n");
    printf("in the correct position within the chosen number.\n\n");
    printf("For example, if you guess 1463 and I respond with 31 then you got 3 of the\n");
    printf("digits which appear in the answer but only one digit is in the correct position.\n");
    printf("Thus, the number I chose could possibly be 0364.\n\n");
    printf("The experienced player will average 5 or 6 guesses per game.\n\n");
  }

  while (1) {
    int iSecret[4] = { rand() % 9, -1, -1, -1 };

    while (iSecret[1] == iSecret[0] || iSecret[1] == iSecret[2] ||
           iSecret[1] == iSecret[3]) {
      iSecret[1] = rand() % 9;
    }
    while (iSecret[2] == iSecret[0] || iSecret[2] == iSecret[1] ||
           iSecret[2] == iSecret[3]) {
      iSecret[2] = rand() % 9;
    }
    while (iSecret[3] == iSecret[0] || iSecret[3] == iSecret[1] ||
           iSecret[3] == iSecret[2] || iSecret[3] == -1) {
      iSecret[3] = rand() % 9;
    }

    printf("\nEnter your first 4 digit guess.\n\n");
    scanf("%1d%1d%1d%1d", &iGuess[0],&iGuess[1],&iGuess[2],&iGuess[3]);

    int turn;
    for (turn = 0; turn < 20; turn++) {
      // TODO: error check input


      // match check
      if (iGuess[0] == iSecret[0] && iGuess[1] == iSecret[1] && iGuess[2] == iSecret[2] &&
      	  iGuess[3] == iSecret[3]) {
        printf("You got it in %d guesses.\n", turn + 1);
        break;
      }

      // calculate number of correct digits
      int iFirst = 0;
      int j, k;
      for (j = 0; j < 4; j++) {
        for (k = 0; k < 4; k++) {
          if (iGuess[j] == iSecret[k]) {
            iFirst++;
          }
        }
      }

      // calculate number of digits in correct location
      int iSecond = 0;
      for (j = 0; j < 4; j++) {
        if (iGuess[j] == iSecret[j]) {
          iSecond++;
        }
      }
      printf(" %d%d\n\n", iFirst, iSecond);

      scanf("%1d%1d%1d%1d", &iGuess[0], &iGuess[1], &iGuess[2], &iGuess[3]);

      // scold player's stupidity
      if (turn == 11) {
        printf("You are obviously a beginner. Here's a hint.\n\n");
        printf("The first digit is a %d\n\n", iSecret[0]);
      }
      if (turn == 14) {
        printf("I'm beginning to lose my patience. The first two digits are %d%d If you can't\n");
        printf("get it now, give it up..\n\n",
          iSecret[0], iSecret[1]);
      }
      if (turn == 19) {
        printf("You are officially an idiot. Please reintegrate yourself into the food chain.\n\n");
        printf("The number was %d%d%d%d\n", iSecret[0], iSecret[1], iSecret[2], iSecret[3]);
      }
    }

    printf("Play another game? (y/n): ");
    scanf("%s", sInput);
    if (strstr(sInput, "n") || strstr(sInput, "N")) {
      break;
    }
  }

  return(0);
}



