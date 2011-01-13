program exemplo402_1;

var
  numero, soma : integer;

begin
  soma := 0;
  repeat
    writeln ('entre com um numero (0 para encerrar)');
    readln (numero);
    if (numero <>0)
      then soma := soma + numero;
  until numero = 0;
  writeln ('soma dos numeros: ', soma);   
end.