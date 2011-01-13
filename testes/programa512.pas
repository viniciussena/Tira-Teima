program Exemplo512;

var
  arq1, arq2: text;
  nomearq:string;
  nome:string[10];
  numero, soma: integer;

begin
  writeln('entre nome arquivo de entrada');
  readln (nomearq);
  assign (arq1, nomearq);
  reset (arq1);
  writeln('entre nome arquivo de saida');
  readln (nomearq);
  assign (arq2, nomearq);
  rewrite (arq2);
  while (not eof (arq1)) do
    begin
      read (arq1, nome);
      soma := 0;
      while (not eoln (arq1)) do
        begin
          read (arq1, numero);
          soma := soma + numero;
        end;
      readln (arq1);
      writeln (arq2, nome, '  ', soma);
    end;
  close (arq2);
end.