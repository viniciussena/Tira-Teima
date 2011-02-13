program exemplo711;

type
  ficha = record
    nome : string[10];
    dia, mes, ano : integer;
    divida : real;
  end;

var
  arq1 : text;
  pessoa : ficha;
  soma : real;

begin
  assign (arq1, 'arq71-1.txt');
  reset (arq1);
  soma := 0;
  while not eof (arq1) do
    begin
      readln (arq1, pessoa.nome, pessoa.dia,
        pessoa.mes, pessoa.ano, pessoa.divida);
      soma := soma + pessoa.divida;
    end;
  writeln ('soma das dividas:  ', soma:8:2);
end.
