program exemplo713;

type
  data = record
    dia, mes, ano : integer;
  end;

  ficha = record
    nome : string[10];
    nascimento : data;
    divida : real;
  end;

var
  arq1 : text;
  pessoa : ficha;
  soma : real;

begin
  assign (arq1, 'arq71-3.txt');
  reset (arq1);
  soma := 0;
  while not eof (arq1) do
    begin
      readln (arq1, pessoa.nome, pessoa.nascimento.dia,
        pessoa.nascimento.mes, pessoa.nascimento.ano, pessoa.divida);
      soma := soma + pessoa.divida;
    end;
  writeln ('soma das dividas:  ', soma:8:2);
end.
