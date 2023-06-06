package br.com.internetbanking.api.exception;

public class ResourceConflictExeception extends RuntimeException {

    public ResourceConflictExeception(String message) {
        super(message);
    }
}
