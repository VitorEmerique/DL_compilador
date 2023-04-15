package lexer;

public enum Tag {
    // Reserved Words
    PROGRAM("PROGRAM"), BEGIN("BEGIN"), END("END"), INT("INT"), REAL("REAL"),
    BOOL("BOOL"), TRUE("TRUE"), FALSE("FALSE"), READ("READ"), WRITE("WRITE"),
    // Assign
    ASSIGN("ASSIGN"),
    // Arithmetical Operations
    SUM("SUM"), MUL("MUL"), SUB("SUB"), DIV("DIV"), // SUM, SUB, MUL, DIV + - * /
    // Logical Operators
    LOR("OR"), LAND("LAND"), LNOT("LNOT"), // LAND, LOR, LNOT & | !
    // Relational Operator
    LT("LT"), LE("LE"), GT("GT"), GE("GE"), NE("NE"),

    // Literals
    LIT_INT("LIT_INT"), LIT_REAL("LIT_REAL"),
    // Identifiers
    ID("ID"),
    // Others
    EOF("EOF"), UNK("UNK"),
    LPAREN("LPAREN"), RPAREN("RPAREN"), // LPAREN, RPAREN ( )
    COMMA("COMMA"), SEMI("SEMI"); // COMMA, SEMI , ;

    private String name;

    private Tag(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
