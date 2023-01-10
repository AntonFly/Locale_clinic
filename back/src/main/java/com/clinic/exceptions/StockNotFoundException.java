package com.clinic.exceptions;

import com.clinic.entities.keys.StockId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class StockNotFoundException extends Exception{

    private final String message;

    public StockNotFoundException(StockId stockId)
    { message = "Не найден объект с производителем №" + stockId.getVendor() + " и серийным номером " + stockId.getId(); }

}
