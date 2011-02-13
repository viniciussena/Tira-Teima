program exemplo73;

type
  ficha = record
    nome : string[10];
    notas : array [1..5] of real;
    media : real;
  end;

var
  arq1 : text;
  alunos : array [1..10] of ficha;
  i, j, numalunos : integer;
  soma : real;
  aux : ficha;

begin
  assign (arq1, 'arq73.txt');
  reset (arq1);
  i := 0;
  while not eof (arq1) do
    begin
      i := i + 1;
      read (arq1, alunos[i].nome);
      soma := 0;
      for j := 1 to 5 do
        begin
          read (arq1, alunos[i].notas[j]);
          soma := soma + alunos[i].notas[j];
        end;
      alunos[i].media := soma / 5;
      readln (arq1);
    end;
  numalunos := i;
  for i := 1 to numalunos - 1 do
    for j := i + 1 to numalunos do
      if alunos[i].media < alunos[j].media
        then
          begin
            aux := alunos[i];
            alunos[i] := alunos[j];
            alunos[j] := aux;
          end;
  for i := 1 to numalunos do
    writeln (alunos[i].nome:10, alunos[i].media:6:2);
end.
