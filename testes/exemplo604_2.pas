program exemplo642;

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
  for i := 1 to 3 do
    begin
      max := m[i,1];
      for j := 2 to 5 do
        if max < m[i,j]
          then max := m[i,j];
      writeln ('max linha ', i, ':  ', max);
    end;
end.

