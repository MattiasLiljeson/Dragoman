Dragoman
========
Dragoman is a simple recursive-descent interpreter written in Java with a CLI written in Groovy.
It is inspired by Crenshaw's 'Lets build a compiler' and was originally written for an assignment in interpreter technology.
The interpreter is therefore simple and should be an easy read,
while still having support for quite a few simple language constructs.

Dragoman can be used to see how a simple interpreter can be written.
It has extensive options for visualising the interpretation while running through the CLI.
These visualization options make it well suited to describe the operation of a recurisve-descent interpreter or compiler.

Dragoman uses Göran Fries' p1 file format as input.
The p1 files are the result of lexical analysis of the source file and are in a block-level format.
Quite a few test programs in p1 format are included in the repo under `docs/Tester` and as unit tests.

To run
------
The CLI is built as a Java jar file and is invocated with java.
There are quite a few options, these can be listed with the `-h` or `--help` switch.
An example invocation can look like: `java -jar intercli.jar -i -l DEBUG docs/Tester/tst6.p1`

To build
--------
Java 7+ and Groovy are required.
Gradle is used to build the project.
As Gradle wrapper is used, there is no need to download and install it yourself though.

1. step into the `src` directory
2. Execute the command `./gradlew build jar intercli:uberjar` or `gradlew.bat build jar intercli:uberjar`
3. Execute the jar with `java -jar intercli/build/libs/intercli.jar -i <program>.p1`

To generate eclipse project files, run `./gradlew eclipse`.
The project can then be opened in eclipse and project can be poked around in.
There are quite a few unit tests in `src/inter/src/test`.
Many of these are modified programs with added "checkpoints" where values on the stack are stored.
These checkpoints can then be copmapred to refence values.
