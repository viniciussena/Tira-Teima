program exemplo621;

var
  a, b : array [1..10] of char;
  arq1, arq2 : text;
  i, ocorrencias : integer;

begin
  assign (arq1, 'arq62-11.txt');
  reset (arq1);
  assign (arq2, 'arq62-12.txt');
  reset (arq2);
  for i := 1 to 10 do
    read (arq1, a[i]);
  for i := 1 to 10 do
    read (arq2, b[i]);
  ocorrencias := 0;
  for i := 1 to 10 do
    if b[i] = a[1]
      then
        ocorrencias := ocorrencias + 1;
  writeln (ocorrencias);
end.