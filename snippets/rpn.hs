rpn :: String -> Double
rpn exp = head (foldl reduct [] (words exp))

reduct :: [Double] -> String -> [Double]
reduct (x:y:ys) "*" = (y * x):ys
reduct (x:y:ys) "+" = (y + x):ys
reduct (x:y:ys) "-" = (y - x):ys
reduct (x:y:ys) "/" = (y / x):ys
reduct (x:y:ys) "^" = (y ** x):ys
reduct (x:xs)  "ln" = log x:xs
reduct xs     "sum" = [sum xs]
reduct xs str       = read str:xs
