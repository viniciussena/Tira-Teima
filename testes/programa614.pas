program Exemplo614;

var
  v1 : array [1..5] of string;
  v2 : array [1..5] of real;
  auxnome : string;
  auxnota : real;
  i, j : integer;
  arq1, arq2 : text;
  nomearq : string;

begin
  writeln ('entre com o nome do arquivo de nomes');
  readln (nomearq);
  assign (arq1, nomearq);
  reset (arq1);
  for i := 1 to 5 do
    readln (arq1, v1[i]);
  writeln ('entre com o nome do arquivo de notas');
  readln (nomearq);
  assign (arq2, nomearq);
  reset (arq2);
  for i := 1 to 5 do
    readln (arq2, v2[i]);
  for i := 1 to 4 do
      for j := (i+1) to 5 do
        if v1[i] > v1[j]
          then
            begin
              auxnome := v1[i];
              v1[i] := v1[j];
              v1[j] := auxnome;
              auxnota := v2[i];
              v2[i] := v2[j];
              v2[j] := auxnota;
            end;
  for i := 1 to 5 do
    writeln (v1[i]:10,' ',v2[i]:5:1);
end.
