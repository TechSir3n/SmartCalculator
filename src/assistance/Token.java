package assistance;

public enum Token {
    O_UNKNOWN(),

    O_MINUS('-'),
    O_PLUS('+'),

    O_DIV('/'),
    O_MUL('*'),

    O_POW('^'),

    O_OPBRACK('('),
    O_CLOBRACK(')'),

    O_COS('c'),
    O_ACOS('C'),
    O_SIN('s'),
    O_ASIN('S'),

    O_ATAN('T'),
    O_TAN('t'),

    O_SQRT('Q'),
    O_LOG('L');


    private Token(char _sym) {
        this.symbol = _sym;
    }

    private Token() { symbol = ' '; }

    public final char getToken() {
        return symbol;
    }

    private final char symbol;
}