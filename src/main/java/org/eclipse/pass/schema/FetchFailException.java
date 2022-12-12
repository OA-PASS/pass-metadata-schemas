package org.eclipse.pass.schema;

public class FetchFailException extends RuntimeException {
    public FetchFailException(String errorMessage) {
        super(errorMessage);
    }
}
