package net.filipvanlaenen.iacaj.cli;

import java.util.regex.Pattern;

import net.filipvanlaenen.iacaj.BooleanFunction;
import net.filipvanlaenen.iacaj.producer.Sha256Producer;

/**
 * Class implementing a command line interface.
 */
public final class CommandLineInterface {
    /**
     * The main entry point for the command line interface.
     *
     * @param args The arguments.
     */
    public static void main(final String... args) {
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
            void execute(final String[] args) {
                String hashFunction = args[1];
                Sha256Producer producer;
                if (args.length > 2) {
                    boolean hasNumberOfRounds = Pattern.matches("\\d+", args[2]);
                    if (hasNumberOfRounds) {
                        producer = new Sha256Producer(Integer.parseInt(args[2]));
                    } else {
                        producer = new Sha256Producer();
                    }
                } else {
                    producer = new Sha256Producer();
                }
                if (hashFunction.equals("SHA-256")) {
                    BooleanFunction bf = producer.produce();
                    System.out.println(bf);
                } else {
                    System.out.println("Unknown hash function " + hashFunction + ".");
                }
            }
        };

        /**
         * Executes the command, passing the arguments from the command line.
         *
         * @param args The arguments from the command line.
         */
        abstract void execute(String[] args);
    }
}
