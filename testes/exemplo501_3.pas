program exemplo501_3;

var
  arq1: text;
  n, soma, i: integer;
  nomearq: string;

begin
  writeln ('entre com o nome do arquivo');
  readln (nomearq);
  assign (arq1, nomearq);
  reset (arq1);
  soma := 0;
  for i := 1 to 5 do
    begin
      read (arq1, n);
      soma := soma + n;
    end;
  writeln (soma);
end.