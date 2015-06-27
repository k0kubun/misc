qsort :: (Ord a) => [a] -> [a]
qsort [] = []
qsort (x:xs) =
  let smaller = [a | a <- xs, a <= x]
      larger  = [a | a <- xs, a > x]
  in  qsort smaller ++ [x] ++ qsort larger
