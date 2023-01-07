package com.clinic.controllers;

import com.clinic.dto.*;
import com.clinic.entities.*;
import com.clinic.exceptions.*;
import com.clinic.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/stock")
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(
            StockService ss
    ){
        this.stockService = ss;
    }

    @PostMapping("/create_item")
    public Stock createStockItem(@RequestBody SimpleStockCreate createData)
            throws StockConflictException, InvalidStockDataException, UserNotFoundException
    { return stockService.createStockItem(createData); }

    @PutMapping("/update_item_amount")
    public Stock updateStockItemAmount(@RequestBody SimpleStockAmountUpdate updateData)
            throws InvalidStockDataException, StockNotFoundException
    { return stockService.updateStockItemAmount(updateData); }

    @PutMapping("/update_item_min_amount")
    public Stock updateStockItemMinAmount(@RequestBody SimpleStockMinAmountUpdate updateData)
            throws InvalidStockDataException, StockNotFoundException
    { return stockService.updateStockItemMinAmount(updateData); }

}