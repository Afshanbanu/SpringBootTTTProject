package com.demo.service;

import java.util.List;

import com.demo.entities.OrderDetails;
import com.demo.entities.Product;
import com.demo.entities.Store;
import com.demo.model.ProductDTO;
import com.demo.model.StoreDTO;

public interface StoreService {
StoreDTO creatStore(Store store);

StoreDTO updateStore(int id,Store store);
String deleteStore(int id);
List<StoreDTO> getAllStore();
StoreDTO getStoreById(int id);


}
