#include<stdio.h>

int main(){
    int a;
    scanf("%d",&a);
    if((a>=0) && (a<5))
        printf("numero entre 0 e 4");
    else
        printf("numero negativo ou maior que 4");
    return 0;
}
