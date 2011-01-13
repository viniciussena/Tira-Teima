program exemplo401_3;

var
  termo, soma : real;
  i : integer;

begin
  soma := 0;
  for i := 1 to 10 do
    begin
      termo := 1 / (2*i);
      writeln ('termo: ', termo: 6:2);
      soma := soma + termo;
    end;
  writeln ('soma: ', soma:6:2);    
end.
