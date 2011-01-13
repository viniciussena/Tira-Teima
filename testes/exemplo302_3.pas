program exemplo302_3;

var
  passaporte, dinheiro, imovel : char;

begin
  writeln('Voce tem passaporte?');
  readln(passaporte);
  writeln('Voce tem dinheiro?');
  readln(dinheiro);
  writeln('Voce tem imovel?');
  readln(imovel);
  if (passaporte='s') and ((dinheiro='s') or (imovel='s'))
    then
      writeln('Voce pode trabalhar no pais.');
end.
