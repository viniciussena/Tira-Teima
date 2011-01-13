program Programa405;

var
  num, fat, cont : integer;

begin
  writeln ('entre com valor de num');
  readln (num);
  fat := 1;
  for cont := 1 to num do 
    fat := fat * cont;
  writeln (fat);
end.