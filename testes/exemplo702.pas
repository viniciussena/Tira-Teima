program exemplo72;

type
  ficha = record
    nome : string[10];
    notas : array [1..5] of real;
  end;

var
  arq1 : text;
  pessoa, pessoamin : ficha;
  i : integer;
  media, soma, mediamin : real;

begin
  assign (arq1, 'arq72.txt');
  reset (arq1);
  soma := 0;
  read (arq1, pessoamin.nome);
  for i := 1 to 5 do
    begin
      read (arq1, pessoamin.notas[i]);
      soma := soma + pessoamin.notas[i];
    end;
  mediamin := soma / 5;
  readln (arq1);
  while not eof (arq1) do
    begin
      soma := 0;
      read (arq1, pessoa.nome);
      for i := 1 to 5 do
        begin
          read (arq1, pessoa.notas[i]);
          soma := soma + pessoa.notas[i];
        end;
      readln (arq1);
      media := soma / 5;
      if media < mediamin
        then
          begin
            pessoamin := pessoa;
            mediamin := media;
          end;
    end;
  write (pessoamin.nome);
  for i := 1 to 5 do
    write (pessoamin.notas[i]:6:2);
  writeln (mediamin:6:2);
end.
