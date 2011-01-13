program Exemplo613;

const
  n = 4;

var
  m : array [1..n,1..n] of integer;
  i, j, k, aux : integer;
  arq : text;
  nomearq : string;

begin
  writeln ('entre com o nome do arquivo');
  readln (nomearq);
  assign (arq, nomearq);
  reset (arq);
  for i := 1 to n do
    for j := 1 to n do
      read (arq, m[i,j]);
  for k := 1 to n do
    for i := 1 to (n-1) do
      for j := (i+1) to n do
        if m[k,i] > m[k,j]
          then
            begin
              aux := m[k,i];
              m[k,i] := m[k,j];
              m[k,j] := aux;
            end;
  for i := 1 to n do
    begin
      for j := 1 to n do
        write (m[i,j],' ');
      writeln;
    end;
end.
