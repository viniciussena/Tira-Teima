program Exemplo606;

const
  n = 15;

var
  v : array [1..n] of integer;
  i, dado : integer;
  inicio, meio, fim : integer;
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
  inicio := 1;
  fim := n;
  meio := (inicio + fim) div 2;
  while (dado <> v[meio]) and (inicio <= fim) do
    begin  
      if dado > v[meio]
        then inicio := meio + 1
        else fim := meio - 1;
      meio := (inicio + fim) div 2;
    end;
  if dado = v[meio]
    then writeln ('achou na posicao:', meio:3)
    else writeln ('ausente');
end.