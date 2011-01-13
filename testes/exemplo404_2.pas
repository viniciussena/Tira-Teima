program exemplo404_2;

var
  valor, i, n : integer;

begin
  repeat
    writeln('entre com um numero inteiro positivo');
    readln(valor);
  until (valor > 0);
  for n := 1 to valor do
    begin
      write('numero: ', n, ' divisores: ');
      i := 1;
      repeat
        if (n mod i) = 0
          then write(i, ' ');
        i := i + 1;
      until (i > valor);
      writeln();
    end;
end.