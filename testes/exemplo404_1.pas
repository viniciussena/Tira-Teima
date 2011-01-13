program exemplo404_1;

var
  valor, i : integer;

begin
  repeat
    writeln('entre com um numero inteiro positivo');
    readln(valor);
  until (valor > 0);
  i := 1;
  repeat
    if (valor mod i) = 0
      then writeln(i);
    i := i + 1;
  until (i > valor);
end.