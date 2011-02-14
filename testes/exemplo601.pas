program exemplo61;

var
  v : array [1..10] of integer;
  i, soma : integer;
  arq1 : text;
  para : boolean;

begin
  assign (arq1, 'arq61.txt');
  reset (arq1);
  for i := 1 to 10 do
    read (arq1, v[i]);
  soma := 0;
  para := false;
  i := 0;
  while (not para) and (i <= 9) do
    begin
      i := i + 1;
      if v[i] mod 2 = 0
        then para := true
        else soma := soma + v[i];
    end;
  writeln ('soma: ', soma);
end.