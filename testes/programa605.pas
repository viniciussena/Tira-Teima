program Exemplo605;

const
  n = 15;

var
  v : array [1..n] of integer;
  achou : boolean;
  i, dado : integer;
  arq : text;
  nomearq : string;

begin
  writeln ('entre com o nome do arquivo');
  readln (nomearq);
  assign (arq, nomearq);
  reset (arq);
  for i := 1 to n do		
    read (arq, v[i]);
  writeln ('entre com o elemento a ser procurado');
  readln (dado);
  achou := false;
  i := 1;
  while (not achou) and (i <= n) do
    if dado = v[i]
      then achou := true
      else i := i + 1;
  if achou
    then writeln ('achou na posicao:', i:3)
    else writeln ('ausente');
end.
