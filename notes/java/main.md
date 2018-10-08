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
-jarを指定すると、jarのmanifestにMain-Class:classnameが指定されている場合(されている必要がある)、それのmainが呼ばれる。
mainclassを指定した場合、そのクラスのmain関数が呼ばれる。そのクラスがpublicな必要はない。
