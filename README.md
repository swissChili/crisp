# crisp ![Circle CI](https://img.shields.io/circleci/project/github/swissChili/crisp.svg)

Objective-C meets Lisp in one weird language.

## Language

The language is very simple. There are only a handful of syntactic rules.

- `[object method: arg0 arg1]`: Call a method on an object
- `[function: arg0 arg1]`: Call a function (functions are also objects)
- `[function]`: Call a function with no arguments
- `"foo"`: A string literal (numbers work as expected)

## Options

None so far. Edit the `input` var in `core.clj` if you would like to test
different programs.

## Examples

```
[let a: "foo"]
[print: a]
//=> foo
```

## Docs

Docs are available in the `doc` directory. Build them with VuePress by running
```sh
$ yarn
$ yarn start
```
And opening `localhost:8080` in a browser. You can also just read the docs from
markdown on the GitLab site. Also, if the pipeline starts working, you will be
able to read the docs at [swisschili.github.io/crisp](https://swisschili.github.io/crisp). 

## License

Copyright © 2019 swissChili

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
