package br.mil.fab.consigext.exceptions;


public class SambaException extends Exception {
 
    private static final long serialVersionUID = -5200537554483363640L;

    public SambaException(String string, Throwable throwable, boolean b, boolean b1) {
        super(string, throwable, b, b1);
    }

    public SambaException(Throwable throwable) {
        super(throwable);
    }

    public SambaException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public SambaException(String string) {
        super(string);
    }

    public SambaException() {
        super();
    }
}