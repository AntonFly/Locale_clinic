package com.clinic.exceptions;

import com.clinic.entities.keys.StockId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class StockConflictException extends Exception {

    private final String message;

    public StockConflictException(StockId stockId)
    { message = "Уже зарегестрирован объект с производителем №" + stockId.getVendor() + " и серийным номером " + stockId.getId(); }

}