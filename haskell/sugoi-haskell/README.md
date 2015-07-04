# 第1章 はじめの第一歩
- `not`

## 1.1 関数呼び出し
- ほとんどの関数は前置関数
- 2引数の前置関数は`` ` ``で囲むことで中置関数として呼び出せる
- `succ`, `min`, `max`, `div`

## 1.2 赤ちゃんの最初の関数
- `:l` でファイルをロード
- すべての関数は何らかの値を返す
- 関数は大文字で始められない
- 関数が1つも値を取らないとき、これを定義とか名前とか呼びます
- `if`, `then`, `else`

## 1.3 リスト入門
- GHCiの中で名前を定義するときはletキーワードを使う
- `[1,2,3]`は`1:2:3:[]`の糖衣構文
- リストの操作方法
  - 連結: `++`
  - 先頭に追加: `:` (cons演算子とも呼ばれる)
  - 要素へのアクセス: `!!`
  - `head`, `tail`, `last`, `init`, `length`, `null`, `reverse`, `take`, `drop`, `maximum`, `minimum`, `sum`, `product`, `elem`

## 1.4 レンジでチン！
- レンジの書き方
  - `[1..20]`
- レンジにはステップを指定することができる
  - `[2,4..20]`
- レンジに浮動小数点を使うとおかしな振る舞いをすることがある
- `cycle`, `repeat`

## 1.5 リスト内包表記
- リスト内包表記の書き方
  - `[x*2 | x <- [1..10]]`
- 束縛: `<-`
- 使い捨て変数: `_`
  - `_`は予約語
- `odd`

## 1.6 タプル
- タプルの書き方
  - `(3, 'a', "hello")`
- 長さ2のタプルをペアと呼ぶ
- 長さ3のタプルをトリプルと呼ぶ
- 累乗: `^`
- `fst`, `snd`
- `zip`
  - zipはリスト2つからペアを作る
  - 短い方が採用されるので、`[1..]`を渡してもok

# 第2章 型を信じろ！
## 2.1 明示的な型宣言
- `:t` で式の型を調べる
- `::` は「の型を持つ」と読む
- 明示的な型の先頭は常に大文字
- 関数を書くとき、その関数に明示的な型宣言を与えることができる

## 2.2 一般的なHaskellの型
- Int型: 有界
- Integer型: 有界でない, 遅い
- Float型
- Double型: 精度がFloatの2倍
- Bool型
- Char型: Unicode文字, `'`で囲う, `[Char]`は文字列
- タプルも型
  - 要素の数とそれぞれの型によって決まる
  - `()`も型 (値`()`だけを取りうる)

## 2.3 型変数
- 型変数とは
  - 任意の型を表す
  - 小文字で始まる
- 多層的関数: 型変数を用いた関数

## 2.4 型クラス 初級講座
- 型クラスとは
  - 振る舞いを定義するインターフェイス
  - 型クラスのインスタンスは振る舞いを実装する
  - 型クラスに属する関数をメソッドと呼ぶ
- 型クラス制約の書き方
  - `(Ord -> a) => a`
     - 型aはOrdクラスのインスタンス
  - `=>`の手前にあるものが型クラス制約
- よく使われるHaskellの型クラス
  - `Eq`
     - `==`, `/=`
  - `Ord`
     - `>`, `<`, `>=`, `<=`
     - `compare`は`Ordering`(`GT`, `LT`, `EQ`)を返す
  - `Show`
     - `show`
  - `Read`
     - `read`
  - `Enum`
     - `succ`, `pred`
     - レンジの中で使える
  - `Bounded`
     - `maxBound`, `minBound`
     - 多層定数だから型を指定すると振る舞いが決定する
  - `Num`
     - あらゆる数も多層定数
  - `Floating`
     - `sin`, `cos`, `sqrt`, ...
  - `Integral`
     - Numは実数全体、Integralは整数全体
     - `fromIntegral`は実数への写像
- 型クラスに関する注意
  - 1つの型クラスは複数の型をインスタンスとして持てる
  - 別の型クラスのインスタンスであることを必要条件とする型クラスがある

