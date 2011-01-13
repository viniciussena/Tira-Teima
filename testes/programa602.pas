program Exemplo602;

var
  a: array [1..10] of real;
  i: integer;
  aux: real;

begin
  for i := 1 to 10 do
    begin
      writeln ('entre com o elemento a', i);
      readln (a[i]);
    end;
  for i := 1 to 10 div 2 do
    begin
      aux := a[i];
      a[i] := a[11 – i];
      a[5 – i] := aux;
    end;
  for i := 1 to 10 do
    writeln ('elemento a',i, ' = ', a[i]:6:2);
end.