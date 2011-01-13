program Exemplo513;

var
  arq1: text;
  nomearq: string;
  nome, nomemax, nomemin: string[10];
  nota, soma, media, mediamax, mediamin: real;
  primeira: boolean;
  i: integer;

begin
  writeln('entre nome do arquivo de entrada');
  readln (nomearq);
  assign (arq1, nomearq);
  reset (arq1);
  primeira := true;
  while (not eof (arq1)) do
    begin
      read (arq1, nome);
      soma := 0;
      for i := 1 to 3 do
        begin
          read (arq1, nota);
          soma := soma + nota;
        end;
      media := soma / 3;
      if primeira
        then
          begin
            nomemax := nome;
            mediamax := media;
            nomemin := nome;
            mediamin := media;
            primeira := false;
          end
        else
          if media > mediamax
            then
              begin
                nomemax := nome;
                mediamax := media;
              end
            else
             if media < mediamin
               then
                 begin
                   nomemin := nome;
                   mediamin := media;
                 end;
    end;
  writeln ('media maxima: ', nomemax, mediamax:7:1);
  writeln ('media minima: ', nomemin, mediamin:7:1);
end.