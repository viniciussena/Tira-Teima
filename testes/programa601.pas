program Exemplo601;

const
  n = 10;

type
  indice = 1..n;
  vetor = array [indice] of real;

var
  a, b : vetor;
  i : integer;

begin
  for i := 1 to n do
    begin
      writeln ('entre com o elemento  a', i);
      readln (a[i]);
    end;
  for  i := 1 to n do  
    b[n + 1 – i] := a[i];
  for  i := 1 to n do
    writeln ('elemento  b', i,' = ', b[i]:5:1);
end.
