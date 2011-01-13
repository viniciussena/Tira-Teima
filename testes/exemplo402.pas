program exemplo402;

var
  numero : integer;
  min, max : integer;

begin
  writeln ('entre com um numero inteiro (0 para encerrar)');
  readln (numero);
  if numero = 0
    then 
      writeln ('sequencia vazia')
    else
      begin
        max := numero;
        min := numero;
        repeat
          writeln ('entre com um numero inteiro (0 para encerrar)');
          readln (numero);
          if numero <> 0
            then
              if max < numero
                then max := numero
                else
                  if min > numero
                    then min := numero;
        until numero = 0;
        writeln ('maximo: ', max);
        writeln ('minimo: ', min);
      end;
end.
