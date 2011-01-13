program exemplo201;

const
  titulo = '      Nome  Idade   Peso';

var
  nome1, nome2, nome3 : string;
  idade1, idade2, idade3 : integer;
  peso1, peso2, peso3 : real;

begin
  writeln ('entre com o primeiro nome');
  readln (nome1);
  writeln ('entre com a primeira idade');
  readln (idade1);
  writeln ('entre com o primeiro peso');
  readln (peso1);
  writeln ('entre com o segundo nome');
  readln (nome2);
  writeln ('entre com a segunda idade');
  readln (idade2);
  writeln ('entre com o segundo peso');
  readln (peso2);
  writeln ('entre com o terceiro nome');
  readln (nome3);
  writeln ('entre com a terceira idade');
  readln (idade3);
  writeln ('entre com o terceiro peso');
  readln (peso3);
  writeln (titulo);
  writeln (nome1:10,'  ', idade1:5, '  ', peso1:6:2);
  writeln (nome2:10,'  ', idade2:5, '  ', peso2:6:2);
  writeln (nome3:10,'  ', idade3:5, '  ', peso3:6:2);
end.