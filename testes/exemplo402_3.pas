program exemplo402_3;

var
  numero, max : integer;

begin
  writeln ('entre com um numero inteiro (0 para encerrar)');
  readln (numero);
  if numero = 0
    then 
      writeln ('sequencia vazia')
    else
      begin
        max := numero;
        repeat
          writeln ('entre com um numero inteiro (0 para encerrar)');
          readln (numero);
          if numero <> 0
            then 
              if max < numero
                then max := numero;
        until numero = 0;
        writeln ('maximo: ', max);
      end;
end.

