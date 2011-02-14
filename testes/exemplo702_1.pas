program exemplo721;

type
  ficha = record
    nome : string[10];
    nota1, nota2, nota3 : real;
  end;

var
  arq1 : text;
  pessoa : ficha;
  numnotas : integer;
  media, soma : real;

begin
  assign (arq1, 'arq72-1.txt');
  reset (arq1);
  soma := 0;
  numnotas := 0;
  while not eof (arq1) do
    begin
      readln (arq1, pessoa.nome, pessoa.nota1, pessoa.nota2, pessoa.nota3);
      soma := soma + pessoa.nota1 + pessoa.nota2 + pessoa.nota3;
      numnotas := numnotas + 3
    end;
  media := soma / numnotas;
  writeln (media:6:2);
end.
