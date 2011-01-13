program Exemplo507;

var
  arq1 : text;
  nomearq : string;
  x: integer;

begin
  writeln ('entre com o nome do arquivo');
  readln (nomearq);
  assign (arq1, nomearq);
  reset (arq1);
  while not eof (arq1) do
    begin
      while not eoln (arq1) do
        begin
          read (arq1, x);
          write (x,' ');
        end;
      readln(arq1);
      writeln;
    end;
end.