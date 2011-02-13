program exemplo502;

var
  arq1 : text;
  nomearq : string;
  nome, nomemax : string[10];
  idade, idademax : integer;
  nota, soma, somamax : real;

begin
  writeln ('entre com o nome do arquivo');
  readln (nomearq);
  assign (arq1, nomearq);
  reset (arq1);
  soma := 0;
  read (arq1, nome, idade);
  while not eoln (arq1) do
    begin
      read (arq1, nota);
      soma := soma + nota;
    end;
  readln (arq1);
  somamax := soma;
  nomemax := nome;
  idademax := idade;
  while not eof (arq1) do
    begin
      soma := 0;
      read (arq1, nome, idade);
      while not eoln (arq1) do
        begin
          read (arq1, nota);
          soma := soma + nota;
        end;
      readln (arq1);
      if somamax < soma
        then
          begin
            somamax := soma;
            nomemax := nome;
            idademax := idade;
          end;
    end;
  writeln (nomemax,'  ', idademax, '  ', somamax:5:2);
end.