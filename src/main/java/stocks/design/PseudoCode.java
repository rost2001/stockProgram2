package stocks.design;

public class PseudoCode {
    
    /* NEW
     * Simple classdiagram, all standard stuff
     * + custom code blocks, denoted by comments
     * gives the ability to define pieces of code block as logical structures
     * for the purpose of it showing up on the class diagram as something substantial logically
     * maybe even a picture of where in code on which code block it is referring to when hovering of the label in the class diagram
     * So ya 2 parts:
     * Simple class diagram
     * and a prehaps nice way of logically grouping and marking the code to show in eclipse, and then for it to also show in the class diagram
     * 
     */
    
    
    //------------------------
    // Simplefy it by just having a standard class diagram from reading code like classes and method calls
    // into connections that is arrows,
    // then also have labels of codeblock, with a name and what it does in a few words like "<>sends to<>" or "Removes the order"
   
   //Name - its name
    // functionality - just a description of what is does
     
    
    //Custom labeling of code blocks:
    /* Denote // { and // }
     * Denote // plabel name = {}
     * Already defined Keywords and java keywords, with functionality, like in java - follows rules, declaring rules, abide them and fit into syntax
     * Custom declaring name of codeblock, and custom declaring functionality of codeblock like "verifies" or "sends the <> to <>"
     * Prehaps custom functionality combined with name
     */
    
    //Custom labeling of code pieces like "e.getKeyCode()":
    /* Assume character by character exactly
     * Denote // plabel name = ""
     * 
     */
    
    //Search through code
    //save to file, in some format, read into gui
    
    
    // All connections 
    // Code Identifiers:
    /* * Assume line by line, with ";"
     * * Assume a check on every identifier on every line, and then conclude what it is from what it matches
     * * Assume Removing lines with spaces, multiple lines when needed
     * Class - Name framed by spaces after class word
     * Interface - interface word
     * implement - implement word
     * extends - extends word
     * Variable declaration - 2 words next to eachother that arent standard modifiers like public and ;
     * Variable call - Matching name of the declared
     * Method declaration - {} with (parameters declaration) before and name before that and return type before that
     * Method call - Matching name of the declared with brackets and parameters 
     * Abstract method - abstract word
     * foreach - 
     * if - 
     * 
     */
    
    //File format:
    /*  Assume just listing of everything
     * Declarations:
     * Classes - 
     * Methods - 
     * Variables - 
     * 
     * CustomLabels - 
     * 
     */

}
