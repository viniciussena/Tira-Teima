program Exemplo806;

var
  v, x, y : integer;

procedure Soma (var a,b,z:integer);

begin
  z := a + b;
  a := 10;
  b := 20;
end;

begin
  x := 1;
  y := 2;
  soma (x,y,v);
  writeln (v);
end.
