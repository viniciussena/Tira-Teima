program exemplo722;

type
  ficha = record
    nome : string[10];
    nota1, nota2, nota3 : real;
  end;

var
  arq1 : text;
  pessoa, pessoamin : ficha;
  media, soma, mediamin : real;

begin
  assign (arq1, 'arq72-2.txt');
  reset (arq1);
  readln (arq1, pessoamin.nome, pessoamin.nota1,
     pessoamin.nota2, pessoamin.nota3);
  soma := pessoamin.nota1 + pessoamin.nota2 + pessoamin.nota3;
  mediamin := soma / 3;
  while not eof (arq1) do
    begin
      readln (arq1, pessoa.nome, pessoa.nota1,
         pessoa.nota2, pessoa.nota3);
      soma := pessoa.nota1 + pessoa.nota2 + pessoa.nota3;
      media := soma / 3;
      if media < mediamin
        then
          begin
            pessoamin := pessoa;
            mediamin := media;
          end;
    end;
  write (pessoamin.nome:10, pessoamin.nota1:6:2,
     pessoamin.nota2:6:2, pessoamin.nota3:6:2, mediamin:6:2);
end.
