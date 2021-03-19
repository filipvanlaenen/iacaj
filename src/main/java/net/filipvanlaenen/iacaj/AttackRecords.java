package net.filipvanlaenen.iacaj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class keeping track of all performed attacks on a Boolean function.
 */
public class AttackRecords {
    /**
     * Class representing an attack line, i.e. a set of attack records with the same
     * number of Boolean constraints.
     */
    public class AttackLine {
        /**
         * The attacks records, mapped by their constraints.
         */
        private final Map<BooleanConstraints, AttackRecord> attackRecords;
        /**
         * The predecessor of the attack line.
         */
        private final AttackLine predecessor;

        /**
         * Creates an attack line without a predecessor.
         */
        AttackLine() {
            this(null);
        }

        /**
         * Creates a new attack line with a predecessor.
         *
         * @param predecessor The predecessor of the new attack line.
         */
        AttackLine(final AttackLine predecessor) {
            this.predecessor = predecessor;
            attackRecords = new HashMap<BooleanConstraints, AttackRecord>();
        }

        /**
         * Adds a Boolean function to the attack line. Note that the Boolean function is
         * not added, but an attack record created from it.
         *
         * @param booleanFunction The Boolean function to be added.
         */
        void add(final BooleanFunction booleanFunction) {
            AttackRecord attackRecord = new AttackRecord(booleanFunction);
            attackRecords.put(attackRecord.getConstraints(), attackRecord);
        }

        /**
         * Extends the Boolean constraints of a parent attack record with a new
         * constraint, such that the result can be used as the next collision candidate.
         * Returns <code>null</code> if none can be found.
         *
         * @param parent The parent attack record to extend.
         * @return A Boolean constraints instance that is the next collision candidate
         *         extended fro the parent attack record.
         */
        private BooleanConstraints extendParent(final AttackRecord parent) {
            BooleanConstraints parentConstraints = parent.getConstraints();
            for (InputParameter inputParameter : parent.getPrioritizedInputParameters()) {
                BooleanConstraints extensionWithFalse = parentConstraints.extend(inputParameter, "False");
                if (!attackRecords.containsKey(extensionWithFalse)) {
                    return extensionWithFalse;
                }
                BooleanConstraints extensionWithTrue = parentConstraints.extend(inputParameter, "True");
                if (!attackRecords.containsKey(extensionWithTrue)) {
                    return extensionWithTrue;
                }
            }
            for (InputParameter[] inputParameterPair : parent.getPrioritizedInputParameterPairs()) {
                BooleanConstraints extensionWithEquality = parentConstraints.extend(inputParameterPair[0],
                        inputParameterPair[1].getName());
                if (!attackRecords.containsKey(extensionWithEquality)) {
                    return extensionWithEquality;
                }
                BooleanConstraints extensionWithOpposition = parentConstraints.extend(inputParameterPair[0],
                        "¬" + inputParameterPair[1].getName());
                if (!attackRecords.containsKey(extensionWithOpposition)) {
                    return extensionWithOpposition;
                }
            }
            return null;
        }

        /**
         * Returns the next collision candidate for this attack line. Returns
         * <code>null</code> if no collision candidates for this attack line can be
         * found.
         *
         * @return The next collision candidate for this attack line, or null.
         */
        BooleanConstraints findNextCollisionCandidate() {
            List<AttackRecord> parents = predecessor.getPrioritizedAttackRecords();
            for (AttackRecord parent : parents) {
                BooleanConstraints candidate = extendParent(parent);
                if (candidate != null) {
                    return candidate;
                }
            }
            return null;
        }

        /**
         * Returns the predecessor for this attack line.
         *
         * @return The predecessor for this attack line.
         */
        AttackLine getPredessor() {
            return predecessor;
        }

        /**
         * Returns a prioritized list of attack records.
         *
         * @return A prioritized list of attack records.
         */
        private List<AttackRecord> getPrioritizedAttackRecords() {
            List<AttackRecord> prioritizedAttackRecords = new ArrayList<AttackRecord>(attackRecords.values());
            prioritizedAttackRecords.sort(new Comparator<AttackRecord>() {
                @Override
                public int compare(final AttackRecord attackRecord0, final AttackRecord attackRecord1) {
                    return 0;
                }
            });
            return prioritizedAttackRecords;
        }

