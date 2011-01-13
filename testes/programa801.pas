program Exemplo801;

var
  x, y, z : integer;

procedure Soma;
begin
  z := x + y;
end;

begin
  x := 1;
  y := 2;
  soma;
  writeln (z);
  x := 5;
  y := 6;
  soma;
  writeln (z);
end.