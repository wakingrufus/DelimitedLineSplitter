package com.github.wakingrufus.csv;

import java.util.ArrayList;

/**
 * This class is used to process and create CSV or other delimited-type files. It handles one line.
 */
public class DelimitedLineSplitter {

    /**
     * The String that denotes the delimitation between tokens
     */
    private String delimiterString;
    /**
     * The String which will appear in pairs around a String to be taken as-is, ignoring delimiters.
     */
    private String escapeString;

    /**
     * Constructor usage for a CSV line is new DelimitedLineSplitter(",","\"")
     *
     * @param delimiterChar The String that denotes the delimitation between tokens
     * @param escapeChar    The String which will appear in pairs around a String to be taken as-is, ignoring delimiters.
     */
    public DelimitedLineSplitter(String delimiterChar, String escapeChar) {
        delimiterString = delimiterChar;
        escapeString = escapeChar;
    }

    /**
     * Creates a line from the tokens given. Usually used when outputting to a file.
     * This method calls makeLine(ArrayList<String> vals, boolean alwaysUseQuotes)
     * using a default value of false for parameter alwaysUseQuotes
     *
     * @param vals Tokens to put into the output line.
     * @return A String representing the delimited line.
     */
    public String makeLine(ArrayList<String> vals) {
        return makeLine(vals, false);
    }

    /**
     * Creates a line from the tokens given. Usually used when outputting to a file.
     *
     * @param vals            Tokens to put into the output line.
     * @param alwaysUseQuotes If true, each token will be encased in escape strings, even if it does not contain a delimiter
     * @return A String representing the delimited line.
     */
    public String makeLine(ArrayList<String> vals, boolean alwaysUseQuotes) {
        StringBuilder sb = new StringBuilder();
        for (String s : vals) {
            if (s == null) {
                sb.append("");
                sb.append(delimiterString);
            } else {
                StringBuilder token = new StringBuilder(s.replace(escapeString, escapeString + escapeString));
                if (token.toString().contains(delimiterString) || alwaysUseQuotes) {
                    token.insert(0, escapeString);
                    token.append(escapeString);
                }
                token.append(delimiterString);
                sb.append(token.toString());
            }
        }
        if (vals.size() > 0) {
            sb.delete(sb.length() - 1, sb.length());
        }
        return sb.toString();
    }

    /**
     * Parses a line and returns the tokens in an array list
     *
     * @param line The input line to parse into tokens
     * @return The list of tokens
     */
    public ArrayList<String> splitLine(String line) {
        ArrayList<String> tokens = new ArrayList<>();
        StringBuilder sb = new StringBuilder(line);
        // loop while there is still part of the string left to parse.
        boolean parse = true;
        while (parse) {
            StringBuilder token = new StringBuilder("");
            // if the length is 0, this is the last token and it is an empty string.
            if (sb.length() == 0) {
                parse = false;
            }
            if (sb.toString().startsWith(delimiterString)) {
                // The token is an empty string
                tokens.add("");
                // Remove the delimiter
                sb.replace(0, delimiterString.length(), "");
            } else {
                // Check if the current token is escaped
                if (sb.toString().startsWith(escapeString)) {
                    // while this is true, we have not reached the end of the token yet
                    while (sb.toString().startsWith(escapeString)) {
                        // bite off from the string until another escape string is found
                        String newToken = sb.substring(0 + escapeString.length(), sb.indexOf(escapeString, 0
                                + escapeString.length()));
                        // store this bite into the token
                        token.append(newToken);
                        // remove this bite from the input string
                        sb.replace(0, newToken.length() + (2 * escapeString.length()), "");
                        // when two escape strings appear consecutively within an escaped token, 
                        // that means that there is a literal occurance of the escape string in the token, so add it,
                        // but leave it in the front of the input String so we know to keep processing this token
                        if (sb.toString().startsWith(escapeString)) {
                            token.append("\"");
                        }
                    }
                } else {
                    // Token is not escaped
                    // Check if there is another token after this one
                    if (sb.toString().contains(delimiterString)) {
                        // pull the token from the input String
                        token.append(sb.toString().substring(0, sb.indexOf(delimiterString)));
                    } else {
                        // pull the rest of the line into this token
                        token.append(sb.toString());
                    }
                    // remove this token from the input string
                    sb.replace(0, token.length(), "");
                }
                // add this token to the token list
                tokens.add(token.toString());
                if (sb.toString().startsWith(delimiterString)) {
                    // remove the delimiter in front of the next token
                    sb.replace(0, delimiterString.length(), "");
                } else {
                    // there is no delimiter after this token, end parsing
                    parse = false;
                }
            }
        }
        return tokens;
    }

    public static DelimitedLineSplitter getCSVInstance() {
        return new DelimitedLineSplitter(",", "\"");
    }
}