# 第3章 関数の構文
## 3.1 パターンマッチ
- パターンマッチとは
  - ある種のデータが従うべきパターンを指定し、そのパターンに従ってデータを分解するために使う
  - 関数を定義する際にパターンマッチを使って、関数の本体を場合分けできる
- パターンに小文字から始まる名前を書くと、任意の値に合致する
- リストのパターンマッチ
  - `x:xs`
  - `x:[]`, `[x]`
  - `x:y:[]`, `[x,y]`
  - `(x:y:_)`を角括弧を使って書き直すことはできない
- asパターン
  - `xs@(x:y:ys)`
  - `all@(x:xs)`

## 3.2 場合分けして、きっちりガード！
- ガードとは
  - 関数を定義する際、引数の値が満たす性質で場合分けをするときにガードを使う
  - 引数の構造で場合分けするときにはパターンを使う

```haskell
bmiTell bmi
  | bmi <= 18.5 = "underweight"
  | otherwise   = "whale"
```

## 3.3 where！？
- whereで定義した変数はひとつのパターンの関数からしか見えない
- whereの束縛内でもパターンマッチが使える
- whereブロックの中で関数が定義できる

## 3.4 let It Be
- letはwhereと違って式だが、ガード間で共有できない
- inなしでリスト内包表記の中で名前をつけるのにも使える
- リスト内包表記の`(w, h) <- xs`をジェネレータと呼ぶ
- ghciではin句を省略でき、以降参照可能になる

## 3.5 case式
- `case of`はどこでも使えるパターンマッチ

# 第4章 Hello再帰！
- Haskellでは命令形言語のように計算をどうやってするかを指定するのではなく、求めるものが何であるかを宣言して計算を行う

## 4.1 最高に最高！
- `maximum`を再帰で実装してみよう

## 4.2 さらにいくつかの再帰関数
- `replicate`, `take`, `reverse`, `repeat`, `zip`, `elem` を再帰で実装してみよう

## 4.3 クイック、ソート！
```haskell
quicksort = (Ord a) => [a] -> [a]
quicksort [] = []
quicksort (x:xs) =
  let smallerOrEqual = [a | a <- xs, a <= x]
      larger = [a | a <- xs, a > x]
  in quicksort smallerOrEqual ++ [x] ++ quicksort larger
```

## 4.4 再帰的に考える
- 再帰を使う際の定跡
  - まず基底部を見極める
  - より小さな部分問題へと分割する
  - 部分問題を再帰的に解く

# 第5章 高階関数
## 5.1 カリー化関数
- カリー化とは
  - 複数の引数を取る代わりに常にちょうど1つの引数を取るようにすること
- 関数を本来より少ない引数で呼び出すことで部分適用された関数が得られる
- 中置関数を部分適用するにはセクションを使う
  - 片側だけに値を置いて括弧で囲む

## 5.2 高階実演
- zipWithは2つリストを取り、最初の引数の関数で1つのリストにする
- `flip' f x y = f y x`

## 5.3 関数プログラマの道具箱
- mapは関数をすべての要素に適用する
- filterはTrueの要素のみを取り出す
- `takeWhile`
- `/=`は!=

## 5.4 ラムダ式
- ラムダ式の書き方
  - `(\a b -> (a * 30 + 3) / b)`
- 普通の関数と同じようにラムダ式でもパターンマッチができる
  - ただし、1つの引数に対して複数のパターンを定義できない

## 5.5 畳み込み、見込みアリ！
- 畳み込み関数は、2引数関数と畳込みに用いる値(アキュムレータ)の初期値、それに畳み込むリストを受け取ります
  - `foldl`は左畳み込み
  - `foldr`は右畳み込み
     - リストから新しいリストを作るときは普通右畳み込みを使う
     - 無限リストにも動作する
- `foldl1`, `foldr1`
- `scanl`と`scanr`は途中の状態をリストとして返す

## 5.6 $ を使った関数適用
- `$`は関数適用演算子
  - `f $ x = f x`
