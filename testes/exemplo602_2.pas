program exemplo622;

var
  a, b : array [1..10] of char;
  arq1, arq2 : text;
  i : integer;
  achou : boolean;

begin
  assign (arq1, 'arq62-21.txt');
  reset (arq1);
  assign (arq2, 'arq62-22.txt');
  reset (arq2);
  for i := 1 to 10 do
    read (arq1, a[i]);
  for i := 1 to 10 do
    read (arq2, b[i]);
  achou := false;
  i := 0;
  while (not achou) and (i <= 9) do
    begin
      i := i + 1;
      if b[i] = a[1]
        then achou := true;
    end;
  if achou
    then writeln (i)
    else writeln (0);
end.
