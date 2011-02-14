program Programa802;

var
  x, y, z, w : integer;

function Soma : integer;
begin
  soma := x + y;
end;

begin
  x := 1;
  y := 2;
  z := soma;
  writeln (z);
  w := 2 * soma + 7;
  writeln (w);
end.