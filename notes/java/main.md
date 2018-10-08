# javaの起動方法

```
Usage: java [options] <mainclass> [args...]
           (to execute a class)
   or  java [options] -jar <jarfile> [args...]
           (to execute a jar file)
   or  java [options] -m <module>[/<mainclass>] [args...]
       java [options] --module <module>[/<mainclass>] [args...]
           (to execute the main class in a module)

 Arguments following the main class, -jar <jarfile>, -m or --module
 <module>/<mainclass> are passed as the arguments to main class.
```

`-m module` はJava 9のProject Jigsawによるものと思われるのであまり気にしなくて良い。
-jarを指定すると、jarのmanifestにMain-Class:classnameが指定されている場合(されている必要がある)、それのmainが呼ばれる。 (META-INF/MANIFEST.MF)
mainclassを指定した場合、そのクラスのmain関数が呼ばれる。そのクラスがpublicな必要はない。

## CLASSPATH
CLASSPATH環境変数を指定するとクラス探索対象を指定できる。 -cp, -classpath, --class-pathでもよさそう。
何も指定しない場合はカレントディレクトリを探す。従って、mainclassを指定する場合もカレントディレクトリでなければ-cpをつける必要がある。

## Unnamed Packages
package宣言がないパッケージを複数作ることができるが、そこからクラスをimportすることはできない。
`java <mainclass>` (classpath指定なし)で起動する場合、Unnamed Packagesがクラス探索対象に入るため、publicでないクラスが発見できる。
mainclassに指定するクラスはパッケージ下にあっても良いが、パッケージ名をディレクトリに変えてclasspathから探索できる場所に.classがないといけない

# クラス修飾子: public, final, abstract
## public
publicをつけない場合、現在のパッケージからしか参照できない。
mainclassに指定するクラスはpublicの必要はない。(ただし main() はpublic staticの必要がある)

変数と違ってprivateなる修飾子はなく、デフォルトでprivateになっている。
また、publicにした場合ファイル名を揃えないといけない。

## final
メソッドが全てfinalになる。メソッドがオーバーライドできなくなる。abstractと同時に指定できない。

## abstract
インスタンス化できなくなる。サブクラスを作るのを強制する用途と思われる。finalと同時に指定できない。

# 変数修飾子:

XXXX

# メソッド修飾子:

XXXX

## メソッド呼び出し
thisが必要なケース
static -> 同static

# 配列
宣言の仕方は2通りある。

```java
int a[];
int[] a; // 普通はこっち。デフォルトではnullが入っている。 int a[10] とかやっておけない
```

値の初期化はこのようにする

```java
int[][] box = new int[1][2];
```

compound statementが使える

```
String[] ss = new String[]{ "foo", "bar" };
String[] ss = { "foo", "bar" };
```
