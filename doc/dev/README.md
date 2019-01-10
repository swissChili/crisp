# Developer Documentation

## Math

### `eq`

Checks if arguments are equal.

```py
[eq: 1 1 1]
#=> true
[eq: 2 2 3]
#=> false
```

### `add`

Adds arguments together. Arguments must be numbers.

```py
[add: 1 2 3]
#=> 6
[add: 1.5 21]
#=> 22.5
```

### `sub`, `mult`, `div`

All do basically what their names says. Each takes 2 or more numbers
as arguments and returns another number

## Logic

### `if`

Takes exactly three arguments. Evaluates the first, if it is true, it
evaluates and returns the second. Otherwise, the third.

```py
[if: true
	[print: "Is True"]
	[print: "Is False"]]
#=> Is True
```
Example demonstrating return:
```py
[let result:
	[if: true
		1
		2]]
[print: result]
#=> 1
```

## IO

### `print`

Prints it's arguments, evaluated to the screen.

```py
[print: "Hello, World" 1 2.0]
#=> Hello, World 1.0 2.0
```

### `debug`

Prints it's arguments unevaluated to the screen. In other words, this prints
the AST that represents it's arguments. This is printed in Clojure form. If
it prooves useful JSON output may be implemented.
