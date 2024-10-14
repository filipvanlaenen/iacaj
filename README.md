# Iterative Algebraic Collision Attack in Java

- [Getting Started](#getting-started)
- [Produce a Boolean Function](#produce-a-boolean-function)
  - [ADD](#add)
  - [AND](#and)
  - [OR](#or)
  - [ROTATE](#rotate)
  - [SHA-256](#sha-256)
  - [SHIFT](#shift)
  - [XOR](#xor)
- [Resolve a Boolean Function](#resolve-a-boolean-function)
- [Report on the Complexity of a Boolean Function](#report-on-the-complexity-of-a-boolean-function)
- [Attack a Boolean Function](#attack-a-boolean-function)

## Getting Started

First of all, you need to obtain a copy of the source code and compile it into an executable. Run the following commands
to do this:

```
git clone git@github.com:filipvanlaenen/iacaj.git
cd iacaj
mvn clean compile assembly:single
```

If everything works well, you'll finda JAR file in the `target` directory with all dependencies included. Let's test it
out producing a simple Boolean function ORing the first half of the input parameters with the second half:

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

## Produce a Boolean Function

Use the `produce` command to produce a Boolean function for a cryptographic hash function, or one of the trivial sample
Boolean functions. The trivial functions are added for testing purposes. Currently, the following functions can be
produced:

- [ADD](#add)
- [AND](#and)
- [OR](#or)
- [ROTATE](#rotate)
- [SHA-256](#sha-256)
- [SHIFT](#shift)
- [XOR](#xor)

### ADD

The following commands produce Boolean functions ADDing the first half of the input parameters with the second half:

```
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce ADD
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce ADD ADD32.bf
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce ADD ADD32.java
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce ADD 4
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce ADD 4 ADD4.bf
```

If no parameters are provided, a word length of 32 is used and the output is printed out on the command line. If a file
name is provided, the result will be written to the file. If a numeric parameter is provided first, it will be used as
the word length. 

### AND

The following commands produce Boolean functions ANDing the first half of the input parameters with the second half:

```
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce AND
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce AND AND32.bf
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce AND AND32.java
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce AND 4
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce AND 4 AND4.bf
```

If no parameters are provided, a word length of 32 is used and the output is printed out on the command line. If a file
name is provided, the result will be written to the file. If a numeric parameter is provided first, it will be used as
the word length. 

### OR

The following commands produce Boolean functions ORing the first half of the input parameters with the second half:

```
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce OR
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce OR OR32.bf
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce OR OR32.java
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce OR 4
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce OR 4 OR4.bf
```

If no parameters are provided, a word length of 32 is used and the output is printed out on the command line. If a file
name is provided, the result will be written to the file. If a numeric parameter is provided first, it will be used as
the word length. 

### ROTATE

The following commands produce Boolean functions rotating the input parameters to the right:

```
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce ROTATE
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce ROTATE ROTATE32.bf
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce ROTATE ROTATE32.java
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce ROTATE 4
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce ROTATE 4 ROTATE4.bf
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce ROTATE 4 1
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce ROTATE 4 1 ROTATE4-1.bf
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce ROTATE 4 -1
```

If no parameters are provided, a word length of 32 is used and the output is printed out on the command line. If a file
name is provided, the result will be written to the file. If a numeric parameter is provided first, it will be used as
the word length. A second numeric parameter will be used as the number of positions to rotate the input parameters.

### SHA-256

To produce a full version of the SHA-256 hash function, and have it printed out on the command line, use the following
command:

```
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce SHA-256
```

For a reduced version of the hash function, add the number of rounds as the second argument, e.g. 32:

```
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce SHA-256 32
```

If you specify a file name at the end, the Boolean function will be written to that file:

```
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce SHA-256 SHA-256.bf
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce SHA-256 32 SHA-256-R32.bf
```

### SHIFT

The following commands produce Boolean functions shifting the input parameters to the right:

```
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce SHIFT
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce SHIFT SHIFT32.bf
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce SHIFT SHIFT32.java
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce SHIFT 4
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce SHIFT 4 SHIFT4.bf
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce SHIFT 4 1
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce SHIFT 4 1 SHIFT4-1.bf
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce SHIFT 4 -1
```

If no parameters are provided, a word length of 32 is used and the output is printed out on the command line. If a file
name is provided, the result will be written to the file. If a numeric parameter is provided first, it will be used as
the word length. A second numeric parameter will be used as the number of positions to shift the input parameters.

### XOR

The following commands produce Boolean functions XORing the first half of the input parameters with the second half:

```
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce XOR
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce XOR XOR32.bf
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce XOR XOR32.java
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce XOR 4
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar produce XOR 4 XOR4.bf
```

If no parameters are provided, a word length of 32 is used and the output is printed out on the command line. If a file
name is provided, the result will be written to the file. If a numeric parameter is provided first, it will be used as
the word length. 

## Resolve a Boolean Function

The following commands read a Boolean function from an input file and try to resolve it:

```
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar resolve ADD32.bf
java -jar iacaj-1.0-SNAPSHOT-jar-with-dependencies.jar resolve ADD32.bf ADD32-resolved.bf
```

If no output file name is provided, the result is printed out on the command line.

## Report on the Complexity of a Boolean Function

## Attack a Boolean Function
