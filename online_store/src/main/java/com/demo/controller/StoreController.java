package com.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.entities.Product;
import com.demo.entities.Store;
import com.demo.model.ProductDTO;
import com.demo.model.StoreDTO;
import com.demo.service.StoreService;
import com.demo.util.Converter;

@RestController
public class StoreController {

	@Autowired
	private StoreService storeService;
	
	@Autowired
	private Converter converter;
	
	@PostMapping("/createStore")
	ResponseEntity<StoreDTO> createStore(@Valid @RequestBody StoreDTO storeDTO)
	{
		System.out.println(storeDTO);
		final Store store =converter.convertToStoreEntity(storeDTO);
		return new ResponseEntity<StoreDTO>(storeService.creatStore(store),HttpStatus.CREATED);
	}
	
	@PutMapping("/updateStore/{id}")
	ResponseEntity<StoreDTO> updateStore(@Valid @PathVariable("id") int pid,@RequestBody StoreDTO storeDTO)
	{
		final Store product =converter.convertToStoreEntity(storeDTO);
		return new ResponseEntity<StoreDTO>(storeService.updateStore(pid, product),HttpStatus.OK);
	}
	
	@GetMapping("/getAllStores")
	List<StoreDTO> getAllStores()
	{
	return storeService.getAllStore();
	}
	
	@GetMapping("/getStoreById/{id}")
	StoreDTO getStoreById(@PathVariable int id)
	{
		return storeService.getStoreById(id);
	}
	
}


//first complete all crud operations for store
//create cancelProduct() method where the logic will be
//1.after 7days of order, can not be cancelled
//2.after cancel,the stock again will going to increase
//3.Throw exception where you have invoke findById() method