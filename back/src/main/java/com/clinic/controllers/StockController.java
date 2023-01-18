package com.clinic.controllers;

import com.clinic.dto.*;
import com.clinic.entities.*;
import com.clinic.exceptions.*;
import com.clinic.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController()
@PreAuthorize("hasRole('ROLE_MANAGER')")
@RequestMapping("/stock")
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(StockService ss)
    { this.stockService = ss; }

    @GetMapping("/get_all_items")
    public List<Stock> getAllItems()
    { return stockService.getAllItems(); }

    @PostMapping("/create_item")
    public Stock createStockItem(@RequestBody SimpleStockCreate createData)
            throws StockConflictException, InvalidStockDataException, UserNotFoundException
    { return stockService.createStockItem(createData); }

    @PutMapping("/update_item")
    public Stock updateStockItem(@RequestBody SimpleStockUpdate updateData)
            throws StockNotFoundException, UserNotFoundException, InvalidStockDataException
    { return  stockService.updateStockItem(updateData); }

    @PutMapping("/update_item_amount")
    public Stock updateStockItemAmount(@RequestBody SimpleStockAmountUpdate updateData)
            throws InvalidStockDataException, StockNotFoundException
    { return stockService.updateStockItemAmount(updateData); }

    @PutMapping("/update_item_min_amount")
    public Stock updateStockItemMinAmount(@RequestBody SimpleStockMinAmountUpdate updateData)
            throws InvalidStockDataException, StockNotFoundException
    { return stockService.updateStockItemMinAmount(updateData); }

}
