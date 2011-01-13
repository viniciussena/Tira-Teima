program Exemplo804;

var
  x, y, z : integer;

procedure Soma (a, b : integer);
begin
  z := a + b;
  a := 10;
  b := 20;
end;

begin
  x := 1;
  y := 2;
  soma (x,y);
  writeln (z);
end.
