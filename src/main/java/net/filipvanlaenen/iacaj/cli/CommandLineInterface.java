package net.filipvanlaenen.iacaj.cli;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import net.filipvanlaenen.iacaj.Attack;
import net.filipvanlaenen.iacaj.AttackResult;
import net.filipvanlaenen.iacaj.BooleanFunction;
import net.filipvanlaenen.iacaj.ComplexityReport;
import net.filipvanlaenen.iacaj.NoCollisionFoundYet;
import net.filipvanlaenen.iacaj.builders.AddFunctionBuilder;
import net.filipvanlaenen.iacaj.builders.BasicVectorialFunctionBuilder;
import net.filipvanlaenen.iacaj.builders.Md5FunctionBuilder;
import net.filipvanlaenen.iacaj.builders.RotationFunctionBuilder;
import net.filipvanlaenen.iacaj.builders.ShiftFunctionBuilder;
import net.filipvanlaenen.iacaj.builders.VectorialFunctionBuilder;
import net.filipvanlaenen.iacaj.expressions.Operator;
import net.filipvanlaenen.iacaj.expressions.VectorialFunction;
import net.filipvanlaenen.nombrajkolektoj.integers.ModifiableOrderedIntegerCollection;

/**
 * Class implementing a command line interface.
 */
public final class CommandLineInterface {
    /**
     * The default word length.
     */
    private static final int DEFAULT_WORD_LENGTH = 32;

    /**
     * The main entry point for the command line interface.
     *
     * @param args The arguments.
     * @throws IOException Thrown if something related to IO goes wrong.
     */
    public static void main(final String... args) throws IOException {
        if (args.length < 1) {
            printUsage();
        }
        String capitalizedArgument = args[0].substring(0, 1).toUpperCase() + args[0].substring(1);
        Command command = Command.valueOf(capitalizedArgument);
        if (command == null) {
            printUsage();
        }
        command.execute(args);
    }

    /**
     * Prints the usage to the command line.
     */
    private static void printUsage() {
        System.out.println("Usage:");
        System.out.println("  attack <file-name> [<file-name>]");
        System.out.println("  produce <function> <numeric-parameter>* [<file-name>]");
        System.out.println("    produce ADD [<word-length>] [<file-name>]");
        System.out.println("    produce AND [<word-length>] [<file-name>]");
        System.out.println("    produce MD5 [<no-of-rounds>] [<file-name>]");
        System.out.println("    produce OR [<word-length>] [<file-name>]");
        System.out.println("    produce ROTATE [<word-length> [<number-of-positions>]] [<file-name>]");
        System.out.println("    produce SHIFT [<word-length> [<number-of-positions>]] [<file-name>]");
        System.out.println("    produce XOR [<word-length>] [<file-name>]");
        System.out.println("  report <file-name> [<file-name>]");
        System.out.println("  resolve <file-name> [<file-name>]");
    }

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private CommandLineInterface() {
    }

