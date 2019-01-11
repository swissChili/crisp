---
home: true
heroText: Crisp
tagline: A functional scripting language
actionText: Getting Started
actionLink: /guide/
features:
- title: Lazy
  details: Lazy-evaluation makes it easy to write expressive code
- title: Prototype Based
  details: Use prototypes to easily give objects state without sacrificing functionality
- title: JVM
  details: The interpreter runs on the JVM which means it can run on any platform.
footer: Copyright  2019 swissChili. Licensed under EPL-2.0
---
## Factorial Example
```python
[let fact: (n): {
	[if: [lt: n 2]
	   1
	   [mult: [fact: [sub: n 1]] n ]]}]

[print: "Factorial of 20 is" [fact: 20]]
#=> Factorial of 20 is 2432902008176640000
```

This is the documentation for the Crisp programming language. If you would like
to use the language, please read the [guide](guide/).

For API documentation, please consult the [dev docs](dev/).

## Implementation

Crisp is implemented in Clojure, using a parser combinator called Kern. It is an
interpreted language, and everything in the language is lazy-evaluated. Currently
there is no CLI available so there is no point in providing a `jar`. 

## Installation

Clone the repository from gitlab, and run `lein uberjar` to build a `jar` archive.
Or just run `lein run` to test it out.
