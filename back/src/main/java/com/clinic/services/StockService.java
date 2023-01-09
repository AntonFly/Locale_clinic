package com.clinic.services;

import com.clinic.dto.SimpleStockCreate;
import com.clinic.dto.SimpleStockMinAmountUpdate;
import com.clinic.dto.SimpleStockAmountUpdate;
import com.clinic.entities.Stock;
import com.clinic.exceptions.InvalidStockDataException;
import com.clinic.exceptions.StockConflictException;
import com.clinic.exceptions.StockNotFoundException;
import com.clinic.exceptions.UserNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StockService {

    List<Stock> getAllItems();
    @Transactional
    Stock createStockItem(SimpleStockCreate stockCreateData)
            throws StockConflictException, InvalidStockDataException, UserNotFoundException;

    @Transactional
    Stock updateStockItemAmount(SimpleStockAmountUpdate stockUpdateData)
            throws StockNotFoundException, InvalidStockDataException;

    @Transactional
    Stock updateStockItemMinAmount(SimpleStockMinAmountUpdate stockMinAmountUpdateData)
            throws StockNotFoundException, InvalidStockDataException;
}
