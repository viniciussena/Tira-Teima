program exemplo202;

var
  alfa, beta : real;
  x, y, aux : real;

begin
  writeln ('entre com angulo alfa');
  readln (alfa);
  writeln ('entre com angulo beta');
  readln (beta);
  alfa := alfa * pi / 180;
  beta := beta * pi / 180;
  x := (sqr(cos(alfa)) - sqr(sin(beta))) / (2*cos(alfa));
  y := sqr(sin(alfa)) - sqr(cos(beta));
  writeln ('x =  ', x, '   y =  ',y);  
  writeln ('x =  ',x:6:3, '   y =  ',y:6:3);
  aux := x;
  x := y;
  y := aux;
  writeln ('x =  ', x:6:3, '   y =  ', y:6:3);
end.