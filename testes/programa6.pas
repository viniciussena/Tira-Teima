program Programa6;

var
  numero, min, max : integer;
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
        writeln ('entre com um numero');
        readln (numero);
        min := numero;
        max := numero;
        while resp = 's' do
          begin
            repeat
              writeln ('deseja entrar com mais dados?  s/n');
              readln (resp);
            until (resp = 's') or (resp = 'n');
            if resp = 's'
              then
                begin
                  writeln ('entre com um numero');
                  readln (numero);
                  if max < numero
                    then max := numero
             	  else
            	    if min > numero
            	      then min := numero;
                end;
          end;
        writeln ('maximo: ', max);
        writeln ('minimo: ', min);
      end;
end.
