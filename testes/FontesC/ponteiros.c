#include<stdio.h>

int main(int argc, char *argv[]){
    int numero;
    int *p;
    p = NULL;
    p = &numero;
    numero = 10;
    printf("%d",*p);
    free(p);
    return 0;
}
