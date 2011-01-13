program exemplo401_2;

var
  soma : real;
  sinal, i : integer;

begin
  soma := 0;
  sinal := -1;
  for i := 1 to 10 do
    begin
      sinal := (-1) * sinal;
      soma := soma + sinal * 1 / (2*i);
    end;
  writeln ('soma: ', soma:6:2); 
end.
