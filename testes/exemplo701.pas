program exemplo71;

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
  pessoa, pessoanova : ficha;

begin
  assign (arq1, 'arq71.txt');
  reset (arq1);
  readln (arq1, pessoanova.nome,
    pessoanova.nascimento.dia, pessoanova.nascimento.mes,
    pessoanova.nascimento.ano, pessoanova.divida);
  while not eof (arq1) do
    begin
      readln (arq1, pessoa.nome,
        pessoa.nascimento.dia, pessoa.nascimento.mes,
        pessoa.nascimento.ano, pessoa.divida);
      if (pessoa.nascimento.ano > pessoanova.nascimento.ano) or
         ((pessoa.nascimento.ano = pessoanova.nascimento.ano) and
              (pessoa.nascimento.mes > pessoanova.nascimento.mes)) or
         ((pessoa.nascimento.ano = pessoanova.nascimento.ano) and
              (pessoa.nascimento.mes = pessoanova.nascimento.mes) and
              (pessoa.nascimento.dia > pessoanova.nascimento.dia))
        then
          pessoanova := pessoa;
    end;
  writeln ('pessoa mais nova:  ', pessoanova.nome,
     pessoanova.nascimento.dia:4, pessoanova.nascimento.mes:4,
     pessoanova.nascimento.ano:8, pessoanova.divida:8:2);
end.
