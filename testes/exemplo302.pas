program exemplo302;

var
  passaporte, dinheiro, imovel : char;

begin
  writeln('Voce tem passaporte?');
  readln(passaporte);
  writeln('Voce tem dinheiro?');
  readln(dinheiro);
  writeln('Voce tem imovel?');
  readln(imovel);
  if not ((passaporte='s') and ((dinheiro='s') or (imovel='s')))
    then
      writeln('Voce nao pode trabalhar no pais.');
end.
