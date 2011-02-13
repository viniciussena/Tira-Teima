program exemplo65;

var
  arq1 : text;
  m : array [1..5, 1..5] of integer;
  i, j, aux : integer;

begin
  assign (arq1, 'arq65.txt');
  reset (arq1);
  for i := 1 to 5 do
    for j := 1 to 5 do
      read (arq1, m[i,j]);
  for i := 2 to 5 do
    for j := 1 to i - 1 do
      begin
        aux := m[i,j];
        m[i,j] := m[j,i];
        m[j,i] := aux;
      end;
  for i := 1 to 5 do
    begin
      for j := 1 to 5 do
        write (m[i,j], '  ');
      writeln;
    end;
end.
