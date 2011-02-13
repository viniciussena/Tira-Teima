program exemplo503_2;

var
  arq1 : text;
  nomearq : string;
  nome : string[10];
  idade : integer;
  nota : real;
  i, j : integer;

begin
  writeln ('entre com o nome do arquivo');
  readln (nomearq);
  assign (arq1, nomearq);
  rewrite (arq1);
  for i := 1 to 4 do
    begin
      writeln('entre com um nome');
      readln (nome);
      writeln ('entre com uma idade');
      readln (idade);
      write (arq1, nome:10, idade:8);
      for j := 1 to 3 do
        begin
          writeln ('entre com uma nota');
          readln (nota);
          write (arq1, nota);
        end;
      writeln (arq1);
    end;
  close (arq1);
end.