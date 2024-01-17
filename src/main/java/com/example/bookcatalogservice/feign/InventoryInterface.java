package com.example.bookcatalogservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient("INVENTORY-SERVICE")
public interface InventoryInterface {

    @PutMapping(value = "api/v1/inventory/updateTheStockOfParticularTitle/{title}")
    public ResponseEntity updateListOfBooksForTitle(@PathVariable String title);
}
