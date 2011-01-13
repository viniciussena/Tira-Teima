program exemplo305_3;

var
  prestacao : real;
  dia : integer;

begin
  writeln ('entre com o valor da prestacao');
  readln (prestacao);
  writeln ('entre com o dia de pagamento');
  readln (dia);
  if dia <= 5
    then
      if prestacao > 1000
        then writeln ('prestacao = ', prestacao * 0.92:8:2)
        else writeln ('prestacao = ', prestacao * 0.95:8:2)
  else if dia <= 10
    then
      if prestacao > 1000
        then writeln ('prestacao = ', prestacao * 0.93:8:2)
        else writeln ('prestacao = ', prestacao * 0.96:8:2)
  else if dia <= 15
    then writeln ('prestacao = ', prestacao * 0.97:8:2)
  else if dia <= 20
    then writeln ('prestacao = ', prestacao * 0.98:8:2);
end.
