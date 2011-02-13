program exemplo612;

var
  v : array [1..10] of integer;
  i, soma : integer;
  arq1 : text;

begin
  assign (arq1, 'arq61-2.txt');
  reset (arq1);
  for i := 1 to 10 do
    read (arq1, v[i]);
  soma := 0;
  for i := 1 to 10 do
    if v[i] mod 2 = 0
      then
        soma := soma + v[i];
  writeln ('soma: ', soma);
end.
