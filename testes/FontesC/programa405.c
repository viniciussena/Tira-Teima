#include <stdio.h>
#include <stdlib.h>

void main(){
int num, fat, cont;
	printf("Entre com o valor de num: ");
	scanf("%d", &num);
	fat = 1;
	for (cont = 1; cont <= num; cont++) {
		fat = fat * cont;
	}
	printf("%d\n",fat);
}

