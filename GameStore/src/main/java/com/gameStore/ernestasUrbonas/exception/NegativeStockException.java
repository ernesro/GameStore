package com.gameStore.ernestasUrbonas.exception;

public class NegativeStockException extends RuntimeException {

    /**
     * Constructor for NegativeStockException.
     * @param message
     *
     * **/

    public NegativeStockException(String message) {
        super(message);
    }
}
