#include<stdio.h>

void incrementa (int* p){
    *p = *p + 1;   
}

int main(int argc, char *argv[]){
    int x;
    x = 10;
    incrementa(&x);
    printf("%d",x);
    return 1;   
}
