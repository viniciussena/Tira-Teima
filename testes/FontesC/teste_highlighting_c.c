#include<stdio.h>

//Comentário de linha
/*Comentário 
de bloco*/
int main(int argc, char *argv[])
{
 int i, *a, *b, *c, *p, *o1, *o2, *e, n, n1;

 n = atoi(argv[1]);
 n1 = n+1;
 ALLO(a)
 ALLO(b)
 ALLO(c)

 a[0] = 1; b[0] = c[0] = n1;
 a[n1] = n1; b[n1] = n+2; c[n1] = n+3;
 for(i=1; i<n1; i++) {
   a[i] = i; b[i] = c[i] = 0;
 }

 o1 = a;
 if(n&1) { o2 = b; e = c; }
 else    { o2 = c; e = b; }

 while(*c>1) {
  if(o1[*o1] > e[*e]) o1[--(*o1)] = e[(*e)++];
  else                e[--(*e)] = o1[(*o1)++];
  p = e; e = o1; o1 = o2; o2 = p;
 }
}
