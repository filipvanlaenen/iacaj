package net.filipvanlaenen.iacaj.cli;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import net.filipvanlaenen.iacaj.BooleanFunction;
import net.filipvanlaenen.iacaj.producer.Sha256Producer;

/**
 * Class implementing a command line interface.
 */
public final class CommandLineInterface {
    /**
     * The magic number 3.
     */
    private static final int THREE = 3;

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
        System.out.println("  produce <hash-function> [<no-of-rounds>] [<file-name>]");
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
         * Command to produce a specific hash function, possibly with a specified number
         * of rounds, and export it to a file.
         */
        Produce {
            @Override
            void execute(final String[] args) throws IOException {
                String hashFunction = args[1];
                Sha256Producer producer;
                String fileName = null;
                if (args.length > 2) {
                    boolean hasNumberOfRounds = Pattern.matches("\\d+", args[2]);
                    if (hasNumberOfRounds) {
                        producer = new Sha256Producer(Integer.parseInt(args[2]));
                        if (args.length > THREE) {
                            fileName = args[THREE];
                        }
                    } else {
                        producer = new Sha256Producer();
                        fileName = args[2];
                    }
                } else {
                    producer = new Sha256Producer();
                }
                if (hashFunction.equals("SHA-256")) {
                    BooleanFunction bf = producer.produce();
                    outputBooleanFunction(bf, fileName);
                } else {
                    System.out.println("Unknown hash function " + hashFunction + ".");
                }
            }
        },
        /**
         * Command to read in a Boolean function from a file, resolve it, and export it
         * to a file.
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
         * Outputs a Boolean function. If the file name is <code>null</code>, then the
         * Boolean function is written to <code>stdout</code>. Otherwise, the Boolean
         * function is written to a file, with the format depending on the extension of
         * the file name.
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
