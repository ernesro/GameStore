package com.gameStore.ernestasUrbonas.exception;

public class InvalidOrderStatusTransitionException extends RuntimeException {

    /**
     * Constructor for InvalidOrderStatusTransitionException.
     *
     * @param message
     **/

    public InvalidOrderStatusTransitionException(String message) {
        super(message);
    }
}
