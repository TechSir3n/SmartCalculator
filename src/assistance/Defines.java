package assistance;

public enum Defines {
 MAX_EXPRESSION(255,"Maximum number of input characters"),
 MAX_SIGNS(7,"At least 7 decimal places"),
 BAD_ATTEMPT("Division by zero,is incorrect");

    Defines(String _descr) {
        this._description = _descr;
    }

    Defines(int _val,String _descr) {
        this._value = _val;
        this._description = _descr;

    }

    public final int getValue() {
        return _value;
    }
    public final String getDescription() {
        return _description;
    }


    private int _value;
    private final String _description;
}