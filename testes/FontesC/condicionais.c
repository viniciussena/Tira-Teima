#include<stdio.h>

int main(){
    int a;
    scanf("%d",&a);
    if((a>=0) && (a<5))
        printf("número entre 0 e 5");
    else
        printf("número negativo ou maior que 6");
    return 0;
}
