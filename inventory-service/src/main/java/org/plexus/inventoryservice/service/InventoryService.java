package org.plexus.inventoryservice.service;

import org.modelmapper.ModelMapper;
import org.plexus.inventoryservice.InventoryServiceApplication;
import org.plexus.inventoryservice.dto.InventoryDTO;
import org.plexus.inventoryservice.excepciones.ResourceNotFoundException;
import org.plexus.inventoryservice.model.Inventory;
import org.plexus.inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {
    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    private ModelMapper modelMapper;
    public List<Inventory> getAll(){
        return inventoryRepository.findAll();
    }

    public Optional<Inventory> getInvetoryBySkuCode (String skuCode){
        return inventoryRepository.findBySkuCode(skuCode);
    }

    public InventoryDTO create (InventoryDTO inventoryDTO){
        Inventory inventory = mapearModel(inventoryDTO);

        Inventory newInventory = inventoryRepository.save(inventory);

        return mapearDTO(newInventory);
    }

    public Inventory read(String skuCode) {
        return this.inventoryRepository.findBySkuCode(skuCode)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found: " + skuCode));
    }

    public InventoryDTO updateById(long id, Integer quantity) {
        Inventory inventory = inventoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id,quantity));
        inventory.setquantity(quantity);
        inventoryRepository.save(inventory);
        return mapearDTO(inventory);
    }



    public InventoryDTO mapearDTO(Inventory inventory){
        InventoryDTO inventoryDTO = modelMapper.map(inventory,InventoryDTO.class);
        return inventoryDTO;
    }

    public Inventory mapearModel (InventoryDTO inventoryDTO){
        Inventory inventory = modelMapper.map(inventoryDTO, Inventory.class);
        return inventory;
    }
}
