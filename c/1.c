#include <stdio.h>

int global = 3; //This is the Global variable.

void ChangeGlobal()
{
   global = 5; //Reference to Global variable in a function.
}

int main()
{
  printf("test1\n");
  printf("test2");
   ChangeGlobal();
   return 0;
}
