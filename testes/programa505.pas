program Programa505;

var
  arq1 : text;
  nomearq : string;
  w,x,y,z: integer;

begin
  writeln ('entre com o nome do arquivo');
  readln (nomearq);
  assign (arq1, nomearq);
  reset (arq1);
  readln (arq1, w, x);
  readln (arq1, y, z);
  writeln (w,' ',x,' ',y,' ',z,' ');
end.