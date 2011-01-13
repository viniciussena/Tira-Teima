program exemplo304_3;

var
  a, b : real;
  
begin
  writeln('Digite a parte real:');
  readln(a);
  writeln('Digite a parte imaginaria:');
  readln(b);
  if b <> 0
    then
      if a = 0
        then
          writeln('imaginario')
        else
          writeln('nao-real')
    else
      writeln('real');
end.
