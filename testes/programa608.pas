program Exemplo608;

const
  n = 4;

type
  matriz = array [1..n,1..n] of real;

var
  m : matriz;
  i, j : integer;
  max : real;
  arq : text;
  nomearq : string;

begin
  writeln ('entre com o nome do arquivo');
  readln (nomearq);
  assign (arq, nomearq);
  reset (arq);
  for i := 1 to n do		
    for j := 1 to n do
      read (arq,  m[i,j]);
    max := m[1,1];
  for i := 1 to n do 
    for j := 1 to n  do
      if max < m[i,j]
        then max := m[i,j];
  writeln ('máximo = ', max:5:1);
end.
