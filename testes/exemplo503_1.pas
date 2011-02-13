program exemplo503_1;

var
  arq1 : text;
  nomearq : string;
  nota : real;
  i, j : integer;

begin
  writeln ('entre com o nome do arquivo');
  readln (nomearq);
  assign (arq1, nomearq);
  rewrite (arq1);
  for i := 1 to 4 do
    begin
      writeln ('entrada dos dados da linha ', i);
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