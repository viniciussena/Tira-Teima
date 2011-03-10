#include<stdio.h>

int main(int argc, char *argv[]){
    int *p1;
    p1 = malloc(sizeof(int));
    *p1 = 10;
    printf("%d",*p1);
    free(p1);
    p1 = NULL;
    return 1;   
}
