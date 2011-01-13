program Exemplo808;

type
  vetor = array [1..5] of real;

var
  w : vetor;
  crescente : boolean;
  resp : char;
  arq1, arq2 : text;
  i : integer;

procedure OrdenaVetor (var v:vetor; cresc:boolean);

var
  j,k : integer;

procedure Troca;

var
  aux : real;

begin
  aux := v[j];
  v[j] := v[k];
  v[k] := aux;
end;

begin {corpo do procedure OrdenaVetor}
  for j := 1 to 4 do
    for k := j+1 to 5 do
      if cresc
        then
          begin
            if v[j] > v[k]
              then
                Troca
          end
        else
          if v[j] < v[k]
            then
              Troca;
end;

begin {corpo do programa principal}
  assign (arq1, 'arq808a.txt');
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
end.
