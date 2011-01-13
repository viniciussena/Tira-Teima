program Programa303;

var
  a, b, c : integer;

begin
  writeln ('entre com os valores de a, b, c');
  readln (a, b, c);
  if (a < b)
    then
      if (b < c)
        then  writeln (a, c)
        else
          if  (a < c)
            then writeln (a, b)
            else  writeln (c, b)
    else
      if  (c < b)
        then writeln (c, a)
        else
          if (a < c)
            then writeln (b, c)
            else  writeln (b, a);
end.