reverse' :: [a] -> [a]
reverse' = foldl (\acc a -> a : acc) []
-- reverse' []     = []
-- reverse' (x:xs) = (reverse' xs) ++ [x]
