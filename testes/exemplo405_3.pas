program exemplo405_3;

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
        while numero <> 0 do
          begin
            writeln ('entre com um numero inteiro (0 para encerrar)');
            readln (numero);
            if numero <> 0
              then
                if max < numero
                  then max := numero;
          end;
        writeln ('maximo: ', max);
      end;
end.