    /**
     * Enumeration with the available commands.
     */
    enum Command {
        /**
         * Command to read in a Boolean function from a file and find a collision.
         */
        Attack {
            @Override
            void execute(final String[] args) throws IOException {
                String inputFileName = args[1];
                String outputFileName = null;
                if (args.length > 2) {
                    outputFileName = args[2];
                }
                BooleanFunction bf = BooleanFunction.parse(readFile(inputFileName));
                Attack attack = new Attack(bf);
                AttackResult attackResult = attack.perform();
                while (attackResult instanceof NoCollisionFoundYet) {
                    System.out.println(attackResult.toString());
                    attackResult = attack.perform();
                }
                if (outputFileName == null) {
                    System.out.println(attackResult.toString());
                } else {
                    writeFile(outputFileName, attackResult.toString());
                }
            }
        },
        /**
         * Command to produce a specific hash function, possibly with a specified number of rounds, and export it to a
         * file.
         */
        Produce {
            @Override
            void execute(final String[] args) throws IOException {
                String function = args[1];
                ModifiableOrderedIntegerCollection parameters = ModifiableOrderedIntegerCollection.empty();
                String fileName = null;
                int i = 2;
                while (i < args.length) {
                    String parameterString = args[i];
                    boolean isNumeric = Pattern.matches("-?\\d+", parameterString);
                    if (isNumeric) {
                        parameters.add(Integer.parseInt(parameterString));
                    } else {
                        fileName = parameterString;
                    }
                    i++;
                }
                VectorialFunctionBuilder builder = null;
                int wordLength = parameters.isEmpty() ? DEFAULT_WORD_LENGTH : parameters.getAt(0);
                if (function.equals("ADD")) {
                    AddFunctionBuilder thisBuilder = new AddFunctionBuilder();
                    thisBuilder.outputVectorWidth(wordLength);
                    builder = thisBuilder;
                } else if (function.equals("AND")) {
                    BasicVectorialFunctionBuilder thisBuilder = new BasicVectorialFunctionBuilder();
                    thisBuilder.outputVectorWidth(wordLength);
                    thisBuilder.operator(Operator.AND);
                    builder = thisBuilder;
                } else if (function.equals("MD5")) {
                    Md5FunctionBuilder thisBuilder = new Md5FunctionBuilder();
                    if (!parameters.isEmpty()) {
                        thisBuilder.setNumberOfRounds(parameters.getAt(0));
                    }
                    builder = thisBuilder;
                } else if (function.equals("OR")) {
                    BasicVectorialFunctionBuilder thisBuilder = new BasicVectorialFunctionBuilder();
                    thisBuilder.outputVectorWidth(wordLength);
                    thisBuilder.operator(Operator.OR);
                    builder = thisBuilder;
                } else if (function.equals("ROTATE")) {
                    RotationFunctionBuilder thisBuilder = new RotationFunctionBuilder();
                    thisBuilder.outputVectorWidth(wordLength);
                    thisBuilder.rotateRight(parameters.size() > 1 ? parameters.getAt(1) : 0);
                    builder = thisBuilder;
                } else if (function.equals("SHIFT")) {
                    ShiftFunctionBuilder thisBuilder = new ShiftFunctionBuilder();
                    thisBuilder.outputVectorWidth(wordLength);
                    thisBuilder.shiftRight(parameters.size() > 1 ? parameters.getAt(1) : 0);
                    builder = thisBuilder;
                } else if (function.equals("XOR")) {
                    BasicVectorialFunctionBuilder thisBuilder = new BasicVectorialFunctionBuilder();
                    thisBuilder.outputVectorWidth(wordLength);
                    thisBuilder.operator(Operator.XOR);
                    builder = thisBuilder;
                }
                builder.inputVectorName("i");
                builder.outputVectorName("o");
                VectorialFunction vectorialFunction = builder.build();
                vectorialFunction = vectorialFunction.simplify(builder.getOutputVector());
                String output = vectorialFunction.toString();
                if (fileName == null) {
                    System.out.println(output);
                } else {
                    writeFile(fileName, output);
                }
            }
        },
        /**
         * Command to read in a Boolean function from a file and produce a report on its complexity.
         */
        Report {
            @Override
            void execute(final String[] args) throws IOException {
                String inputFileName = args[1];
                String outputFileName = null;
                if (args.length > 2) {
                    outputFileName = args[2];
                }
                BooleanFunction bf = BooleanFunction.parse(readFile(inputFileName));
                ComplexityReport complexityReport = new ComplexityReport(bf);
                if (outputFileName == null) {
                    System.out.println(complexityReport.toString());
                } else {
                    writeFile(outputFileName, complexityReport.toYaml());
                }
            }
        },
        /**
         * Command to read in a Boolean function from a file, resolve it, and export it to a file.
         */
        Resolve {
            @Override
            void execute(final String[] args) throws IOException {
                String inputFileName = args[1];
                String outputFileName = null;
                if (args.length > 2) {
                    outputFileName = args[2];
                }
                BooleanFunction bf = BooleanFunction.parse(readFile(inputFileName));
                bf.resolve();
                outputBooleanFunction(bf, outputFileName);
            }
        };

        /**
         * Executes the command, passing the arguments from the command line.
         *
         * @param args The arguments from the command line.
         * @throws IOException Thrown if something related to IO goes wrong.
         */
        abstract void execute(String[] args) throws IOException;

        /**
         * Outputs a Boolean function. If the file name is <code>null</code>, then the Boolean function is written to
         * <code>stdout</code>. Otherwise, the Boolean function is written to a file, with the format depending on the
         * extension of the file name.
         *
         * @param bf       The Boolean function.
         * @param fileName The name of the file, can be <code>null</code>.
         * @throws IOException Thrown if an exception occurs related to IO.
         */
        private static void outputBooleanFunction(final BooleanFunction bf, final String fileName) throws IOException {
            if (fileName == null) {
                System.out.println(bf);
            } else if (fileName.endsWith(".java")) {
                writeFile(fileName, bf.toJavaString());
            } else {
                writeFile(fileName, bf.toString());
            }
        }

        /**
         * Utility method to read a file into an array of strings.
         *
         * @param fileName The name of the file to read from.
         * @return The content of the file, as an array of strings.
         * @throws IOException Thrown if an exception occurs related to IO.
         */
        private static String[] readFile(final String fileName) throws IOException {
            return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8).toArray(new String[] {});
        }

        /**
         * Utility method to write a string to a file.
         *
         * @param fileName The name for the file.
         * @param content  The string to be written to the file.
         * @throws IOException Thrown if an exception occurs related to IO.
         */
        private static void writeFile(final String fileName, final String content) throws IOException {
            Files.writeString(Paths.get(fileName), content, StandardCharsets.UTF_8);
        }

    }
}
