# Iterative Algebraic Collision Attack in Java

## Getting Started

First of all, you need to obtain a copy of the source code and compile it into
an executable. Run the following commands to do this:

```
git clone git@github.com:filipvanlaenen/iacaj.git
cd iacaj
mvn clean compile assembly:single
```

If everything works well, you'll a JAR file in the `target` directory with all
dependencies included.

## Produce a Hash Function

Use the `produce` command to produce the Boolean function for a cryptographic
hash function. To produce a full version of the SHA-256 hash function, and
have it printed out on the command line, use the following command:

```
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce SHA-256
```

For a reduced version of the hash function, add the number of rounds as the
second argument, e.g. 32:

```
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce SHA-256 32
```
