filter' :: (a -> Bool) -> [a] -> [a]
filter' f [] = []
filter' f (x:xs)
  | f x       = x : filter' f xs
  | otherwise = filter' f xs
