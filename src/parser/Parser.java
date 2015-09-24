package parser;

import parser.tree.TreeNode;

import java.util.Iterator;
import java.util.Stack;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class Parser {
    public static void parse(String toParse){
        Stack<Character> stack = new Stack<Character>();
        TreeNode nodePointer = null;

        toParse = toParse.trim();
        toParse = toParse.replaceAll("[^0-9\\+\\-*()^]", "");

        for(char c: toParse.toCharArray()){

        }

        System.out.println(toParse);
    }
}
