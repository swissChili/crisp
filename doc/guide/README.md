# Guide

Crisp is a loosely functional, interpreted programming language inspired
by Objective-C, Lisp, JavaScript, and Haskell. This document aims to be
a simple introduction to the language, and it's implementation.

## Installation

[Download the jar](/crisp/crisp.jar), or build from source with

```sh
$ lein uberjar
$ java -jar target/crisp-0.1.0-standalone.jar
```

## Basic Syntax

Crisp uses square brackets religiously. They are used for function calls,
and method calls on objects. 

```python
[print: "hello, world"]
```

Will print `hello, world` to the screen. This is calling the `print` function
with one argument, a string. It is important to note that arguments, like
everything in crisp, are evaluated lazily. This means that it is only evaluated
once it is needed, saving some time up front.

## Variables

Variables in Crisp are immutable, which means you can't change theri value after
you create them. Variables are created with the `let` function.

```python
[let name: "Jeff"]
[print: "Hello" name]
#=> Hello Jeff
[let name: "bob"]
#=> Runtime error
```

## Basic Types

Crisp has most of the types you're probably used to from any other language.
There are slight exceptions with how some of them function because of the lazy
nature of the language.

### Numbers

Numbers can be written literally as you'd expect: `123` `1.23` `0.123`
all work as you would expect. Integers are stored as 32 bit signed ints, and
decimals as 64 bit floats.

### Strings

Like most lisps, strings must be double-quoted. These are stored internally
as Clojure strings, so they support UTF-8 natively. `"This is a string"`.

### Booleans

Crisp booleans work exactly as expected. Anything except `nil`, `false`, and
0 is truthy. 

### Lambdas

Lambdas are an integral part of this language. Being functional (and lazy), functions
can be passed as arguments to functions, and manipulated the same way as other data.

The lambda syntax is as follows:

```py
(arg0 arg1 arg2): {
	# lambda body
	[print: arg0]
	# arg1 and 2 are not accessed, so are not
	# evaluated.
}
```

Function calls are also lazily evaluated, which means passing a function call as an
argument to a function will not evaluate that function until it is accessed in the
function it is passed to. This makes things like this possible:

```python
[let foo: (func): {
	[print: "before call"]
	func
	[print: "after call"]
}]
[foo: [print "function called"]]

# Output:
#   before call
#   function called
#   after call
```
