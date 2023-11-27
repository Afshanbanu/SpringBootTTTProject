package com.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.entities.OrderDetails;
import com.demo.entities.Product;
import com.demo.model.ProductDTO;
import com.demo.service.ProductService;
import com.demo.util.Converter;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

	@Autowired
	private Converter converter;
	
	@Autowired
	private ProductService productService;
	
	
	@PostMapping("/createProduct")
 ResponseEntity<ProductDTO>	createProduct(@Valid @RequestBody ProductDTO productDTO)
 {
  final Product product =converter.convertToProductEntity(productDTO);
  return new ResponseEntity<ProductDTO>(productService.createProduct(product),HttpStatus.CREATED); 
		  
 }
	
	@PutMapping("/updateProduct/{id}")
	ResponseEntity<ProductDTO> updateProduct(@Valid @PathVariable("id") int pid,@RequestBody ProductDTO productDTO)
	{
		final Product product =converter.convertToProductEntity(productDTO);
		return new ResponseEntity<ProductDTO>(productService.updateProduct(pid, product),HttpStatus.OK);
	}
	
	@GetMapping("/getAllproducts")
	List<ProductDTO> getAllproducts()
	{
	return productService.getAllProduct();
	}
	
	@GetMapping("/getProductById/{id}")
	ProductDTO getProductById(@PathVariable int id)
	{
		return productService.getProductById(id);
	}
	
	@PostMapping("assign/{sid}/{pid}")
	String assignStoreToProduct(@PathVariable int sid,@PathVariable int pid)
	{
		return productService.assignStoreToProduct(sid, pid);
	}
	
	@DeleteMapping("/deleteProduct/{id}")
	String deleteProduct(@PathVariable int id)
	{
	return productService.deleteProduct(id);	
	}
	
	@PostMapping("orderProduct/{pid}")
	 String orderProduct(@PathVariable("pid") int productId,@RequestBody OrderDetails orderDetails)
	 {
		 return productService.orderProduct(productId, orderDetails);
	 }
	
	@DeleteMapping("cancelProduct/{oid}")
	 String cancelProduct(@PathVariable("oid") int orderId)
	 {
		 return productService.cancelProduct(orderId);
	 }
}

