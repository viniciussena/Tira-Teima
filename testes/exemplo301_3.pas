program exemplo301_3;

var
  idade : integer;
  preco : real;

begin
  writeln('Qual e a sua idade?');
  readln(idade);
  writeln('Qual e o preco de seu carro?');
  readln(preco);
  if idade >= 25
    then
      writeln('Valor do seguro: R$ ', preco/20:2:2, '.')
    else
      writeln('Voce nao pode contratar o seguro.');
end.
