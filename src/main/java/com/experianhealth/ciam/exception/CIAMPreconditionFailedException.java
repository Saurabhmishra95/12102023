package com.experianhealth.ciam.exception;

import org.apache.http.HttpStatus;
public class CIAMPreconditionFailedException extends CIAMRuntimeException {
    public static final String PUBLIC_DETAIL_MESSAGE_1 = "Precondition failed for request: ";

    public CIAMPreconditionFailedException(String resource, String exceptionMessage) {
        super(PUBLIC_DETAIL_MESSAGE_1 + resource, exceptionMessage, HttpStatus.SC_NO_CONTENT);
    }


    public CIAMPreconditionFailedException(String exceptionMessage) {
        super(PUBLIC_DETAIL_MESSAGE_1 + exceptionMessage, exceptionMessage, HttpStatus.SC_NO_CONTENT);
    }
}
