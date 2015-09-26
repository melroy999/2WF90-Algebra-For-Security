package parser;

import parser.tree.MinusNode;
import parser.tree.OperatorNode;
import parser.tree.TreeNode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class Parser {
    public static void parse(String toParse){
        System.out.println(toParse);
        toParse = toParse.trim();
        toParse = toParse.toUpperCase().replaceAll("[A-Z]", "X");
        toParse = toParse.replaceAll("((?<=[0-9])\\*X)", "X");
        toParse = toParse.replaceAll("X([^\\^]|\\Z)", "X^1");
        toParse = toParse.replaceAll("[^0-9\\+\\-*()^]", "");
        toParse = toParse.replace("+^", "+1^");
        toParse = toParse.replace("-^", "-1^");
        toParse = toParse.replace("*^", "*1^");
        toParse = toParse.replace("(^", "(1^");
        toParse = toParse.startsWith("^") ? "1" + toParse : toParse;

        String[] split = toParse.split("\\(");
        for(int i = 1; i < split.length; i++){
            split[i] = "(" + split[i];
            if(split[i-1].equals("")){
                continue;
            }
            char c = split[i-1].charAt(split[i-1].length()-1);
            if(Character.isDigit(c)) {
                split[i - 1] = split[i - 1] + "*";
            } else if(c == '-'){
                split[i - 1] = split[i - 1] + "1*";
            } else if(c == ')'){
                split[i - 1] = split[i - 1] + "*";
            }
        }

        toParse = "";
        for (String aSplit : split) {
            toParse = toParse + aSplit;
        }

        split = toParse.split("\\-");
        for(int i = 1; i < split.length; i++){

            if(!split[i-1].equals("")){
                split[i] = "-" + split[i];
                char c = split[i-1].charAt(split[i-1].length()-1);
                if(c == ')' || Character.isDigit(c)){
                    split[i - 1] = split[i - 1] + "+";
                }
            }
        }

        toParse = "";
        for (String aSplit : split) {
            toParse = toParse + aSplit;
        }

        System.out.println(toParse);

        makeTree("" + toParse + "");
    }

    public static TreeNode makeTree(final String s){
        TreeNode node = null;
        String currentTerm = "";
        final char[] chars = s.toCharArray();

        Iterator<Character> iterator = new Iterator<Character>() {
            int i = 0;

            @Override
            public boolean hasNext() {
                return i < chars.length;
            }

            @Override
            public Character next() {
                return chars[i++];
            }
        };

        ArrayList<TreeNode> nodes = new ArrayList<TreeNode>();

        while(iterator.hasNext()){
            char c = iterator.next();
            if(c=='+'|c=='*'|c=='('|c==')'){
                if(!currentTerm.equals("")){
                    nodes.add(new TreeNode(currentTerm));
                }
                nodes.add(new OperatorNode("" + c));
                currentTerm="";
            } else if (c=='-'){
                nodes.add(new MinusNode("-"));
                currentTerm="";
            } else {
                currentTerm = currentTerm + c;
            }
        }
        if(!currentTerm.equals("")){
            nodes.add(new TreeNode(currentTerm));
        }

        System.out.println("-list-----");

        System.out.println(nodes);

        //process the - into complete treenodes.
        for(int i = 0; i < nodes.size(); i++){
            TreeNode t = nodes.get(i);
            if(t instanceof OperatorNode){
                if(t.getValue().equals("-")){
                    if(i + 1 < nodes.size()){
                        TreeNode t2 = nodes.get(i+1);
                        if(!(t2 instanceof OperatorNode)){
                            t.setLeftNode(t2);
                        }
                        nodes.remove(i+1);
                    }
                }
            }
        }

        //process all stuff that has brackets.
        TreeNode parent = process(nodes);

        System.out.println("adding *:" + nodes);
        System.out.println("tree: " + parent);

        System.out.println("-----------");
        System.out.println();

        return node;
    }

    private static TreeNode process(ArrayList<TreeNode> nodes){
        System.out.println(nodes);

        Stack<TreeNode> stack = new Stack<TreeNode>();

        ArrayList<TreeNode> nodesClone = (ArrayList<TreeNode>) nodes.clone();

        for(TreeNode t : nodesClone){
            if(t.getValue().equals(")")){
                nodes.remove(t);
                TreeNode current = stack.pop();
                TreeNode previous = null;
                while(!current.getValue().equals("(")){
                    if(current instanceof OperatorNode){
                        if(current.getValue().equals("+")){
                            TreeNode next = stack.pop();
                            current.setRightNode(previous);
                            current.setLeftNode(next);
                            nodes.remove(previous);
                            nodes.remove(next);
                        }
                    }
                    previous = current;
                    current = stack.pop();
                }
                nodes.remove(current);
            } else {
                stack.push(t);
            }
        }

        for(int i = 1; i < nodes.size() - 1; i++){
            TreeNode current = nodes.get(i);
            if(current.getValue().equals("*")){
                TreeNode previous = nodes.get(i-1);
                TreeNode next = nodes.get(i+1);
                current.setLeftNode(previous);
                current.setRightNode(next);
                nodes.remove(previous);
                nodes.remove(next);
                i--;
            }
        }

        for(int i = 1; i < nodes.size() - 1; i++){
            TreeNode current = nodes.get(i);
            if(current.getValue().equals("+")){
                TreeNode previous = nodes.get(i-1);
                TreeNode next = nodes.get(i+1);
                current.setLeftNode(previous);
                current.setRightNode(next);
                nodes.remove(previous);
                nodes.remove(next);
                i--;
            }
        }

        return nodes.get(0);
    }
}
