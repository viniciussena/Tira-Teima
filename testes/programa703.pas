program Exemplo703;

type
  ficha = record
    nome : string[10];
    nota : array [1..5] of real;
    media : real;
  end;

var
  aluno, alumax : ficha;
  arq : text;
  mediamax, soma : real;
  i : integer;

begin
  assign (arq, 'arq703.txt');
  reset (arq);
  mediamax := 0;
  while not eof (arq) do
    begin
      read (arq, aluno.nome);
      soma := 0;
      for i := 1 to 5 do
        begin
          read (arq, aluno.nota[i]);
          soma := soma + aluno.nota[i];
        end;
      readln (arq);
      aluno.media := soma / 5;
      if aluno.media >= mediamax
        then
          begin
            alumax := aluno;
            mediamax := aluno.media;
          end;
    end;
  write (alumax.nome);
  for i := 1 to 5 do
    write (alumax.nota[i]:4:1);
  writeln (alumax.media:6:2);
end.

