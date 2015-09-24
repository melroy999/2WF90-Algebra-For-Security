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
        //toParse = toParse.replace("-","+-");
        toParse = toParse.replace("+^", "+1^");
        toParse = toParse.replace("-^", "-1^");
        toParse = toParse.replace("(^", "(1^");

        //use regular expressions to give it the correct format
        //eg aa+b*c+ -> aaaaabccc, aabbbbbc etc
        //http://regexone.com/lesson/

        System.out.println(toParse);
    }
}
