# Installation

## Manual

There are `jar`s available for each major release on [gitlab](https://gitlab.com/swissChili/crisp).
You can manually install this on your platform by either running `java -jar /path/to/jar file.crisp`
every time you'd like to run a program, or by writing a script to execute it for you. There is such
a script provided for GNU/Linux based systems.

## Automatic

Run these commands to download and install the bootstrap installer to your `$PATH`.

```shell
$ sudo curl swisschili.gitlab.io/crisp/install.sh > /usr/bin/crisp
$ sudo chmod +x /usr/bin/crisp
$ crisp
Installing Jar
```
