#include <stdio.h>
#include <stdlib.h>

//-------------------------------------
void ponteiros11 (int *p1, int **ep2) {

  *p1 = 5;
  free(*ep2);
  *ep2 = malloc (sizeof(int));
  **ep2 = 10;
}

//-------------------------------------
main () {

  int *pont1, *pont2;
  pont1 = malloc (sizeof (int));
  pont2 = malloc (sizeof (int));    
  ponteiros11 (pont1, &pont2);
  printf("%d \n", *pont1);
  printf("%d \n", *pont2);
  
  system("pause");
}
