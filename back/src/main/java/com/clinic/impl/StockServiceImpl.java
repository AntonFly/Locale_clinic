package com.clinic.impl;

import com.clinic.dto.SimpleStockCreate;
import com.clinic.dto.SimpleStockMinAmountUpdate;
import com.clinic.dto.SimpleStockAmountUpdate;
import com.clinic.entities.Stock;
import com.clinic.entities.User;
import com.clinic.entities.keys.StockId;
import com.clinic.exceptions.*;
import com.clinic.repositories.StockRepository;
import com.clinic.repositories.UserRepository;
import com.clinic.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    private final UserRepository userRepository;


    @Autowired
    public StockServiceImpl(
            StockRepository sr,
            UserRepository ur
    ){
        this.stockRepository = sr;
        this.userRepository = ur;
    }

    @Override
    public List<Stock> getAllItems()
    { return stockRepository.findAll(); }

    @Override
    public Stock createStockItem(SimpleStockCreate stockCreateData)
            throws StockConflictException, InvalidStockDataException, UserNotFoundException
    {
        if (stockCreateData.getAmount() < stockCreateData.getMinAmount() || stockCreateData.getAmount() <= 0 || stockCreateData.getMinAmount() <= 0)
            throw new InvalidStockDataException("Amount " + stockCreateData.getAmount() + " is less than minimal allowed amount " + stockCreateData.getMinAmount());

        StockId stockId = new StockId();
        stockId.setId(stockCreateData.getId());
        stockId.setVendor(stockCreateData.getVendorId());

        if (stockRepository.existsByStockId(stockId))
            throw new StockConflictException("There is already an item with id: " + stockId);

        User user = userRepository.findById(stockCreateData.getUserId())
                .orElseThrow(() -> new UserNotFoundException("There is no user with id: " + stockCreateData.getUserId()));

        Stock stock = new Stock();
        stock.setStockId(stockId);
        stock.setAmount(stockCreateData.getAmount());
        stock.setDescription(stockCreateData.getDescription());
        stock.setName(stockCreateData.getName());
        stock.setMinAmount(stockCreateData.getMinAmount());
        stock.setUser(user);
        stock.setLastUpdateTime(Calendar.getInstance());

        return stockRepository.save(stock);
    }

    @Override
    public Stock updateStockItemAmount(SimpleStockAmountUpdate stockUpdateData)
            throws StockNotFoundException, InvalidStockDataException
    {
        StockId stockId = new StockId();
        stockId.setId(stockUpdateData.getId());
        stockId.setVendor(stockUpdateData.getVendorId());

        Stock stock = stockRepository.findByStockId(stockId)
                .orElseThrow(() -> new StockNotFoundException("There is no stock with id: " + stockId));

        if (stock.getAmount() < stock.getAmount() + stockUpdateData.getChange())
            throw new InvalidStockDataException("Amount " + stock.getAmount() + " is less than new minimal allowed amount " + stock.getAmount() + stockUpdateData.getChange());

        stock.setAmount(stock.getAmount() + stockUpdateData.getChange());
        stock.setLastUpdateTime(Calendar.getInstance());

        return stockRepository.save(stock);
    }

    @Override
    public Stock updateStockItemMinAmount(SimpleStockMinAmountUpdate stockMinAmountUpdateData)
            throws StockNotFoundException, InvalidStockDataException
    {
        StockId stockId = new StockId();
        stockId.setId(stockMinAmountUpdateData.getId());
        stockId.setVendor(stockMinAmountUpdateData.getVendorId());

        Stock stock = stockRepository.findByStockId(stockId)
                .orElseThrow(() -> new StockNotFoundException("There is no stock with id: " + stockId));

        if (stock.getAmount() < stockMinAmountUpdateData.getMinAmount())
            throw new InvalidStockDataException("Amount " + stock.getAmount() + " is less than new minimal allowed amount " + stockMinAmountUpdateData.getMinAmount());

        stock.setMinAmount(stockMinAmountUpdateData.getMinAmount());
        stock.setLastUpdateTime(Calendar.getInstance());

        return stockRepository.save(stock);
    }
}
