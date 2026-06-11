package com.example.inventory.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.inventory.dto.InventoryDTO;
import com.example.inventory.model.Inventory;
import com.example.inventory.repo.InventoryRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class InventoryService {
    @Autowired
    private InventoryRepo inventoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<InventoryDTO> getInventory() {
        List<Inventory> inventoryList = inventoryRepo.findAll();
        return modelMapper.map(inventoryList, new TypeToken<List<InventoryDTO>>() {
        }.getType());
    }

    public InventoryDTO addInventory(InventoryDTO inventoryDTO) {
        inventoryRepo.save(modelMapper.map(inventoryDTO, Inventory.class));
        return inventoryDTO;

    }

public InventoryDTO getInventoryById(Integer inventoryId) {
    Inventory inventory = inventoryRepo.getInventoryById(inventoryId);

    if (inventory == null) {
        throw new RuntimeException(
            "Inventory not found with id: " + inventoryId);
    }

    return modelMapper.map(inventory, InventoryDTO.class);
}

        public InventoryDTO updateInventory(InventoryDTO inventoryDTO) {
        inventoryRepo.save(modelMapper.map(inventoryDTO, Inventory.class));
        return inventoryDTO;
    }

    public String deleteInventory(Integer inventoryId) {
        inventoryRepo.deleteById(inventoryId);
        return "Order deleted";
    }

}
