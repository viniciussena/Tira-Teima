program Exemplo509;

var
  arq1 : text;
  nomearq : string;
  v, x, y, w, z : integer;

begin
  writeln ('entre com o nome do arquivo');
  readln (nomearq);
  assign (arq1, nomearq);
  rewrite (arq1);
  writeln ('entre 5 números inteiros');
  readln (v, w, x, y, z);  
  writeln (arq1, v,' ', w);
  write (arq1, x,' ');
  writeln (arq1, y,' ', z);
  close (arq1);
end.
