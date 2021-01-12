# Iterative Algebraic Collision Attack in Java

## Getting Started

First of all, you need to obtain a copy of the source code and compile it into
an executable. Run the following commands to do this:

```
git clone git@github.com:filipvanlaenen/iacaj.git
cd iacaj
mvn clean compile assembly:single
```

If everything works well, you'll finda JAR file in the `target` directory with
all dependencies included. Let's test it out producing a simple Boolean function
ORing the first half of the input parameters with the second half:

```
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce OR 4
```

This should produce the following output:

```
v1 = i1 ∨ i5
v2 = i2 ∨ i6
v3 = i3 ∨ i7
v4 = i4 ∨ i8
o1 = v1
o2 = v2
o3 = v3
o4 = v4
```

The sections below explain how to use the tool to perform different actions.

## Produce a Hash Function

Use the `produce` command to produce a Boolean function for a cryptographic
hash function, or one of the trivial sample Boolean functions. The trivial
functions are added for testing purposes. Currently, the following functions can
be produced:

* OR
* SHA-256 (under development)

### OR

The following commands produce Boolean functions ORing the first half of the
input parameters with the second half:

```
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce OR
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce OR OR32.bf
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce OR OR32.java
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce OR 4
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce OR 4 OR4.bf
```

If no parameters are provided, a word length of 32 is used and the output is
printed out on the command line. If a file name is provided, the result will be
written to the file. If a numeric parameter is provided first, it will be used
as the word length. 


### SHA-256

To produce a full version of the SHA-256 hash function, and
have it printed out on the command line, use the following command:

```
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce SHA-256
```

For a reduced version of the hash function, add the number of rounds as the
second argument, e.g. 32:

```
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce SHA-256 32
```

If you specify a file name at the end, the Boolean function will be written to
that file:

```
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce SHA-256 SHA-256.bf
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce SHA-256 32 SHA-256-R32.bf
```

## Resolve a Boolean Function
