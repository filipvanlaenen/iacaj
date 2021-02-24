package net.filipvanlaenen.iacaj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.filipvanlaenen.iacaj.ComplexityReport.Metric;

/**
 * Class keeping track of all performed attacks on a Boolean function.
 */
public class AttackRecords {
    public class AttackRecord {
        private final Set<InputParameter> inputParameters;
        private final BooleanConstraints constraints;
        private final ComplexityReport complexityReport;

        AttackRecord(Set<InputParameter> inputParameters, Set<BooleanConstraint> constraints,
                ComplexityReport complexityReport) {
            this.inputParameters = inputParameters;
            this.constraints = new BooleanConstraints(constraints);
            this.complexityReport = complexityReport;
        }

        Set<InputParameter> getInputParameters() {
            return inputParameters;
        }

        BooleanConstraints getConstraints() {
            return constraints;
        }

        ComplexityReport getComplexityReport() {
            return complexityReport;
        }
    }

    public class AttackLine {
        private final Map<BooleanConstraints, AttackRecord> attacks = new HashMap<BooleanConstraints, AttackRecord>();
        private final AttackLine predecessor;

        AttackLine() {
            this.predecessor = null;
        }

        AttackLine(AttackLine predecessor) {
            this.predecessor = predecessor;
        }

        BooleanConstraints findNextCollisionCandidate() {
            List<AttackRecord> parents = predecessor.getSortedExtensionPoints();
            for (AttackRecord parent : parents) {
                BooleanConstraints candidate = extendParent(parent);
                if (candidate != null) {
                    return candidate;
                }
            }
            return null;
        }

        private BooleanConstraints extendParent(AttackRecord parent) {
            List<InputParameter> inputParameters = new ArrayList<InputParameter>(parent.getInputParameters());
            inputParameters.sort(new Comparator<InputParameter>() {
                @Override
                public int compare(InputParameter ip0, InputParameter ip1) {
                    Long m0 = parent.getComplexityReport().getInputParameterValue(Metric.NumberOfExpressions, ip0);
                    Long m1 = parent.getComplexityReport().getInputParameterValue(Metric.NumberOfExpressions, ip1);
                    return m1.intValue() - m0.intValue();
                }
            });
            BooleanConstraints parentConstraints = parent.getConstraints();
            for (InputParameter inputParameter : inputParameters) {
                BooleanConstraints extensionWithFalse = parentConstraints.extend(inputParameter, "False");
                if (!attacks.containsKey(extensionWithFalse)) {
                    return extensionWithFalse;
                }
                BooleanConstraints extensionWithTrue = parentConstraints.extend(inputParameter, "True");
                if (!attacks.containsKey(extensionWithTrue)) {
                    return extensionWithTrue;
                }
            }
            return null;
        }

        private List<AttackRecord> getSortedExtensionPoints() {
            List<AttackRecord> extensionPoints = new ArrayList<AttackRecord>(attacks.values());
            // TODO sort
            return extensionPoints;
        }

        void add(BooleanFunction booleanFunction) {
            BooleanConstraints constraints = new BooleanConstraints(booleanFunction.getConstraints());
            AttackRecord record = new AttackRecord(booleanFunction.getInputParameters(),
                    booleanFunction.getConstraints(), new ComplexityReport(booleanFunction));
            attacks.put(constraints, record);
        }

        int size() {
            return attacks.size();
        }

        AttackLine getPredessor() {
            return predecessor;
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
    public AttackRecords(final BooleanFunction booleanFunction) {
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
    public void add(final BooleanFunction booleanFunction) {
        attackLines[booleanFunction.getNumberOfConstraints()].add(booleanFunction);
    }

    BooleanConstraints findNextCollisionCandidate() {
        List<AttackLine> sortedLines = sortAttackLines();
        for (AttackLine line : sortedLines) {
            BooleanConstraints candidate = line.findNextCollisionCandidate();
            if (candidate != null) {
                return candidate;
            }
        }
        return null;
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

    private List<AttackLine> sortAttackLines() {
        List<AttackLine> sortedLines = new ArrayList<AttackLine>(Arrays.asList(attackLines));
        sortedLines.remove(attackLines[0]);
        sortedLines.sort((Comparator<AttackLine>) new Comparator<AttackLine>() {
            private int compareEdges(AttackLine line0, AttackLine line1) {
                return 0; // TODO
            }

            private int compareOuts(AttackLine line0, AttackLine line1) {
                return 0; // TODO
            }

            private int compareMiddle(AttackLine line0, AttackLine line1) {
                return 0; // TODO
            }

            @Override
            public int compare(AttackLine line0, AttackLine line1) {
                boolean edge0 = line0.size() == 0 && line0.getPredessor().size() > 0;
                boolean edge1 = line1.size() == 0 && line1.getPredessor().size() > 0;
                boolean out0 = line0.getPredessor().size() == 0;
                boolean out1 = line1.getPredessor().size() == 0;
                if (edge0 && edge1) {
                    return compareEdges(line0, line1);
                } else if (edge0) {
                    return -1;
                } else if (edge1) {
                    return 1;
                } else if (out0 && out1) {
                    return compareOuts(line0, line1);
                } else if (out0) {
                    return 1;
                } else if (out1) {
                    return -1;
                } else {
                    return compareMiddle(line0, line1);
                }
            }
        });
        return sortedLines;
    }

}
