program Programa301;

var
  a, b, c : integer;

begin
  writeln ('entre com os valores de a, b, c');
  readln (a, b, c);
  if (a < b) and (b < c) then writeln (a, c);
  if (a < c) and (c < b) then writeln (a, b);
  if (b < c) and (c < a) then writeln (b, a);
  if (b < a) and (a < c) then writeln (b, c);
  if (c < b) and (b < a) then writeln (c, a);
  if (c < a) and (a < b) then writeln (c, b);
end.
