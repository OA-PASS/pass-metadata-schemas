package org.eclipse.pass.schema;

public class MergeFailException extends Exception {
    public MergeFailException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

}
