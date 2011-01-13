program exemplo406_1;

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
        while resp = 's' do
          begin
            writeln ('entre com um numero');
            readln (numero);
            soma := soma + numero;
            writeln ('deseja entrar com mais dados?  s/n');
            readln (resp);
          end;
        writeln ('soma: ', soma);
      end;
end.