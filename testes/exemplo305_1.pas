program exemplo305_1;

var
  prestacao : real;
  dia : integer;

begin
  writeln ('entre com o valor da prestacao');
  readln (prestacao);
  writeln ('entre com o dia de pagamento');
  readln (dia);
  if dia <= 5
    then writeln ('prestacao = ', prestacao * 0.95:8:2)
  else if dia <= 10
    then writeln ('prestacao = ', prestacao * 0.96:8:2)
  else if dia <= 15
    then writeln ('prestacao = ', prestacao * 0.97:8:2)
  else if dia <= 20
    then writeln ('prestacao = ', prestacao * 0.98:8:2);
end.