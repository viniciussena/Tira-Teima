program exemplo302_3_2;

var
  passaporte, imovel : char;

begin
  writeln('Voce tem passaporte?');
  readln(passaporte);
  writeln('Voce tem imovel?');
  readln(imovel);
  if (passaporte='s') and (imovel='s')
    then
      writeln('Voce pode trabalhar no pais.');
end.
