program exemplo613;

var
  v : array [1..10] of integer;
  i : integer;
  arq1 : text;
  para : boolean;

begin
  assign (arq1, 'arq61-3.txt');
  reset (arq1);
  for i := 1 to 10 do
    read (arq1, v[i]);
  para := false;
  i := 0;
  while (not para) and (i <= 9) do
    begin
      i := i + 1;
      if v[i] mod 2 = 0
        then
          para := true;
    end;
  if para
    then writeln ('posicao: ', i)
    else writeln ('posicao: ', 0);
end.
