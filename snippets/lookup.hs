phoneBook =
  [("betty", "555-2938")
  ,("bonnie", "452-2928")
  ,("pasty", "493-2928")
  ,("lucille", "205-2928")
  ,("wendy", "939-8282")
  ,("penny", "835-2492")
  ]

lookup' :: (Eq k) => k -> [(k, v)] -> Maybe v
lookup' key xs = foldr (\(k, v) acc -> if k == key then Just v else acc) Nothing xs
-- lookup' key [] = Nothing
-- lookup' key ((k, v):xs)
--   | key == k  = Just v
--   | otherwise = lookup' key xs
