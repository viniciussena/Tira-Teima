program Exemplo701;

type
  ficha = record
    nome : string[10];
    matric : integer;
    nota : real;
  end;

var
  aluno, alumax : ficha;
  arq : text;
  notamax : real;

begin
  assign (arq, 'arq701.txt');
  reset (arq);
  notamax := 0;
  while not eof (arq) do
    begin
      readln (arq, aluno.nome, aluno.matric, aluno.nota);
      if aluno.nota > notamax
        then
          begin
            alumax := aluno;
            notamax := aluno.nota;
          end;
    end;
  writeln (alumax.nome, alumax.matric, alumax.nota:4:1);
end.

