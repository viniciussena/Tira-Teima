program Exemplo704;

type
  ficha = record
    nome : string[10];
    nota : array [1..5] of real;
    media : real;
  end;

var
  aluno : array [1..10] of ficha;
  auxaluno : ficha;
  arq : text;
  soma : real;
  i, j, numaluno : integer;

begin
  assign (arq, 'arq704.txt');
  reset (arq);
  i := 0;
  while not eof (arq) do
    begin
      i := i + 1;
      read (arq, aluno[i].nome);
      soma := 0;
      for j := 1 to 5 do
        begin
          read (arq, aluno[i].nota[j]);
          soma := soma + aluno[i].nota[j];
        end;
      aluno[i].media := soma/5;
      readln (arq);
    end;
  numaluno := i;
  for i:= 1 to numaluno-1 do
    for j:= i+1 to numaluno do
      if aluno[i].nome > aluno[j].nome
        then
          begin
            auxaluno := aluno[i];
            aluno[i] := aluno[j];
            aluno[j] := auxaluno;
          end;
  for i := 1 to numaluno do
    begin
      write (aluno[i].nome);
      for j := 1 to 5 do
        write (aluno[i].nota[j]:4:1);
      writeln (aluno[i].media:6:2);
    end; 
end.

