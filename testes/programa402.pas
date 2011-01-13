program Programa402;

var
  num, fat, cont : integer;

begin
  writeln ('entre com valor de num');
  readln (num);
  cont := 1;
  fat := 1;
  repeat
    fat := fat * cont ;
    cont := cont + 1 ;
  until  cont > num ;
  writeln (fat);
end.