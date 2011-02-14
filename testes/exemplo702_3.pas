program exemplo723;

type
  ficha = record
    nome : string[10];
    notas : array [1..5] of real;
  end;

var
  arq1 : text;
  pessoa : ficha;
  i, numnotas : integer;
  media, soma : real;

begin
  assign (arq1, 'arq72-3.txt');
  reset (arq1);
  soma := 0;
  numnotas := 0;
  while not eof (arq1) do
    begin
      read (arq1, pessoa.nome);
      for i := 1 to 5 do
        begin
          read (arq1, pessoa.notas[i]);
          soma := soma + pessoa.notas[i];
          numnotas := numnotas + 1;
        end;
      readln (arq1);
    end;
  media := soma / numnotas;
  writeln (media:6:2);
end.
