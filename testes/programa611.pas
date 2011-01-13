program Exemplo611;

const
  n = 4;

type
  matriz = array [1..n,1..n] of real;

var
  m : matriz;
  i, j : integer;
  aux : real;
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
  for i := 1 to (n - 1) do 
    for j := (i + 1) to n  do
      begin
        aux := m[i,j];
        m[i,j] := m[j,i];
        m[j,i] := aux;
      end;
  for i := 1 to n do
    begin
      for j := 1 to n do
        write (m[i,j], ' ');
      writeln;
    end;
end.
