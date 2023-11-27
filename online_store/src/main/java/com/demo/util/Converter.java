package com.demo.util;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.demo.entities.Product;
import com.demo.entities.Store;
import com.demo.model.ProductDTO;
import com.demo.model.StoreDTO;

@Component
public class Converter {

	
	//convert from DTO to Entity-convert from DTO to entity and return entity object
	
	public Product convertToProductEntity(ProductDTO productDTO)
	{
		Product product=new Product();
		if(productDTO!=null)
		{
			BeanUtils.copyProperties(productDTO, product);
		}
		return product;
	}
	
	//convert from Entity to DTO-convert from Entity to DTO  and return DTO object
	
	public ProductDTO convertToProductDTO(Product product)
	{
		ProductDTO productDTO=new ProductDTO();
		if(product!=null)
		{
			BeanUtils.copyProperties(product, productDTO);
		}
		return productDTO;
	}
	
	//same do for Store
	
	public StoreDTO convertToStoreDTO(Store store)
	{
		StoreDTO storeDTO=new StoreDTO();
		if(store!=null)
		{
			BeanUtils.copyProperties(store, storeDTO);
		}
		return storeDTO;
	}
	
	public Store convertToStoreEntity(StoreDTO storeDTO)
	{
		Store store=new Store();
		if(storeDTO!=null)
		{
			BeanUtils.copyProperties(storeDTO, store);
		}
		return store;
	}
}
