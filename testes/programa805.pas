program Exemplo805;

var
  v, w, x, y : integer;

procedure Soma (a,b:integer; var z:integer);

begin
  z := a + b;
end;

begin
  x := 1;
  y := 2;
  soma (x,y,v);
  writeln (v);
  w := 5;
  soma (x,5,w);
  writeln (w);
end.
