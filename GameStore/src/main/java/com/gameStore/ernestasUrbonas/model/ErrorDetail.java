package com.gameStore.ernestasUrbonas.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDetail {

    private LocalDateTime timestamp;

    private String message;
    private String details;
}
