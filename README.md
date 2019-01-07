# crisp

Objective-C meets Lisp in one weird language.

## Language

The language is very simple. There are only a handful of syntactic rules.

- `[object method: arg0 arg1]`: Call a method on an object
- `[function: arg0 arg1]`: Call a function (functions are also objects)
- `[function]`: Call a function with no arguments
- `"foo"`: A string literal (numbers work as expected)

## Options

None so far

## Examples

*Does not currently run:*

```
[let a: "foo"]
[a print]
//=> foo
```

## License

Copyright © 2019 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
