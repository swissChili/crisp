# Introduction to crisp

Crisp is a loosely functional, interpreted programming language inspired
by Objective-C, Lisp, JavaScript, and Haskell. This document aims to be
a simple introduction to the language, and it's implementation.

## Basic Syntax

Crisp uses square brackets religiously. They are used for function calls,
and method calls on objects. 

```m
[print: "hello, world"]
```

Will print `hello, world` to the screen. This is calling the `print` function
with one argument, a string. It is important to note that arguments, like
everything in crisp, are evaluated lazily. This means that it is only evaluated
once it is needed, saving some time up front.
