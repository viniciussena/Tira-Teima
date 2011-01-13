program Programa406;

var
  n, cont : integer;
  termo, soma : integer;

begin
  writeln ('entre com valor de num');
  readln (n);
  termo := 1;
  soma := 0;
  for cont := 1 to n do
    begin
      termo := termo * 2;
      soma := soma + termo;
    end;
  writeln (soma);
end.
