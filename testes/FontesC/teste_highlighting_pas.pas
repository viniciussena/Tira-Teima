Program testahanoi;
 Var n,contador:integer;
 
 Procedure Hanoi(n,origem,dest, temp:integer; var contador:integer);
 Begin
    if(n=1) then begin
       contador:=contador+1;
    end
    else begin
    	Hanoi(n-1, origem, temp, dest,contador); 
    	contador:=contador+1;
    	Hanoi(n-1, temp, dest, origem,contador);
    end;
 End;
 Begin
     writeln('entre com o numero de discos');
     readln(n);
     contador:=0;
     Hanoi(n,1,2,3,contador);
     writeln('o numero de movimentos eh: ',contador);
 End.