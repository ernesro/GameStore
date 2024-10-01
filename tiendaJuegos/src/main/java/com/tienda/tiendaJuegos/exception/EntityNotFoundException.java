package com.tienda.tiendaJuegos.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {
    private final String entityName;
    private final Object identifier;

    public EntityNotFoundException(String entityName, Object identifier) {
        super(String.format("%s not found with identifier: %s", entityName, identifier));
        this.entityName = entityName;
        this.identifier = identifier;
    }
}