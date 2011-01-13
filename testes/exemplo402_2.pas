program exemplo402_2;

var
  numero, somap, soman : integer;

begin
  somap := 0;
  soman := 0;
  repeat
    writeln ('entre com um numero (0 para encerrar)');
    readln (numero);
    if numero > 0
      then somap := somap + numero
      else 
        if numero < 0
          then soman := soman + numero;
  until numero = 0;
  writeln ('soma dos numeros positivos: ', somap);   
  writeln ('soma dos numeros negativos: ', soman);   
end.
