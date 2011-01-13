program Programa401;

var
  num, fat, cont : integer;

begin
  writeln ('entre com valor de num');
  readln (num);
  cont := 0;
  fat := 1;
  repeat
    cont := cont + 1 ;
    fat := fat * cont ;
  until  cont >= num  ;
  writeln (fat);
end.