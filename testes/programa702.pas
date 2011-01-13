program Exemplo702;

type
  data = record
    dia : integer;
    mes : integer;
    ano : integer;
  end;

  ficha = record
    nome : string[10];
    matric : integer;
    nota : real;
    epoca : data;
  end;

var
  aluno, alurecente : ficha;
  arq : text;
  primeiravez : boolean;

begin
  assign (arq, 'arq702.txt');
  reset (arq);
  primeiravez := true;
  while not eof (arq) do
    begin
      if primeiravez
        then
          begin
            readln (arq, alurecente.nome, alurecente.matric, alurecente.nota, alurecente.epoca.dia,
                    alurecente.epoca.mes, alurecente.epoca.ano);
            primeiravez := false;
          end
        else
          begin
            readln (arq, aluno.nome, aluno.matric, aluno.nota,
                 aluno.epoca.dia, aluno.epoca.mes, aluno.epoca.ano);
            if aluno.epoca.ano > alurecente.epoca.ano
              then
                alurecente := aluno
              else
                if (aluno.epoca.ano = alurecente.epoca.ano) and
                   (aluno.epoca.mes > alurecente.epoca.mes)
                  then alurecente := aluno
                  else
                    if (aluno.epoca.ano = alurecente.epoca.ano) and
                       (aluno.epoca.mes = alurecente.epoca.mes) and
                       (aluno.epoca.dia > alurecente.epoca.dia)
                      then alurecente := aluno;
          end;
    end;
  writeln (alurecente.nome, alurecente.matric, alurecente.nota:4:1,
        alurecente.epoca.dia:4, alurecente.epoca.mes:4,
        alurecente.epoca.ano:6);
end.

