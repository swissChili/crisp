# Developer Documentation

## IO

**`print`:**

Prints it's arguments, evaluated to the screen.

```py
[print: "Hello, World" 1 2.0]
#=> Hello, World 1.0 2.0
```

**`debug`:**

Prints it's arguments unevaluated to the screen. In other words, this prints
the AST that represents it's arguments. This is printed in Clojure form. If
it prooves useful JSON output may be implemented.
