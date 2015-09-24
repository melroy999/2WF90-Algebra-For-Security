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
        toParse = toParse.replaceAll("[^0-9\\+\\-*()^]", "");
        toParse = toParse.replace("+^", "+1^");
        toParse = toParse.replace("-^", "-1^");
        toParse = toParse.replace("(^", "(1^");

        String[] split = toParse.split("\\(");
        for(int i = 1; i < split.length; i++){
            split[i] = "(" + split[i];
            char c = split[i-1].charAt(split[i-1].length()-1);
            if(Character.isDigit(c)) {
                split[i - 1] = split[i - 1] + "*";
            } else if(c == '-'){
                split[i - 1] = split[i - 1] + "1*";
            } else if(c == '+'){

            }
        }

        toParse = "";
        for (String aSplit : split) {
            toParse = toParse + aSplit;
        }

        //use regular expressions to give it the correct format
        //eg aa+b*c+ -> aaaaabccc, aabbbbbc etc
        //http://regexone.com/lesson/

        System.out.println(toParse);
    }
}
