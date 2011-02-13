program exemplo631;

var
  arq1, arq2 : text;
  aluno : array [1..5] of string[10];
  nota : array [1..5] of real;
  i : integer;
  notamax : real;

begin
  assign (arq1, 'arq63-11.txt');
  reset (arq1);
  for i := 1 to 5 do
    readln (arq1, aluno[i]);
  assign (arq2, 'arq63-12.txt');
  reset (arq2);
  for i := 1 to 5 do
    readln (arq2, nota[i]);
  notamax := nota[1];
  for i := 2 to 5 do
    if nota[i] > notamax
      then notamax := nota[i];
  writeln ('nota maxima: ', notamax:6:2);
end.