- `$`関数は最も低い優先順位を持つ
  - 括弧の数を少なくしたい時役に立つ

## 5.7 関数合成
- `.`は関数合成に使う
  - 関数合成は右結合
  - `f (g (z x))`は`(f . g . z) x`と等価
- 関数合成を上手く使うと引数を省略して書くことができる
  - これをポイントフリースタイルという
     - ポイントとは一時変数のこと

# 第6章 モジュール
## 6.1 モジュールをインポートする
- Preludeというモジュールはデフォルトでインポートされている
- `Data.Map`
  - `filter`や`null`などPreludeと同じ名前をたくさんエクスポートしている
- GHCiでimportをするときは`:m + Data.List`のようにする
- 特定の関数だけをimportすることができる
  - `import Data.List (nub, sort)`
- 特定の関数以外をimportすることができる
  - `import Data.List hiding (nub)`
- 名前の競合を避けるには修飾付きインポート（qualified インポート）を使う
  - `import qualified Data.Map`
  - `import qualified Data.Map as M`
- `.`がqualifiedされたモジュール名と関数名の間に空白を開けずにおかれるとインポートされた関数とみなされ、それ以外は関数合成として扱われる

## 6.2 標準モジュールの関数で問題を解く
- `Data.List`
  - `nub`: uniq
  - `words`: split(' ')
  - `group`: group (only connected)
  - `sort`
  - `tails`: tailを繰り返す
  - `isPrefixOf`
  - `any`
  - `isInfixOf`
  - `foldl'`, `foldl1`: 正格評価、stack overflowしない
  - `find`: find\_by
- `Data.Char`
  - `ord`: 文字->数, `chr`: 数->文字
  - `digitToInt`: to\_i
- `negate`: 負にする

## 6.3 キーから値へのマッピング
- `Data.Map`
  - 高速な連想リスト
  - `import qualified Data.Map as Map`する
  - `Map.fromList :: (Ord k) => [(k, v)] -> Map.Map k v`
     - 高速化のためにOrdが必要
  - `lookup`, `insert`, `size`
  - `fromListWith`: 重複時の関数定義

## 6.4 モジュールを作ってみよう
- moduleの宣言はexportする関数を指定する
- ディレクトリを切って名前空間を深くすることができる

### Geometry.hs

```hs
module Geometry
( sphereVolume
, sphereArea
, cubeVolume
, cubeArea
, cuboidArea
, cuboidVolume
) where

sphereVolume :: Float -> Float
sphereVolume radius = (4.0 / 3.0) * pi * (radius ^ 3)

sphereArea :: Float -> Float
sphereArea radisu = 4 * pi * (radius ^ 2)

cubeVolume :: Float -> Float
cubeVolume side = cuboidVolume side side side

cubeArea :: Float -> Float
cubeArea side = cuboidArea side side side

cuboidVolume :: Float -> Float -> Float -> Float
cuboidVolume a b c = rect a b * c

cuboidArea :: Float -> Float -> Float -> Float
cuboidArea a b c = rectArea a b * 2 + rectArea a c * 2 +
rectArea c b * 2

rectArea :: Float -> Float -> Float
rectArea a b = a * b
```

これをインポートするには、`import Geometry`を同じディレクトリから行う

### Geometry/Sphere.hs

```hs
module Geometry.Sphere
( volume
, area
) where

volume :: Float -> Float
volume radius = (4.0 / 3.0) * pi * (radius ^ 3)

area :: Float -> Float
area a b c = rectArea a b * 2 + rectArea a c * 2 + rectArea c b * 2

rectArea :: Float -> Float -> Float
rectArea a b = a * b
```

# 第7章 型や型クラスを自分で作ろう
## 7.1 新しいデータ型を定義する
- データ型の定義は値コンストラクタと`|`を使う

```haskell
data Bool = False | True
```

## 7.2 形づくる
- 値コンストラクタには引数を与えられる

