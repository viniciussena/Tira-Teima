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
  primeiravez : boolean;

function teste(v1, v2 : integer) : integer;
begin
end;

begin
  assign (arq, 'arq701.txt');
  reset (arq);
  primeiravez := true;
  while not eof (arq) do
    begin
      if primeiravez
        then
          begin
            readln (arq, alumax.nome, alumax.matric, alumax.nota);
            primeiravez := false;
          end
        else
          begin
            readln (arq, aluno.nome, aluno.matric, aluno.nota);
            if aluno.nota > alumax.nota
              then
                alumax := aluno;
          end;
    end;
  writeln (alumax.nome, alumax.matric, alumax.nota:4:1);
end.

