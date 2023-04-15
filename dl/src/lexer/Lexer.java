package lexer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Hashtable;

public class Lexer {
    private static final char EOF_CHAR = (char) -1;
    private static int line = 1;
    private BufferedReader reader;
    private char peek;

    // tabela hash -- Reserved Words
    private Hashtable<String, Tag> keywords;

    public Lexer(File file) {
        try {
            this.reader = new BufferedReader(new FileReader(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.peek = ' ';

        // palavras reservadas
        keywords = new Hashtable<String, Tag>();
        keywords.put("programa", Tag.PROGRAM);
        keywords.put("inicio", Tag.BEGIN);
        keywords.put("fim", Tag.END);
        keywords.put("inteiro", Tag.INT);
        keywords.put("real", Tag.REAL);
        keywords.put("booleano", Tag.BOOL);
        keywords.put("verdadeiro", Tag.TRUE);
        keywords.put("falso", Tag.FALSE);
        keywords.put("leia", Tag.READ);
        keywords.put("escreva", Tag.WRITE);
    }

    public static int line() {
        return line;
    }

    private char nextChar() {
        if (peek == '\n')
            line++;
        try {
            peek = (char) reader.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return peek;
    }

    private static boolean isWhitespace(int c) {
        switch (c) {
            case ' ':
            case '\t':
            case '\n':
                return true;
            default:
                return false;
        }
    }

    public Token nextToken() {
        while (isWhitespace(peek))
            nextChar();
        switch (peek) {
            case '=':
                nextChar();
                return new Token(Tag.ASSIGN, "=");
            case '+':
                nextChar();
                return new Token(Tag.SUM, "+");
            case '-':
                nextChar();
                return new Token(Tag.SUB, "-");
            case '*':
                nextChar();
                return new Token(Tag.MUL, "*");
            case '|':
                nextChar();
                return new Token(Tag.LOR, "|");
            case '<':
                nextChar();
                if (peek == '=') {
                    nextChar();
                    return new Token(Tag.LE, "<=");
                }
                return new Token(Tag.LT, "<");

            case '>':
                nextChar();
                if (peek == '=') {
                    nextChar();
                    return new Token(Tag.GE, ">=");
                }
                return new Token(Tag.GT, ">");

            case '!':
                nextChar();
                if (peek == '=') {
                    nextChar();
                    return new Token(Tag.NE, "!=");
                }
                return new Token(Tag.LNOT, "!");

            case '/':
                nextChar();
                if (peek == '/') {
                    nextChar();

                } else {
                    return new Token(Tag.DIV, "/");
                }

            case '&':
                nextChar();
                return new Token(Tag.LAND, "&");

            case '(':
                nextChar();
                return new Token(Tag.LPAREN, "(");

            case ')':
                nextChar();
                return new Token(Tag.RPAREN, ")");

            case ',':
                nextChar();
                return new Token(Tag.COMMA, ",");

            case ';':
                nextChar();
                return new Token(Tag.SEMI, ";");

            case EOF_CHAR:
                return new Token(Tag.EOF, "");

            default:
                if (Character.isDigit(peek)) {
                    String num = "";
                    do {
                        num += peek;
                        nextChar();

                    } while (Character.isDigit(peek));
                    if (peek != '.')
                        return new Token(Tag.LIT_INT, num);

                    do {
                        num += peek;
                        nextChar();

                    } while (Character.isDigit(peek));
                    return new Token(Tag.LIT_REAL, num);
                }

                else if (isIdStart(peek)) {
                    String id = "";

                    do {
                        id += peek;
                        nextChar();
                    } while (isIdPart(peek));

                    if (keywords.containsKey(id)) {
                        return new Token(keywords.get(id), id);
                    }
                    return new Token(Tag.ID, id);

                }

        }
        String unk = String.valueOf(peek);
        nextChar();
        return new Token(Tag.UNK, unk);
    }

    // identifiers
    private static boolean isIdStart(int c) {
        return (Character.isAlphabetic(c) || c == '_');
    }

    private static boolean isIdPart(int c) {
        return (isIdStart(c) || Character.isDigit(c));
    }
}
