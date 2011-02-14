program Exemplo809;

type
  vetor = array [1..5] of real;

var
  w : vetor;
  crescente : boolean;
  resp : char;
  arq1 : text;
  i : integer;

procedure Troca (var a, b : real);

var
  aux : real;

begin
  aux := a;
  a := b;
  b := aux;
end;

procedure OrdenaVetor (var v:vetor; cresc:boolean);

var
  j,k : integer;

begin {corpo do procedure OrdenaVetor}
  for j := 1 to 4 do
    for k := j+1 to 5 do
      if cresc
        then
          begin
            if v[j] > v[k]
              then
                Troca (v[j], v[k]);
          end
        else
          if v[j] < v[k]
            then
              Troca (v[j], v[k]);
end;

begin {corpo do programa principal}
  assign (arq1, 'arq809.txt');
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