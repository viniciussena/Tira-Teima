program Programa502_3;

var
  arq1 : text;
  nomearq : string;
  nome : string[10];
  idade : integer;
  nota, soma : real;

begin
  writeln ('entre com o nome do arquivo');
  readln (nomearq);
  assign (arq1, nomearq);
  reset (arq1);
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
      writeln (nome,'  ', idade, '  ', soma:5:2);
    end;
end.
