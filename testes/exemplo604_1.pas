program exemplo641;

var
  arq1 : text;
  m : array [1..3, 1..5] of integer;
  i, j : integer;
  max : integer;

begin
  assign (arq1, 'arq64-1.txt');
  reset (arq1);
  for i := 1 to 3 do
    for j := 1 to 5 do
      read (arq1, m[i,j]);
  max := m[1,1];
  for i := 2 to 5 do
    if max < m[1,i]
      then max := m[1,i];
  writeln ('max linha 1:  ', max);
end.

