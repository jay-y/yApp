package org.yapp.ex;

public class ApplicationException extends BaseException {
    private static final long serialVersionUID = 1L;

    public ApplicationException() {
    }

    public ApplicationException(String detailMessage) {
        super(detailMessage);
    }

    public ApplicationException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ApplicationException(Throwable throwable) {
        super(throwable);
    }
}
