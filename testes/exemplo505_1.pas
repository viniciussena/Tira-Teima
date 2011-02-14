program Exemplo505_1;

var
  arq1, arq2, arq3 : text;
  nomearq : string;
  nota, soma : real;
  i : integer;

begin
  writeln ('entre com o nome do arquivo de entrada');
  readln (nomearq);
  assign (arq1, nomearq);
  reset (arq1);
  writeln ('entre com o nome do arquivo de saida 1');
  readln (nomearq);
  assign (arq2, nomearq);
  rewrite (arq2);
  writeln ('entre com o nome do arquivo de saida 2');
  readln (nomearq);
  assign (arq3, nomearq);
  rewrite (arq3);
  while not eof (arq1) do
    begin
      soma := 0;
      for i := 1 to 3 do
        begin
          read (arq1, nota);
          soma := soma + nota;
        end;
      readln (arq1);
      if soma > 10
        then
          writeln (arq2, soma:6:2)
        else
          writeln (arq3, soma:6:2);
    end;
  close (arq2);
  close (arq3);
end.
