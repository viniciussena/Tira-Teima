program Programa508;

var
  arq1 : text;
  nomearq : string;
  nome : string [5];
  idade : integer;
  nota : real;

begin
  writeln ('entre com o nome do arquivo');
  readln (nomearq);
  assign (arq1, nomearq);
  rewrite (arq1);
  writeln('entre com o nome');
  readln (nome);
  writeln ('entre com a idade');
  readln (idade);
  writeln ('entre com a nota');
  readln (nota);
  writeln (arq1, nome, idade:4, nota:6:1);
  close (arq1);
end.