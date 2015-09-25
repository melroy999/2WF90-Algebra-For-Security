package parser;

import parser.tree.TreeNode;

import java.util.Iterator;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class Parser {
    public static void parse(String toParse){
        toParse = toParse.trim();
        toParse = toParse.toUpperCase().replaceAll("[A-Y]", "X");
        toParse = toParse.replaceAll("X([^\\^]|\\Z)", "X^1");
        //toParse = toParse.replaceAll("(((?<=\\+|\\-|\\*)([0-9]+)(?!X))|((?<!\\^)([0-9]+)(?=\\+|\\-|\\*|\\))))", "\g<1>X^0");
        //TODO look into this...

        toParse = toParse.replaceAll("[^0-9\\+\\-*()^X]", "");
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

        split = toParse.split("\\*");


        //use regular expressions to give it the correct format
        //eg aa+b*c+ -> aaaaabccc, aabbbbbc etc
        //http://regexone.com/lesson/

        System.out.println(toParse);
    }
}
