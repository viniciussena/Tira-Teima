program Exemplo612;

const
  n = 5;

type
  matriz = array [1..n,1..n] of integer;
  vetor = array [1..n] of integer;

var
  m : matriz;
  v : vetor;
  i, j : integer;
  max : integer;
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
  for i := 1 to n do 
    begin
      max := m[i,1];
      for j := 2 to n do
        if max < m[i,j]
          then max := m[i,j];
      v[i] := max;
    end; 
  for i := 1 to n do
    writeln ('valor maximo da linha', i:3,':', v[i]:4);
end.
