program exemplo302_1_2;

var
  dinheiro, imovel : char;

begin
  writeln('Voce tem dinheiro?');
  readln(dinheiro);
  writeln('Voce tem imovel?');
  readln(imovel);
  if (dinheiro='s') or (imovel='s')
    then
      writeln('Voce so precisa do passaporte.');
end.

