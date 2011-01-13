program exemplo303_3;

var
  especializacao, mestrado: char;
  salario, remuneracao: real;

begin
  salario := 1000;
  remuneracao := salario;
  writeln('Possui especializacao?');
  readln(especializacao);
  writeln('Possui mestrado?');
  readln(mestrado);
  if (especializacao = 's')
    then
      begin
        remuneracao := remuneracao * 1.03;
        writeln('Voce recebeu 3%.');
      end;
  if (mestrado = 's')
    then
      begin
        remuneracao := remuneracao * 1.07;
        writeln('Voce recebeu 7%.');
      end
    else
      writeln('Subsidiamos cursos de mestrado.');
  writeln('Sua remuneracao e de ', remuneracao:8:2);
end.
