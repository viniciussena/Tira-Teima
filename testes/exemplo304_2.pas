program exemplo304_2;

var
  a, b : real;
  
begin
  writeln('Digite a parte real:');
  readln(a);
  writeln('Digite a parte imaginaria:');
  readln(b);
  if b <> 0
    then
      writeln('nao-real')
    else
      if a < 0
        then
          writeln('real negativo')
        else
          writeln('real nao-negativo');
end.
