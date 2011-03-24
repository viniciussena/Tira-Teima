#include <stdio.h>
#include <stdlib.h>

main () {

typedef struct elemento {
  char c;
  struct elemento *prox;
} lista;

  lista *pinicio, *p1;

  pinicio = NULL;

  p1 = malloc (sizeof (struct elemento));
  p1->c = 'x';   
  p1->prox = pinicio;
  pinicio = p1;

  p1 = malloc (sizeof (struct elemento));
  p1->c = 'y';   
  p1->prox = pinicio;
  pinicio = p1;

  p1 = malloc (sizeof (struct elemento));
  p1->c = 'z';   
  p1->prox = pinicio;
  pinicio = p1;

  printf("elemento1  %c \n", pinicio->c);
  printf("elemento2  %c \n", pinicio->prox->c);
  printf("elemento3  %c \n", pinicio->prox->prox->c);
  system ("pause");     
}
  
