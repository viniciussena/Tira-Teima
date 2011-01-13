program Programa302;

var
  a, b, c : integer;

begin
  writeln ('entre com os valores de a, b, c');
  readln (a, b, c);
  if (a < b) and (b < c)
    then writeln (a, c)
    else
      if (a < c) and (c < b)
        then writeln (a, b)
        else
          if (b < c) and (c < a) 
            then writeln (b, a)
            else
              if (b < a) and (a < c)
                then writeln (b, c)
                else
                  if (c < b) and (b < a)
                    then writeln (c, a)
                    else writeln (c, b);
end.