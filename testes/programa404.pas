program Programa404;

var
  num, fat, cont : integer;

begin
  writeln ('entre com valor de num');
  readln (num);
  cont := 1;
  fat := 1;
  while  cont <= num do
    begin
      fat := fat * cont;
      cont := cont + 1;
    end;
  writeln (fat);
end.