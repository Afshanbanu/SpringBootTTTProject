package com.demo.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.entities.Product;
import com.demo.entities.Store;
import com.demo.exception.ResourceNotFoundException;
import com.demo.model.ProductDTO;
import com.demo.model.StoreDTO;
import com.demo.repositories.StoreRepository;
import com.demo.service.StoreService;
import com.demo.util.Converter;

@Service
public class StoreServiceImpl implements StoreService{

	@Autowired
	private StoreRepository storeRepository;
	
	@Autowired
private Converter converter;
	
	@Override
	public StoreDTO creatStore(Store store) {
		
		return converter.convertToStoreDTO(storeRepository.save(store));
	}

	@Override
	public StoreDTO updateStore(int id, Store store) {
		Store s=storeRepository.findById(id).get();
		s.setStoreName(store.getStoreName());
		s.setAddress(store.getAddress());
		s.setProducts(store.getProducts());
		
		storeRepository.save(s);
		return converter.convertToStoreDTO(s);
	}

	@Override
	public String deleteStore(int id) {
		Store p=storeRepository.findById(id).get();
		storeRepository.delete(p);
		return "Store delete successfully";
	}

	@Override
	public List<StoreDTO> getAllStore() {
		List<Store> sl=storeRepository.findAll();
		
		List<StoreDTO> sDTOS=new ArrayList<>();
		
		for(Store s:sl)
		{
		sDTOS.add(converter.convertToStoreDTO(s));
		}
			return sDTOS;
	}

	@Override
	public StoreDTO getStoreById(int id) {
		Optional<Store> store=storeRepository.findById(id);
		Store s;
		if(store.isPresent())
		{
			 s=store.get();
		}
		else {
			throw new ResourceNotFoundException("Store","id",id);
		}
		return converter.convertToStoreDTO(s);
	}

}
