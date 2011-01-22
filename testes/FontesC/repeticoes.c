#include<stdio.h>
 
int main(){
    int a;
    scanf("%d",&a);
    while(a < 10){
        a = a + 1;
        printf("%d\n",a);
    }
    return 0;
}
