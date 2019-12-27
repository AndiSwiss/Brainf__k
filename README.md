# Brainf__k - a Java interpreter for the programming language "Brainfuck"

### Author
Andreas Ambühl, [https://andiswiss.ch](https://andiswiss.ch)

### General
This is a Java interpreter for the programming language "Brainfuck", which is an amazing turing-complete
programming language. See [https://en.wikipedia.org/wiki/Brainfuck](https://en.wikipedia.org/wiki/Brainfuck) for details.

This programming language consists of 8 instructions:

-  \>   Increment data pointer so that it points to next location in memory.
-  <   Decrement data pointer so that it points to previous location in memory.
-  \+   Increment the byte pointed by data pointer by 1. If it is already at its maximum value, 255, then new value will be 0.
-  \-   Decrement the byte pointed by data pointer by 1. If it is at its minimum value, 0, then new value will be 255.
-  .   Output the character represented by the byte at the data pointer.
-  ,   Read one byte and store it at the memory location pointed by data pointer.
-  \[   If the byte pointed by data pointer is zero, then move instruction pointer to next matching ']',
otherwise move instruction pointer to next command.
-  ]   If the byte pointed by data pointer is non-zero, then move instruction pointer to previous matching '[' command,
otherwise to next command.

### Compiling and running the application
Just download the project in to your idea and run the maven-imports.

After that, you can use the program:

- Run the tests in /test/java/ch.andiswiss. There you can see some functionality and how to use Brain__k.
You can run all tests via Maven -> Lifecycle -> test
- Write code which calls the class /main/java/Brainf__k.java

### Feature list
- Full implementation of an interpreter, which can run Brainfuck-code
- Implementation and usage of a custom data-class "ByteArr"

### License
BSD 3-Clause License. Copyright (c) 2019, Andreas Ambühl, [https://www.andiswiss.ch](https://www.andiswiss.ch)

See complete license in the [LICENSE.txt](LICENSE.txt)-file.
