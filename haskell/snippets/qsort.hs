qsort :: (Ord a) => [a] -> [a]
qsort [] = []
qsort (x:xs) =
  let smaller = filter (<=x) xs
      larger  = filter (>x) xs
  in  qsort smaller ++ [x] ++ qsort larger
