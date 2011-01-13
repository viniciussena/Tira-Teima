program Exemplo603;

var
  a : array [1..10] of real;
  max : real;
  i : integer;
  arq : text;
  nomearq : string;

begin
  writeln ('entre com o nome do arquivo');
  readln (nomearq);
  assign (arq, nomearq);
  reset (arq);
  for i := 1 to 10 do 
    read (arq, a[i]);
  max := a[1];
  for i := 2 to 10 do
    if max < a[i]
      then max := a[i];
  writeln ('maximo = ', max:5:1);
end.