program exemplo651;

var
  arq1 : text;
  m : array [1..5, 1..5] of integer;
  i, j, max : integer;

begin
  assign (arq1, 'arq65-1.txt');
  reset (arq1);
  for i := 1 to 5 do
    for j := 1 to 5 do
      read (arq1, m[i,j]);
  max := m[1,1];
  for i := 1 to 5 do
    for j := 1 to 5 do
      if max < m[i,j]
        then max := m[i,j];
  writeln ('max: ', max);
end.
