package org.yapp.ex;

import android.text.TextUtils;

public class RequestException extends BaseException {
    private static final long serialVersionUID = 1L;

    private String code;
    private String customMessage;
    private String result;

    public RequestException() {
    }

    /**
     * @param code The http response status code, 0 if the http request error and has no response.
     */
    public RequestException(int code) {
        this.code = String.valueOf(code);
    }

    /**
     * @param code The http response status code, 0 if the http request error and has no response.
     */
    public RequestException(String code) {
        this.code = code;
    }

    public RequestException(int code,String detailMessage) {
        super(detailMessage);
        this.code = String.valueOf(code);
    }

    /**
     * @param code          The http response status code, 0 if the http request error and has no response.
     * @param detailMessage
     */
    public RequestException(String code, String detailMessage) {
        super(detailMessage);
        this.code = code;
    }

    /**
     * @param code          The http response status code, 0 if the http request error and has no response.
     * @param detailMessage
     * @param throwable
     */
    public RequestException(String code, String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
        this.code = code;
    }

    /**
     * @param code      The http response status code, 0 if the http request error and has no response.
     * @param throwable
     */
    public RequestException(String code, Throwable throwable) {
        super(throwable);
        this.code = code;
    }

    public void setMessage(String message) {
        this.customMessage = message;
    }

    /**
     * @return The http response status code, 0 if the http request error and has no response.
     */
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        if (!TextUtils.isEmpty(customMessage)) {
            return customMessage;
        } else {
            return super.getMessage();
        }
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "code: " + code + ", msg: " + getMessage() + ", result: " + result;
    }
}
