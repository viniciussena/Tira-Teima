program exemplo306_2_3;

var
  nivel: integer;

begin
  writeln('qual o nivel do pais?');
  readln(nivel);
  case nivel of
    1 .. 2 : writeln('investimento ruim');
    3 : begin
          writeln('investimento razoavel');
          writeln('ha paises melhores');
        end;
    4 : writeln('investimento bom');
    5 : writeln('investimento otimo');
  end;
end.
