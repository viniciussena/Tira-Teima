program exemplo501_2;

var
  arq1 : text;
  n, soma, i : integer;

begin
  assign (arq1, 'arq12.txt');
  reset (arq1);
  while not eof (arq1) do
    begin
      soma := 0;
      for i := 1 to 5 do
        begin
          read (arq1, n);
          soma := soma + n;
        end;
      writeln (soma);
    end;
end.