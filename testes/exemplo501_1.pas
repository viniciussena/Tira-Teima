program exemplo501_1;

var
  arq1 : text;
  n, soma, i : integer;

begin
  assign (arq1, 'arq11.txt');
  reset (arq1);
  soma := 0;
  for i := 1 to 5 do
    begin
      read (arq1, n);
      soma := soma + n;
    end;
  writeln (soma);
end.