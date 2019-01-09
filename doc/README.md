# Crisp

This is the documentation for the Crisp programming language. If you would like
to use the language, please read the [guide](guide/).

For API documentation, please consult the [dev docs](dev/).

## Implementation

Crisp is implemented in Clojure, using a parser combinator called Kern. It is an
interpreted language, and everything in the language is lazy-evaluated. Currently
there is no CLI available so there is no point in providing a `jar`. 

## Installation

Clone the repository from gitlab, and run `lein uberjar` to build a `jar` archive.
