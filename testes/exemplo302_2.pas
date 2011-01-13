program exemplo302_2;

var
  passaporte, dinheiro : char;

begin
  writeln('Voce tem passaporte?');
  readln(passaporte);
  writeln('Voce tem dinheiro?');
  readln(dinheiro);
  if (passaporte = 's') and not (dinheiro = 's')
    then
      writeln('Voce pode trabalhar no pais se tiver imovel.');
end.