```haskell
data Shape = Circle Float Float Float |
             Rectangle Float Float Float Float

area :: Shape -> Float
area (Circle _ _ r) = pi * r ^ 2
area (Rectangle x1 y1 x2 y2) = (abs $ x2 - x1) * (abs $ y2 - y1)
```

- モジュールと値コンストラクタはexportできる
  - `(..)`と指定すると全部exportできる

```haskell
module Shapes
( Point(..)
, shape(Rectangle, Circle)
) where
```

## 7.3 レコード構文
- レコード構文では、Showをderiveするときに表示がわかりやすくなる

```haskell
data Car = Car { company :: String
               , model :: String
               , year :: Int
               } deriving (Show)

Car {company="Ford", model="Mustang", year=1967}
```

## 7.4 型引数
- Maybeは型コンストラクタ
  - 型コンストラクタは型引数を取る

```haskell
data Maybe a = Nothing | Just a
```

## 7.5 インスタンスの自動導出
- 特定の型クラスのインスタンス宣言をderiveできる
  - Eq, Ord, Enum, Bounded, Show, Read
  - 各フィールドの型がEq型クラスのインスタンスでなければならない

```haskell
data Person = Person { fileName :: String
                     , lastName :: String
                     , age :: Int
                     } deriving (Eq)
```

## 7.6 型シノニム
- 型シノニムも型引数をとれる

```haskell
type String = [Char]
type AssocList k v = [(k, v)]
```

## 7.7 再帰的なデータ構造
- 再帰的なデータ構造を自分で定義することができる
  - `data List a = Empty | Cons a (List a) deriving (Show, Read, Eq, Ord)`
- 記号文字だけを使って関数に名前をつけると自動的に中置換数になる
  - 中置換数にする場合、値コンストラクタの名前はコロンで始まる必要がある
- `infixl`, `infixr`: 結合性宣言
  - :infoでわかる
- パターンマッチとは値コンストラクタをマッチさせることにほかならない
  - 8や'a'も値コンストラクタ
- Data.SetやData.Mapは平衡二分探索木
- Haskellには古い木と新しい木の部分構造のほとんどを共有する仕組みが備わっている

## 7.8 型クラス 中級講座
### クラス定義
```hs
class Eq a where
  (==) :: a -> a -> Bool
  (/=) :: a -> a -> Bool
  x == y = not (x /= y)
  x /= y = not (x == y)
```

関数定義の実体はなくてもよい。あるとデフォルト実装になる。

### インスタンス定義
```hs
instance Eq TrafficLight where
  Red == Red = True
  Green == Green = True
  Yellow == Yellow = True
  _ == _ = False
```

インスタンス宣言するのは型コンストラクタであってはならない

```hs
instance (Eq m) => Eq (Maybe m) where
  Just x == Just y = x == y
  Nothing == Nothing = True
  _ == _ = False
```

- 型クラスのインスタンスが何者か知りたければ:infoを使う

## 7.9 Yes と No の型クラス
```hs
class YesNo a where
  yesno :: a -> Bool
```

- `id`は引数を1つとって同じものを返すだけの標準ライブラリ関数

## 7.10 Functor 型クラス
- Functorは全体を写せるものの型クラス
- Maybeは型コンストラクタ、Maybe Intは具体型、Justは値コンストラクタ、Just 1はファンクター値
- `[]`もFunctor
- Functorは型引数を1つしか取れない

```hs
class Functor f where
  fmap :: (a -> b) -> f a -> f b
```

```hs
instance Functor [] where
  fmap = map
```

## 7.11 型を司るもの、種類
- 型コンストラクタもカリー化されているので部分適用すればよい
- :kで型の種類を調べられる
- 型の種類は型のラベルで、型は値のラベル

# 第8章 入出力
## 8.1 不順なものと純粋なものを分離する
- 関数は副作用を持つことを許されないが、隔てた世界でimpureな部分を扱うシステムがある

## 8.2 Hello, World!
- `IO ()`は意味のある返り値がないからダミーの値として空のタプルを返している
- `putStrLn`

## 8.3 I/Oアクション同士をまとめる
- `do`でIOアクションをまとめられる
  - `let`が使える
