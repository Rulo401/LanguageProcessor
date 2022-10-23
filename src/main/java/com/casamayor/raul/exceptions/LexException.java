package com.casamayor.raul.exceptions;

public class LexException extends Exception{
    
    private static final String MSG_FORMAT = "Lexical error at line %d (%s)";

    private int errorCode, lineNumber;
    private String textLine;
    
    public LexException(int errorCode, int lineNumber, String textLine){
        super();
        this.errorCode = errorCode;
        this.lineNumber = lineNumber;
        this.textLine = textLine;
    }

    @Override
    public String toString() {
        return String.format(MSG_FORMAT, lineNumber, textLine);
    }
}
