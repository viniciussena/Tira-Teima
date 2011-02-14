program exemplo633;

var
  arq1, arq2 : text;
  aluno : array [1..5] of string[10];
  nota : array [1..5] of real;
  i, j : integer;
  alunoaux : string[10];
  notaaux : real;

begin
  assign (arq1, 'arq63-31.txt');
  reset (arq1);
  for i := 1 to 5 do
    readln (arq1, aluno[i]);
  assign (arq2, 'arq63-32.txt');
  reset (arq2);
  for i := 1 to 5 do
    readln (arq2, nota[i]);
  for i := 1 to 4 do
    for j := i + 1 to 5 do
      if aluno[i] > aluno[j]
        then
          begin
            alunoaux := aluno[i];
            aluno[i] := aluno[j];
            aluno[j] := alunoaux;
          end;
  for i := 1 to 4 do
    for j := i + 1 to 5 do
      if nota[i] < nota[j]
        then
          begin
            notaaux := nota[i];
            nota[i] := nota[j];
            nota[j] := notaaux;
          end;
  for i := 1 to 5 do
    writeln (aluno[i]:10);
  for i := 1 to 5 do
    write (nota[i]:6:2);
end.
