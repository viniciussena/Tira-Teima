program exemplo661;

var
  arq1 : text;
  m : array [1..5, 1..5] of integer;
  i, j : integer;

begin
  assign (arq1, 'arq66-1.txt');
  reset (arq1);
  for i := 1 to 5 do
    for j := 1 to 5 do
      read (arq1, m[i,j]);
  for i := 1 to 5 do
    m[i,i] := 0;
  for i := 1 to 5 do
    begin
      for j := 1 to 5 do
        write (m[i,j], '  ');
      writeln;
    end;
end.
