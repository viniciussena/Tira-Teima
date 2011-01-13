program exemplo202_2;

var
  alfa, beta : real;
  x, y, aux : real;

begin
  writeln ('entre com angulo alfa');
  readln (alfa);
  writeln ('entre com angulo beta');
  readln (beta);
  x := cos(alfa * pi / 180);
  y := sin(beta * pi / 180);
  writeln ('x =  ', x:5:3, '   y =  ', y:5:3);
  aux := x;
  x := y;
  y := aux;
  writeln ('x =  ', x:5:3, '   y =  ', y:5:3);
end.