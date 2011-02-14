program Exemplo810;

type
  vetor = array [1..5] of real;

var
  w, x : vetor;
  arq1 : text;
  soma1, soma2 : real;
  i : integer;

function SomaVetor (v:vetor): real;

var
  j : integer;
  somaaux : real;

begin
  somaaux := 0;
  for j := 1 to 5 do
    somaaux := somaaux + v[j];
  SomaVetor := somaaux;
end;

begin {corpo do programa principal}
  assign (arq1, 'arq810a.txt');
  reset (arq1);
  for i := 1 to 5 do
    read (arq1, w[i]);
  soma1 := SomaVetor (w);
  writeln (soma1:5:1);
  assign (arq1, 'arq810b.txt');
  reset (arq1);
  for i := 1 to 5 do
    read (arq1, x[i]);
  soma2 := SomaVetor (x);
  writeln (soma2:5:1);
end.