program Programa504;

var
  arq1 : text;
  nomearq : string;
  nome : string [10];
  idade : integer;
  nota : real;

begin
  writeln ('entre com o nome do arquivo');
  readln (nomearq);
  assign (arq1, nomearq);
  reset (arq1);
  read (arq1, nome);
  read (arq1, idade);
  read (arq1, nota);
end.
