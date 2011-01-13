program exemplo303_1;

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
      remuneracao := remuneracao * 1.03;
  if (mestrado = 's')
    then
      remuneracao := remuneracao * 1.07;
  writeln('Sua remuneracao e de ', remuneracao:8:2);
end.
