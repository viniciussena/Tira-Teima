program Exemplo505;

var
  arq1, arq2, arq3 : text;
  nomearq : string;
  nome : string[10];
  nota, soma : real;

begin
  writeln ('entre com o nome do arquivo de entrada');
  readln (nomearq);
  assign (arq1, nomearq);
  reset (arq1);
  writeln ('entre com o nome do arquivo de saida 1');
  readln (nomearq);
  assign (arq2, nomearq);
  rewrite (arq2);
  writeln ('entre com o nome do arquivo de saida 2');
  readln (nomearq);
  assign (arq3, nomearq);
  rewrite (arq3);
  while not eof (arq1) do
    begin
      soma := 0;
      read (arq1, nome);
      while not eoln (arq1) do
        begin
          read (arq1, nota);
          soma := soma + nota;
        end;
      readln (arq1);
      if soma > 10
        then
          writeln (arq2, nome:10, soma:6:2)
        else
          writeln (arq3, nome:10, soma:6:2);
    end;
  close (arq2);
  close (arq3);
end.
