program exemplo202_3;

var
  a, b : real;
  x, y, aux : real;

begin
  writeln ('entre com o valor de a');
  readln (a);
  writeln ('entre com o valor de b');
  readln (b);
  x := 2 * a * a - b;
  y := 3 * (b * b - a);
  writeln ('x =  ', x:5:2, '   y =  ', y:5:2);
  aux := x;
  x := y;
  y := aux;
  writeln ('x =  ', x:5:2, '   y =  ', y:5:2);
end.