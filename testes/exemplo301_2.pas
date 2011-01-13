program exemplo301_2;

var
  idade : integer;

begin
  writeln('Qual e a sua idade?');
  readln(idade);
  if idade < 25
    then
      begin
        writeln('Voce nao pode contratar o seguro.');
        writeln('Podera contratar em ', 25-idade,' anos.');
      end;
end.
