program exemplo404;

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
      if n > 2
        then
          begin
            i := 2;
            repeat
              if (n mod i = 0)
                then primo := false;
              i := i + 1;
            until ((not primo) or (i > n div 2));
          end;
      if primo
        then writeln (n, ' e primo')
        else writeln (n, 'nao e primo');
    end;
end.