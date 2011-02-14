program exemplo62;

var
  a, b : array [1..10] of char;
  arq1, arq2 : text;
  i, j : integer;
  achou : boolean;

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
    begin
      achou := false;
      j := 0;
      while (not achou) and (j <= 9) do
        begin
          j := j + 1;
          if b[i] = a[j]
            then
              achou := true;
        end;
      if achou
        then writeln (b[i], ' ', j)
        else writeln (b[i], ' ', 0);
    end;
end.