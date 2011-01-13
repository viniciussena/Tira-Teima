program exemplo404_3;

var
  n, i : integer;
  primo : boolean;

begin
  primo := true;
  repeat
    writeln ('entre com um numero positivo');
    readln (n);
  until n > 0;
  if n > 2
    then
      begin
        i := 2;
        repeat
          if (n mod i = 0)
            then primo := false;
          i := i + 1;
        until ((not primo) or (i > n div 2));
      end;
  if primo
    then writeln (n, ' e primo')
    else writeln (n, ' nao e primo'); 
end.