- do内だと`<-`でIOの中身を取り出せる
  - I/O外に不純なものが拡散しない
- `return`は制御構文ではなく、引数の値を持つIOになる

## 8.4 いくつかの便利な I/O関数
- `pubStr`, `putChar`, `print`
- `when`: TrueならIO, Falseならreturn ()
- `sequence`: 順に実行
- `mapM`: リストからIOを返すマップ
- `mapM_`: 値を捨てる
- `forever`: 永遠にIOを繰り返す
- `forM`: 引数の順序が逆

## 8.5 I/Oアクションおさらい
- IOの特別なところはmainの中に入っているとそれが実行されること

# 第9章 もっと入力、もっと出力
## 9.1 ファイルとストリーム
- `getLine`, `interact`
- `getContents`: 遅延IO, C-dで終了
- 遅延IOはpromiseになり、参照されるまで実行されない
- `lines`, `unlines`

## 9.2 ファイルの読み書き
- `openFile`
- `type FilePath = String`
- `data IOMode = ReadMode | WriteMode | AppendMode | ReadWritemode`
- `withFile`: 関数を受け取り、閉じる
- `bracket`: IOが失敗しても関数が実行されることを保証

## 9.3 ToDoリストを作る
- `System.IO.openTempFile`

```hs
import System.IO
import System.Directory
import Data.List
import Control.Exception

main = do
  contents <- readFile "todo.txt"
  let todoTasks = lines contents
      numberedTasks = zipWith (\n line -> show n ++ " - " ++ line) [0..] todoTasks

  putStrLn "These are your TODO items:"
  mapM_ putStrLn numberedTasks
  putStrLn "Which one do you want to delete?"
  numberString <- getLine
  let number = read numberString
      newTodoItems = unlines $ delete (todoTasks !! number) todoTasks
  bracketOnError (openTempFile "." "temp")
    (\(tempName, tempHandle) -> do
      hClose tempHandle
      removeFile tempName)
    (\(tempName, tempHandle) -> do
      hPutStr tempHandle newTodoItems
      hClose tempHandle
      removeFile "todo.txt"
      renameFile tempName "todo.txt")

```

## 9.4 コマンドライン引数
- `getArgs`

## 9.5 ToDoリストをもっと楽しむ
- ToDoに機能を追加する

## 9.6 ランダム性
- `System.Random`は乱数生成に必要なすべての関数をもっている
  - `StdGen`といういい感じの型があり、mkStdGenで作れる
- `random`はジェネレータを返し、`randoms`はリストを返す
- `randomR`は値のレンジを指定できる、`randomRs`もある
- 乱数のシードはIOで拾ってこないといけない
  - `System.Random.getStdGen`というグローバル乱数ジェネレータを使う
  - グローバル乱数ジェネレータは何度呼んでも同じ

## 9.7 bytestring
- `Data.ByteString`は正格で、一発で全部メモリに読む
- `Data.ByteString.Lazy`はチャンクごとに読み込み、残りはthunkになる
  - `pack`は64Kバイト間隔のチャンクにする。`unpack`が逆関数。
  - fromChuksは正格bytestringのリストを遅延bytestringにする
  - `Data.ByteString.Lazy.cons`でつなげる
- `readFile`や`hPutStr`の代わりに`B.readFile`や`B.hPutStr`を使うだけで高速になることがある

# 第10章 関数型問題解決法
## 10.1 逆ポーランド記法電卓
- RPNを畳み込みで実装する

## 10.2 ヒースロー空港からロンドンへ
- 畳み込みで最短経路を求める

# 第11章 ファンクターからアプリカティブファンクターへ
## 11.1 帰ってきたファンクター
TODO

## 11.2 ファンクター則
TODO

## 11.3 アプリカティブファンクターを使おう
TODO

## 11.4 アプリカティブの便利な関数
TODO

# 第12章 モノイド

# 第13章 モナドがいっぱい

# 第14章 もうちょっとだけモナド

# 第15章 Zipper
