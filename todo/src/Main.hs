import System.Environment
import System.IO
import System.Directory
import Data.List

main :: IO ()
main = do
  args <- getArgs
  case args of
       ("add":_)   -> addTask
       ("rm":rest) -> rmTask rest
       ("list":_)  -> listTasks
       _           -> showUsage

filePath :: String
filePath = "/tmp/todo"

addTask :: IO ()
addTask = do
  putStrLn "Input Task: "
  task <- getLine
  appendFile filePath (task ++ "\n")

rmTask :: [String] -> IO ()
rmTask [] = return ()
rmTask (number:_) = do
  text <- readFile filePath
  let tasks   = lines text
      newText = unlines $ delete (tasks !! read number) tasks
  (name, handle) <- openTempFile "/tmp" "temp"
  hPutStr handle newText
  hClose handle
  removeFile filePath
  renameFile name filePath

listTasks :: IO ()
listTasks = do
  text <- readFile filePath
  let tasks = zipWith (\n line -> show n ++ ": " ++ line) [0..] $ lines text
  mapM_ putStrLn tasks

showUsage :: IO ()
showUsage = do
  putStrLn "Usage:"
  putStrLn "  todo add"
  putStrLn "  todo list"
