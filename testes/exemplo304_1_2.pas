program exemplo304_1_2;

var
  a, b : real;

begin
  writeln('Digite a parte real:');
  readln(a);
  writeln('Digite a parte imaginaria:');
  readln(b);
  if b <> 0
    then
      begin
        if a = 0
          then
            writeln('imaginario');
      end
    else
      writeln('real');
end.