        /**
         * Returns the size of the attack line, i.e. the number of attack records stored
         * in this attack line.
         *
         * @return The size of the attack line.
         */
        int size() {
            return attackRecords.size();
        }
    }

    /**
     * The attack lines in which the resolved Boolean functions are stored by number
     * of constraints.
     */
    private final AttackLine[] attackLines;

    /**
     * Constructor taking a Boolean function as its parameter. It is assumed that
     * the Boolean function has no constraints.
     *
     * @param booleanFunction The Boolean function to attack.
     */
    AttackRecords(final BooleanFunction booleanFunction) {
        attackLines = new AttackLine[booleanFunction.getNumberOfInputParameters()];
        attackLines[0] = new AttackLine();
        for (int i = 1; i < attackLines.length; i++) {
            attackLines[i] = new AttackLine(attackLines[i - 1]);
        }
        add(booleanFunction);
    }

    /**
     * Adds a Boolean function to the attack records. It is assumed that the Boolean
     * function is already resolved.
     *
     * @param booleanFunction The Boolean function to be added to the attack
     *                        records.
     */
    void add(final BooleanFunction booleanFunction) {
        attackLines[booleanFunction.getNumberOfConstraints()].add(booleanFunction);
    }

    /**
     * Finds a set of Boolean constraints that is the next collision candidate.
     * Returns <code>null</code> if none can be found.
     *
     * @return A BooleanConstraints instance representing the next collision
     *         candidate.
     */
    BooleanConstraints findNextCollisionCandidate() {
        List<AttackLine> prioritizedAttackLines = getPrioritizedAttackLines();
        for (AttackLine line : prioritizedAttackLines) {
            BooleanConstraints candidate = line.findNextCollisionCandidate();
            if (candidate != null) {
                return candidate;
            }
        }
        return null;
    }

    /**
     * Sorts the attack lines according to priority: edges first, then regular
     * lines, and lines with empty predecessors at the end. Edges are lines that are
     * empty, but with a predecessor that is not empty.
     *
     * @return A prioritized list of the attack lines.
     */
    private List<AttackLine> getPrioritizedAttackLines() {
        List<AttackLine> prioritizedAttackLines = new ArrayList<AttackLine>(Arrays.asList(attackLines));
        prioritizedAttackLines.remove(attackLines[0]);
        prioritizedAttackLines.sort((Comparator<AttackLine>) new Comparator<AttackLine>() {
            @Override
            public int compare(final AttackLine line0, final AttackLine line1) {
                boolean isEdge0 = isEdge(line0);
                boolean isEdge1 = isEdge(line1);
                boolean isPredecessorEmpty0 = isPredecessorEmpty(line0);
                boolean isPredecessorEmpty1 = isPredecessorEmpty(line1);
                if (isEdge0 && isEdge1) {
                    return 0;
                } else if (isEdge0) {
                    return -1;
                } else if (isEdge1) {
                    return 1;
                } else if (isPredecessorEmpty0 && isPredecessorEmpty1) {
                    return 0;
                } else if (isPredecessorEmpty0) {
                    return 1;
                } else if (isPredecessorEmpty1) {
                    return -1;
                } else {
                    return compareRegularLines(line0, line1);
                }
            }

            private int compareRegularLines(final AttackLine line0, final AttackLine line1) {
                return 0;
            }

            private boolean isEdge(final AttackLine line) {
                return line.size() == 0 && line.getPredessor().size() > 0;
            }

            private boolean isPredecessorEmpty(final AttackLine line0) {
                return line0.getPredessor().size() == 0;
            }
        });
        return prioritizedAttackLines;
    }

    /**
     * Calculates the number of attacks stored.
     *
     * @return The number of attacks stored.
     */
    int size() {
        int result = 0;
        for (AttackLine attackLine : attackLines) {
            result += attackLine.size();
        }
        return result - 1;
    }
}
