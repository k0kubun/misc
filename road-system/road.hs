data Section = Section { getA :: Int, getB :: Int, getC :: Int }
  deriving (Show)
type RoadSystem = [Section]

data Label = A | B | C deriving Show
type Path = [(Label, Int)]

startToGoal :: RoadSystem
startToGoal = [ Section 50 10 30
              , Section 5  90 20
              , Section 40 2  25
              , Section 10 8  0
              ]

roadStep :: (Path, Path) -> Section -> (Path, Path)
roadStep (pathA, pathB) (Section a b c) =
  let timeA = sum (map snd pathA)
      timeB = sum (map snd pathB)

      nextA = if timeA + a < timeB + b + c
                 then pathA ++ [(A, a)]
                 else pathB ++ [(B, b), (C, c)]
      nextB = if timeB + b < timeA + a + c
                 then pathB ++ [(B, b)]
                 else pathA ++ [(A, a), (C, c)]
      in (nextA, nextB)

result =
  let (pathA, _) = foldl roadStep ([], []) startToGoal
  in pathA
