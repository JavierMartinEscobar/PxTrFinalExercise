package org.plexus.inventoryservice.controller;


import org.plexus.inventoryservice.dto.InventoryDTO;
import org.plexus.inventoryservice.model.Inventory;
import org.plexus.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;
    private Object mapper;
    private static final int QUANTITY_TO_ADD = 10;

    @GetMapping
    public ResponseEntity<List<Inventory>> getInventory (){
        return ResponseEntity.ok(inventoryService.getAll());
    }
    @GetMapping(value = "/{skuCode}")
    public ResponseEntity<Inventory> getInventoryBySkuCode (@PathVariable String skuCode){
        Optional<Inventory> inventory = inventoryService.getInvetoryBySkuCode(skuCode);
        return inventory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{skuCode}")
    public ResponseEntity<InventoryDTO> add(@PathVariable long id) {
        return ResponseEntity.ok(inventoryService.updateById(id,QUANTITY_TO_ADD));
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteBySkuCode(@RequestParam long id, @RequestParam Integer quantity) {
        inventoryService.updateById(id,quantity);
        return ResponseEntity.ok("Inventory deleted successfully");
    }
}
