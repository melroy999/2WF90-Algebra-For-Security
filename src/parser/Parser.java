package parser;

import parser.tree.OperatorNode;
import parser.tree.TreeNode;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class Parser {
    /**
     * Changes the inputted string representation of a polynomial to a Tree object.
     *
     * @param toParse: The string to convert to polynomial.
     * @return The tree of components.
     */
    public static TreeNode parse(String toParse) {
        //clean up the inserted string.
        toParse = toParse.trim();
        toParse = toParse.toUpperCase().replaceAll("[A-Z]", "X");

        //remove the fancy symbols.
        toParse = toParse.replaceAll("X⁰", "X^0");
        toParse = toParse.replaceAll("X¹", "X^1");
        toParse = toParse.replaceAll("X²", "X^2");
        toParse = toParse.replaceAll("X³", "X^3");
        toParse = toParse.replaceAll("X⁴", "X^4");
        toParse = toParse.replaceAll("X⁵", "X^5");
        toParse = toParse.replaceAll("X⁶", "X^6");
        toParse = toParse.replaceAll("X⁷", "X^7");
        toParse = toParse.replaceAll("X⁸", "X^8");
        toParse = toParse.replaceAll("X⁹", "X^9");

        //change back solo superscripts.
        toParse = toParse.replaceAll("⁰", "0");
        toParse = toParse.replaceAll("¹", "1");
        toParse = toParse.replaceAll("²", "2");
        toParse = toParse.replaceAll("³", "3");
        toParse = toParse.replaceAll("⁴", "4");
        toParse = toParse.replaceAll("⁵", "5");
        toParse = toParse.replaceAll("⁶", "6");
        toParse = toParse.replaceAll("⁷", "7");
        toParse = toParse.replaceAll("⁸", "8");
        toParse = toParse.replaceAll("⁹", "9");

        //clean up the inserted string.
        toParse = toParse.replaceAll("((?<=[0-9])\\*X)", "X");
        toParse = toParse.replaceAll("X(?=[0-9])", "X*");
        toParse = toParse.replaceAll("X(?!\\^)", "X^1");
        toParse = toParse.replaceAll("[^0-9\\+\\-*()^]", "");
        toParse = toParse.replace("+^", "+1^");
        toParse = toParse.replace("-^", "-1^");
        toParse = toParse.replace("*^", "*1^");
        toParse = toParse.replace("(^", "(1^");
        toParse = toParse.startsWith("^") ? "1" + toParse : toParse;

        //make sure the brackets get the correct syntax.
        String[] split = toParse.split("\\(");
        for (int i = 1; i < split.length; i++) {
            split[i] = "(" + split[i];
            if (split[i - 1].equals("")) {
                continue;
            }
            char c = split[i - 1].charAt(split[i - 1].length() - 1);
            if (Character.isDigit(c)) {
                split[i - 1] = split[i - 1] + "*";
            } else if (c == '-') {
                split[i - 1] = split[i - 1] + "1*";
            } else if (c == ')') {
                split[i - 1] = split[i - 1] + "*";
            }
        }

        //merge it again.
        toParse = "";
        for (String aSplit : split) {
            toParse = toParse + aSplit;
        }

        //make sure subtraction has correct syntax.
        split = toParse.split("\\-");
        for (int i = 1; i < split.length; i++) {
            split[i] = "-" + split[i];
            if (!split[i - 1].equals("")) {
                char c = split[i - 1].charAt(split[i - 1].length() - 1);
                if (c == ')' || Character.isDigit(c)) {
                    split[i - 1] = split[i - 1] + "+";
                }
            }
        }

        //merge it again.
        toParse = "";
        for (String aSplit : split) {
            toParse = toParse + aSplit;
        }

        //return the string converted to a tree
        return makeTree("" + toParse + "");
    }

    /**
     * Convert the preformatted string to polynomial.
     *
     * @param s: The string to convert to polynomial.
     * @return The polynomial in the format of a string.
     */
    public static TreeNode makeTree(final String s) {
        //holds the current term, as we might have numbers not fitting in 0-9
        String currentTerm = "";
        //we process everything as characters.
        char[] chars = s.toCharArray();

        //The array of nodes.
        ArrayList<TreeNode> nodes = convertToTreeNodeArray(currentTerm, chars);

        //change the subtractions into MinusNodes.
        for (int i = 0; i < nodes.size(); i++) {
            TreeNode t = nodes.get(i);
            //if we have a TreeNode with the value -.
            if (t.getValue().equals("-")) {
                if (i + 1 < nodes.size()) {
                    //the first following node.
                    TreeNode next = nodes.get(i + 1);
                    if (!(next instanceof OperatorNode)) {
                        t.setLeftNode(next);
                    }
                    //remove the next node from the ArrayList, as we just merged it.
                    nodes.remove(i + 1);
                }
            }
        }

        //process everything else, including brackets.
        return process(nodes);
    }

    /**
     * Convert string to array of TreeNodes.
     *
     * @param currentTerm: The term we are on.
     * @param chars:       Array of chars.
     * @return array of TreeNodes.
     */
    private static ArrayList<TreeNode> convertToTreeNodeArray(String currentTerm, char[] chars) {
        ArrayList<TreeNode> nodes = new ArrayList<TreeNode>();
        //Split up the string into multiple TreeNodes:
        for (char c : chars) {
            if (c == '+' | c == '*' | c == '(' | c == ')') {
                if (!currentTerm.equals("")) {
                    nodes.add(new TreeNode(currentTerm));
                }
                nodes.add(new OperatorNode("" + c));
                currentTerm = "";
            } else if (c == '-') {
                nodes.add(new TreeNode("-"));
                currentTerm = "";
            } else {
                currentTerm = currentTerm + c;
            }
        }
        //add the last TreeNode, if needed.
        if (!currentTerm.equals("")) {
            nodes.add(new TreeNode(currentTerm));
        }
        return nodes;
    }

    /**
     * Process the operators in the TreeNode array.
     *
     * @param nodes: The array of TreeNodes.
     * @return The Polynomial in tree format.
     */
    @SuppressWarnings("unchecked")
    private static TreeNode process(ArrayList<TreeNode> nodes) {
        //stack that keeps track of found TreeNodes before a bracket.
        Stack<TreeNode> stack = new Stack<TreeNode>();

        //clone, to avoid concurrentModifications.
        ArrayList<TreeNode> nodesClone = (ArrayList<TreeNode>) nodes.clone();

        for (TreeNode t : nodesClone) {
            //if we encounter a bracket.
            if (t.getValue().equals(")")) {
                //remove the bracket in the ArrayList.
                nodes.remove(t);

                //find the previous node.
                TreeNode current = stack.pop();
                TreeNode previous = null;

                //loop until you reach the other bracket.
                while (!current.getValue().equals("(")) {
                    if (current instanceof OperatorNode) {
                        //if we find a + operator.
                        if (current.getValue().equals("+")) {
                            //find next node.
                            TreeNode next = stack.pop();

                            //set the children of the + node.
                            current.setRightNode(previous);
                            current.setLeftNode(next);

                            //remove children from the ArrayList.
                            nodes.remove(previous);
                            nodes.remove(next);
                        }
                    }

                    //prepare for next iteration.
                    previous = current;
                    current = stack.pop();
                }
                //the other bracket will now be current. Remove it.
                nodes.remove(current);
            } else {
                //push the node onto the stack.
                stack.push(t);
            }
        }

        //find all occurrences of *, and process them.
        for (int i = 1; i < nodes.size() - 1; i++) {
            TreeNode current = nodes.get(i);
            if (current.getValue().equals("*")) {
                //find next and previous nodes.
                TreeNode previous = nodes.get(i - 1);
                TreeNode next = nodes.get(i + 1);

                //set children.
                current.setLeftNode(previous);
                current.setRightNode(next);

                //remove children from ArrayList.
                nodes.remove(previous);
                nodes.remove(next);

                //go back one step, as we might miss the next multiplication.
                i--;
            }
        }

        //Process the last additions.
        for (int i = 1; i < nodes.size() - 1; i++) {
            TreeNode current = nodes.get(i);
            if (current.getValue().equals("+")) {
                //find next and previous nodes.
                TreeNode previous = nodes.get(i - 1);
                TreeNode next = nodes.get(i + 1);

                //set children.
                current.setLeftNode(previous);
                current.setRightNode(next);

                //remove children from ArrayList.
                nodes.remove(previous);
                nodes.remove(next);

                //go back one step, as we might miss the next multiplication.
                i--;
            }
        }

        //return the only remaining element in the ArrayList, the root of the tree.
        return nodes.get(0);
    }
}
