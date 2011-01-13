program exemplo403_1;

var
  numero, soma : integer;
  resp : char;

begin
  writeln ('deseja entrar com dados?  s/n');
  readln (resp);
  if resp = 'n'
    then writeln ('conjunto vazio de dados')
    else
      begin
        soma := 0;
        repeat
          writeln ('entre com um numero');
          readln (numero);
          soma := soma + numero;
          writeln ('deseja entrar com mais dados?  s/n');
          readln (resp);
        until (resp = 'n');
        writeln ('soma: ', soma);
      end;
end.
