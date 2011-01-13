program exemplo302_1;

var
  dinheiro, imovel : char;

begin
  writeln('Voce tem dinheiro?');
  readln(dinheiro);
  writeln('Voce tem imovel?');
  readln(imovel);
  if not ((dinheiro='s') or (imovel='s'))
    then
      writeln('Voce nao pode trabalhar no pais.');
end.
