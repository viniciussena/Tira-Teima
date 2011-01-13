program exemplo407;

var
  n, i, valor  : integer;
  primo        : boolean;

begin
  writeln('Digite um numero inteiro positivo');
  repeat
    readln(valor);
  until (valor > 0);
  for n := 1 to valor do
    begin
      primo := true;
      i := 2;
      while (primo) and (i <= n div 2) do
        begin
          if (n mod i = 0)
            then primo := false;
          i := i + 1;
        end;
     if primo
        then writeln (n, ' e primo')
        else writeln (n, 'nao e primo');
    end;
end.