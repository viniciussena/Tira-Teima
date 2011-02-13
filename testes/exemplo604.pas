program exemplo64;

var
  arq1 : text;
  m : array [1..3, 1..5] of integer;
  i, j, k : integer;
  aux : integer;

begin
  assign (arq1, 'arq64.txt');
  reset (arq1);
  for i := 1 to 3 do
    for j := 1 to 5 do
      read (arq1, m[i,j]);
  for i := 1 to 3 do
    for j := 1 to 4 do
      for k := j + 1 to 5 do
        if m[i,j] > m[i,k]
          then
            begin
              aux := m[i,j];
              m[i,j] := m[i,k];
              m[i,k] := aux;
            end;
  for i := 1 to 3 do
    begin
      for j := 1 to 5 do
        write (m[i,j],'  ');
      writeln;
    end;
end.

