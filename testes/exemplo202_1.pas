program exemplo202_1;

var
  x, y, aux : real;

begin
  writeln ('entre com o valor de x');
  readln (x);
  writeln ('entre com o valor de y');
  readln (y);
  writeln ('x =  ',x:5:2, '   y =  ',y:5:2);
  aux := x;
  x := y;
  y := aux;
  writeln ('x =  ', x:5:2, '  y =  ', y:5:2);
end.