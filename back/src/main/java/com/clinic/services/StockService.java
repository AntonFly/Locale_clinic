package com.clinic.services;

import com.clinic.dto.SimpleStockCreate;
import com.clinic.dto.SimpleStockMinAmountUpdate;
import com.clinic.dto.SimpleStockAmountUpdate;
import com.clinic.dto.SimpleStockUpdate;
import com.clinic.entities.Stock;
import com.clinic.entities.User;
import com.clinic.exceptions.InvalidStockDataException;
import com.clinic.exceptions.StockConflictException;
import com.clinic.exceptions.StockNotFoundException;
import com.clinic.exceptions.UserNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StockService {

    List<Stock> getAllItems();
    @Transactional(rollbackFor = Exception.class)
    Stock createStockItem(SimpleStockCreate stockCreateData)
            throws StockConflictException, InvalidStockDataException, UserNotFoundException;

    @Transactional(rollbackFor = Exception.class)
    Stock updateStockItem(SimpleStockUpdate stockUpdateData)
            throws StockNotFoundException, InvalidStockDataException, UserNotFoundException;

    @Transactional(rollbackFor = Exception.class)
    Stock updateStockItemAmount(SimpleStockAmountUpdate stockUpdateData)
            throws StockNotFoundException, InvalidStockDataException;

    @Transactional(rollbackFor = Exception.class)
    Stock updateStockItemMinAmount(SimpleStockMinAmountUpdate stockMinAmountUpdateData)
            throws StockNotFoundException, InvalidStockDataException;
}
