program Programa803;

var
  w, x, y, z : integer;

procedure Soma (a, b : integer);
begin
  z := a + b;
end;

begin
  x := 1;
  y := 2;
  soma (x,y);
  writeln (z);
  w := 5;
  soma (w,7);
  writeln (z);
  soma (4, 2*w);
  writeln (z);
end.
