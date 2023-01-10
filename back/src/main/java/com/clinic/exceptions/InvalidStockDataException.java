package com.clinic.exceptions;

import com.clinic.entities.Stock;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class InvalidStockDataException extends Exception {

    private final String message;

    public InvalidStockDataException(long amount, long minAmount)
    {
        if (minAmount < 0)
            message = "Минимально допустимый остаток не может быть меньше нуля: " + minAmount;
        else if (amount < 0)
            message = "Остаток не может быть меньше нуля: " + amount;
        else if (amount < minAmount)
            message = "Остаток в количестве " + amount + " меньше допустимого: " + minAmount;
        else
            message = "Ошибочные данные, остаток: " + amount + ", минимальный остаток: " + minAmount;
    }

}