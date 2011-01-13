program Exemplo511;

var
  arq1:text;
  nomearq:string;
  nome:string[10];
  nota: real;
  idade:integer;

begin
  writeln('entre nome do arquivo');
  readln (nomearq);
  assign (arq1, nomearq);
  reset (arq1);
  while (not eof (arq1)) do
    begin
      readln (arq1, nome, idade, nota);
      writeln (nome, idade:4, nota:6:1);
    end;
end.

