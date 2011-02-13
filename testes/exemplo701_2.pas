program Exemplo72;

type
  ficha = record
    nome : string[10];
    dia, mes, ano : integer;
    divida : real;
  end;

var
  arq1 : text;
  pessoa, pessoanova : ficha;

begin
  assign (arq1, 'arq71-2.txt');
  reset (arq1);
  readln (arq1, pessoanova.nome, pessoanova.dia,
    pessoanova.mes, pessoanova.ano, pessoanova.divida);
  while not eof (arq1) do
    begin
      readln (arq1, pessoa.nome, pessoa.dia,
        pessoa.mes, pessoa.ano, pessoa.divida);
      if (pessoa.ano > pessoanova.ano) or
         ((pessoa.ano = pessoanova.ano) and
              (pessoa.mes > pessoanova.mes)) or
         ((pessoa.ano = pessoanova.ano) and
              (pessoa.mes = pessoanova.mes) and
              (pessoa.dia > pessoanova.dia))
        then
          pessoanova := pessoa;
    end;
  writeln ('pessoa mais nova:  ', pessoanova.nome,
     pessoanova.dia:4, pessoanova.mes:4,
     pessoanova.ano:8, pessoanova.divida:8:2);
end.
