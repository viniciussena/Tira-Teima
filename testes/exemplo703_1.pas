program exemplo731;

type
  ficha = record
    nome : string[10];
    nota1, nota2, nota3 : real;
    media : real;
  end;

var
  arq1 : text;
  alunos : array [1..10] of ficha;
  i, numalunos : integer;
  soma : real;

begin
  assign (arq1, 'arq73-1.txt');
  reset (arq1);
  i := 0;
  while not eof (arq1) do
    begin
      i := i + 1;
      readln (arq1, alunos[i].nome, alunos[i].nota1,
              alunos[i].nota2, alunos[i].nota3);
      soma := alunos[i].nota1 + alunos[i].nota2 + alunos[i].nota3;
      alunos[i].media := soma / 3;
    end;
  numalunos := i;
  for i := 1 to numalunos do
    writeln (alunos[i].nome:10, alunos[i].media:6:2);
end.
