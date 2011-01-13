program Programa403;

var
  num, fat, cont : integer;

begin
  writeln ('entre com valor de num');
  readln (num);
  cont := 0;
  fat := 1;
  while cont < num do
    begin
      cont := cont + 1;
      fat := fat * cont;
    end;
  writeln (fat);
end.