program exemplo407_1;

var
  valor, i : integer;

begin
  writeln('Digite um numero inteiro positivo');
  repeat
    readln(valor);
  until (valor > 0);
  i := 1;
  while i < valor do
    begin
      if (valor mod i) = 0
        then writeln(i);
      i := i + 1;
    end;
end.