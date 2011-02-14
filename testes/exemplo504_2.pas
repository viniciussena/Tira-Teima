program exemplo504_2;

var
  arq1, arq2 : text;
  nomearq : string;
  nome : string[10];
  idade : integer;
  nota, soma : real;

begin
  writeln ('entre com o nome do arquivo de entrada');
  readln (nomearq);
  assign (arq1, nomearq);
  reset (arq1);
  writeln ('entre com o nome do arquivo de saida');
  readln (nomearq);
  assign (arq2, nomearq);
  rewrite (arq2);
  while not eof (arq1) do
    begin
      soma := 0;
      read (arq1, nome, idade);
      while not eoln (arq1) do
        begin
          read (arq1, nota);
          soma := soma + nota;
        end;
      readln (arq1);
      writeln (arq2, nome, idade, soma);
    end;
  close (arq2);
end.