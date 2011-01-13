program Exemplo609;

var
  m : array [1..4,1..4] of integer;
  i, j : integer;
  arq : text;
  nomearq : string;

begin
  writeln ('entre com o nome do arquivo');
  readln (nomearq);
  assign (arq, nomearq);
  reset (arq);
  for i := 1 to 4 do		
    for j := 1 to 4 do
      read (arq, m[i,j]);
  for i := 1 to 4 do 
    m[i,i] := 0;
  for i := 1 to 4 do
    begin
      for j := 1 to 4 do
        write (m[i,j], ' ');
      writeln;
    end;
end.
