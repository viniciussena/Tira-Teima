program exemplo63;

var
  arq1, arq2 : text;
  aluno : array [1..5] of string[10];
  nota : array [1..5] of real;
  i, j : integer;
  alunoaux : string[10];
  notaaux : real;

begin
  assign (arq1, 'arq631.txt');
  reset (arq1);
  for i := 1 to 5 do
    readln (arq1, aluno[i]);
  assign (arq2, 'arq632.txt');
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
            notaaux := nota[i];
            nota[i] := nota[j];
            nota[j] := notaaux;
          end;
  for i := 1 to 5 do
    writeln (aluno[i]:10, nota[i]:6:2);
end.