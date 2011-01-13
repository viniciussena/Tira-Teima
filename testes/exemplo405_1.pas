program exemplo405_1;

var
  numero, soma : integer;
  ok : boolean;

begin
  ok := true;
  soma := 0;
  while ok do
    begin
      writeln ('entre com um numero (0 para encerrar)');
      readln (numero);
      if numero = 0
        then ok := false
        else soma := soma + numero;
    end;
  writeln ('soma dos numeros: ', soma);   
end.