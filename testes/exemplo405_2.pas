program exemplo405_2;

var
  numero, somap, soman : integer;
  ok : boolean;

begin
  somap := 0;
  soman := 0;
  ok := true;
  while ok do
    begin
      writeln ('entre com um numero (0 para encerrar)');
      readln (numero);
      if numero = 0
        then ok := false
        else
          if numero > 0
            then somap := somap + numero
            else soman := soman + numero;
    end;
  writeln ('soma dos numeros positivos: ', somap);   
  writeln ('soma dos numeros negativos: ', soman);   
end.
