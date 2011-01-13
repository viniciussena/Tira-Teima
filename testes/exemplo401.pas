program exemplo401;

var
  termo, soma : real;
  sinal, i : integer;

begin
  soma := 0;
  sinal := -1;
  for i := 1 to 10 do
    begin
      sinal := (-1) * sinal;
      termo := sinal * 1 / (2*i);
      writeln (termo:7:3);
      soma := soma + termo;
    end;
  writeln ('soma: ', soma:6:2);
end.
