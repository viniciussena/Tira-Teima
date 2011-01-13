program Exemplo607;

const
  n = 10;

var
  v : array [1..n] of integer;
  i, j, aux : integer;
  arq : text;
  nomearq : string;

begin
  writeln ('entre com o nome do arquivo');
  readln (nomearq);
  assign (arq, nomearq);
  reset (arq);
  for i := 1 to n do
    read (arq, v[i]);
  for i := 1 to (n -1) do
    for j := (i + 1) to n do
      if v[i] > v[j]
        then
          begin
            aux := v[i];
            v[i] := v[j];
            v[j] := aux;
          end;
  for i := 1 to n do
    write (v[i],' ');
end.