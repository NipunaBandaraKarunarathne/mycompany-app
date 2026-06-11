package com.example.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventory.dto.InventoryDTO;
import com.example.inventory.service.InventoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/getinventory")
    public List<InventoryDTO> getMethodName() {
        return inventoryService.getInventory();
    }

    @PostMapping("/addinventory")
    public InventoryDTO addInventory(@RequestBody InventoryDTO inventoryDTO) {

        return inventoryService.addInventory(inventoryDTO);
    }

    @GetMapping("/inventory/{inventoryId}")
    public InventoryDTO getInventoryById(@PathVariable Integer inventoryId) {
        return inventoryService.getInventoryById(inventoryId);
    }

    @PutMapping("/updateinventory")
    public InventoryDTO updateInventory(@RequestBody InventoryDTO inventoryDTO) {
        return inventoryService.updateInventory(inventoryDTO);
    }

    @DeleteMapping("/deleteinventory/{inventoryId}")
    public String deleteInventory(@PathVariable Integer inventoryId) {
        return inventoryService.deleteInventory(inventoryId);
    }

}
