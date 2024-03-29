package com.clinic.impl;

import com.clinic.dto.SimpleStockCreate;
import com.clinic.dto.SimpleStockMinAmountUpdate;
import com.clinic.dto.SimpleStockAmountUpdate;
import com.clinic.dto.SimpleStockUpdate;
import com.clinic.entities.Stock;
import com.clinic.entities.User;
import com.clinic.entities.keys.StockId;
import com.clinic.exceptions.*;
import com.clinic.repositories.StockRepository;
import com.clinic.repositories.UserRepository;
import com.clinic.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(rollbackFor = Exception.class)
    public Stock createStockItem(SimpleStockCreate stockCreateData)
            throws StockConflictException, InvalidStockDataException, UserNotFoundException
    {
        if (stockCreateData.getAmount() <= 0 || stockCreateData.getMinAmount() <= 0)
            throw new InvalidStockDataException(stockCreateData.getAmount(), stockCreateData.getMinAmount());

        StockId stockId = new StockId();
        stockId.setId(stockCreateData.getId());
        stockId.setVendor(stockCreateData.getVendorId());

        if (stockRepository.existsByStockId(stockId))
            throw new StockConflictException(stockId);

        User user = userRepository.findById(stockCreateData.getUserId())
                .orElseThrow(() -> new UserNotFoundException("id", String.valueOf(stockCreateData.getUserId())));

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
    @Transactional(rollbackFor = Exception.class)
    public Stock updateStockItem(SimpleStockUpdate stockUpdateData)
            throws StockNotFoundException, InvalidStockDataException, UserNotFoundException
    {
        if (stockUpdateData.getAmount() < 0 || stockUpdateData.getMinAmount() < 0)
            throw new InvalidStockDataException(stockUpdateData.getAmount(), stockUpdateData.getMinAmount());

        User user = userRepository.findById(stockUpdateData.getUserId())
                .orElseThrow(() -> new UserNotFoundException("id", String.valueOf(stockUpdateData.getUserId())));

        StockId stockId = new StockId();
        stockId.setId(stockUpdateData.getId());
        stockId.setVendor(stockUpdateData.getVendorId());

        Stock stock = stockRepository.findByStockId(stockId)
                .orElseThrow(() -> new StockNotFoundException(stockId));

        stock.setAmount(stockUpdateData.getAmount());
        stock.setMinAmount(stockUpdateData.getMinAmount());
        stock.setUser(user);
        stock.setName(stockUpdateData.getName());
        stock.setDescription(stock.getDescription());
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
                .orElseThrow(() -> new StockNotFoundException(stockId));

        if (stock.getAmount() + stockUpdateData.getChange() < 0)
            throw new InvalidStockDataException(stock.getAmount() + stockUpdateData.getChange(), stock.getMinAmount());

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
                .orElseThrow(() -> new StockNotFoundException(stockId));

        if (stockMinAmountUpdateData.getMinAmount() < 0)
            throw new InvalidStockDataException(stock.getAmount(), stockMinAmountUpdateData.getMinAmount());

        stock.setMinAmount(stockMinAmountUpdateData.getMinAmount());
        stock.setLastUpdateTime(Calendar.getInstance());

        return stockRepository.save(stock);
    }
}
