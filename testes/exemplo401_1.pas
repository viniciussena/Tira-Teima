program exemplo401_1;

var
  soma : real;
  i : integer;

begin
  soma := 0;
  for i := 1 to 10 do
    soma := soma + 1 / (2*i);
  writeln ('soma: ', soma:6:2);  
end.

