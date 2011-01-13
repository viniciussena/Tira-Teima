program Exemplo604;

var
  a : array [1..6] of integer;
  i : integer;
  arq : text;
  nomearq : string;
  cresc : boolean;

begin
  writeln ('entre com o nome do arquivo');
  readln (nomearq);
  assign (arq, nomearq);
  reset (arq);
  for i := 1 to 6 do
    read (arq, a[i]);
  cresc := true;
  i := 1;
  while (cresc) and (i <= 5) do
    begin
      if a[i] > a[i+1]
        then cresc := false;
      i = i + 1;
    end;
  if cresc 
    then writeln ('estão em ordem crescente')
    else writeln ('não estão em ordem crescente');
end.
