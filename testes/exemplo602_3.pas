program exemplo623;

var
  a, b : array [1..10] of char;
  arq1, arq2 : text;
  i, j : integer;

begin
  assign (arq1, 'arq621.txt');
  reset (arq1);
  assign (arq2, 'arq622.txt');
  reset (arq2);
  for i := 1 to 10 do
    read (arq1, a[i]);
  for i := 1 to 10 do
    read (arq2, b[i]);
  for i := 1 to 10 do
    for j := 1 to 10 do
      writeln (a[i], '  ', b[j]);
end.
