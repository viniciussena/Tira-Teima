program exemplo403_3;

var
  numero, soma : integer;
  resp : char;

begin
  repeat
    writeln ('deseja entrar com dados?  s/n');
    readln (resp);
  until (resp = 's') or (resp = 'n');
  if resp = 'n'
    then writeln ('conjunto vazio de dados')
    else
      begin
        soma := 0;
        repeat
          writeln ('entre com um numero');
          readln (numero);
          soma := soma + numero;
          repeat
            writeln ('deseja entrar com mais dados?  s/n');
            readln (resp);
          until (resp = 's') or (resp = 'n');
        until resp = 'n';
        writeln ('soma: ', soma);
      end;
end.
