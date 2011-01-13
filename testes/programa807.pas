program Exemplo807;

type
  vetor = array [1..5] of real;

var
  w, x : vetor;
  crescente : boolean;
  resp : char;
  arq1, arq2 : text;
  i : integer;

procedure OrdenaVetor (var v:vetor; cresc:boolean);

var
  aux : real;
  j,k : integer;

 begin
  for j := 1 to 4 do
    for k := j+1 to 5 do
      if cresc
        then
          begin
            if v[j] > v[k]
              then
                begin
                  aux := v[j];
                  v[j] := v[k];
                  v[k] := aux;
                end;
          end
        else
          if v[j] < v[k]
            then
              begin
                aux := v[j];
                v[j] := v[k];
                v[k] := aux;
              end;
end;

begin {corpo do programa principal}
  assign (arq1, 'arq807a.txt');
  reset (arq1);
  for i := 1 to 5 do
    read (arq1, w[i]);
  writeln ('deseja ordem crescente? s/n');
  readln (resp);
  if (resp = 's')
    then crescente := true
    else crescente := false;
  OrdenaVetor (w, crescente);
  for i := 1 to 5 do
    write (w[i]:4:1,' ');
  writeln;
  assign (arq2, 'arq807b.txt');
  reset (arq2);
  for i := 1 to 5 do
    read (arq2, x[i]);
  writeln ('deseja ordem crescente? s/n');
  readln (resp);
  if (resp = 's')
    then crescente := true
    else crescente := false;
  OrdenaVetor (x, crescente);
  for i := 1 to 5 do
    write (x[i]:4:1,' ');
  writeln;
end.
