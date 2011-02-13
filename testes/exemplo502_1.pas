program exemplo502_1;

var
  arq1 : text;
  nomearq : string;
  nota, soma : real;

begin
  writeln ('entre com o nome do arquivo');
  readln (nomearq);
  assign (arq1, nomearq);
  reset (arq1);
  soma := 0;
  while not eof (arq1) do
    begin
      soma := 0;
      while not eoln (arq1) do
        begin
          read (arq1, nota);
          soma := soma + nota;
        end;
      readln (arq1);
      writeln (soma:5:2);
    end;
end.