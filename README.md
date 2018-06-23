# Static analyser for Java written on Scala

## Usage

### Build form source
`mvn package`

### Run using jar
`java -jar done.jar [path to file]`

## Performing checks
1. Check if static final variable has low case in it's name
2. Vheck if If Else branches are the same
3. Check if method's argument is unused

## Output example
```java -jar done.jar Test.java```

```
If statement has equivalent branches! at (line 12,col 9)-(line 16,col 9)
Unused argument in in method test1 at (line 5,col 5)-(line 7,col 5)
Field badName is not in uppercase at (line 3,col 5)-(line 3,col 49)
```
