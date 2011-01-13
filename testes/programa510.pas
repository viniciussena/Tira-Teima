program Exemplo510;

var
  arq1:text;
  nomearq:string;
  nome:string[10];
  nota: real;
  idade:integer;
  resp: char;

begin
  writeln('entre nome do arquivo');
  readln(nomearq);
  assign (arq1, nomearq);
  rewrite (arq1);
  writeln ('deseja entrar um conjunto de dados? s / n');
  readln (resp);
  while (resp <> 'n') do
    begin
      writeln ('entre nome, idade, nota');
      readln (nome, idade, nota);
      writeln (arq1, nome, idade:4, nota:6:1);
      writeln ('deseja entrar mais um conjunto de dados? s / n');
      readln (resp);
    end;
  close (arq1);
end.