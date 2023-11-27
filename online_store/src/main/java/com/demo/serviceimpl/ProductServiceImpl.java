package com.demo.serviceimpl;

import java.lang.module.ResolutionException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.entities.OrderDetails;
import com.demo.entities.Product;
import com.demo.entities.Store;
import com.demo.exception.ResourceNotFoundException;
import com.demo.model.ProductDTO;
import com.demo.repositories.OrderRepository;
import com.demo.repositories.ProductRepository;
import com.demo.repositories.StoreRepository;
import com.demo.service.ProductService;
import com.demo.util.Converter;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
private	ProductRepository productRepository;
	@Autowired
private Converter converter;

	@Autowired
	private StoreRepository storeRepository;
   @Autowired
	private OrderRepository orderRepository;
	
	@Override
	public ProductDTO createProduct(Product product) {
	//Product p=productRepository.save(product);
		return converter.convertToProductDTO(productRepository.save(product));
	}


	@Override
	public ProductDTO updateProduct(int id, Product product){
		Product p=productRepository.findById(id).get();
		p.setProductName(product.getProductName());
		p.setProductPrice(product.getProductPrice());
		p.setStock(product.getStock());
		
		productRepository.save(p);
		return converter.convertToProductDTO(p);
		
		
//		Optional<Product> p=productRepository.findById(id);
//		if(p.isPresent())
//		{
//			Product prod=p.get();
//		}
//		else
//			throw new Exception("product id not found");
		
	}


	@Override
	public String deleteProduct(int id) {
		Product p=productRepository.findById(id).get();
		productRepository.delete(p);
		return "Product delete successfully";
	}


	@Override
	public List<ProductDTO> getAllProduct() {
	List<Product> products=productRepository.findAll();
	
	List<ProductDTO> productDTOS=new ArrayList<>();
	
	for(Product p:products)
	{
	productDTOS.add(converter.convertToProductDTO(p));
	}
		return productDTOS;
	}


	@Override
	public ProductDTO getProductById(int id) throws ResourceNotFoundException {
		Optional<Product> prod=productRepository.findById(id);
		Product p;
		if(prod.isPresent())
		{
			 p=prod.get();
		}
		else {
			throw new ResourceNotFoundException("Product","id",id);
		}
		return converter.convertToProductDTO(p);
	}


	@Override
	public String assignStoreToProduct(int storeId, int pid) {
		Store store=storeRepository.findById(storeId).get();
		Product p=productRepository.findById(pid).get();
		
		p.setStore(store);
		
		List<Product> products=new ArrayList<>();
		products.add(p);
		
		store.setProducts(products);
		
		productRepository.save(p);
		return "Store Id added successfully";
	}


	@Override
	public String orderProduct(int productId,OrderDetails orderDetails) {
		Product p=productRepository.findById(productId).get();
		double totalAmount=0.0;
		String message;
		if(p!=null)
		{
			 totalAmount=(orderDetails.getQuantity()*p.getProductPrice());
			orderDetails.setTotalAmount(totalAmount);
			List<Product> products=new ArrayList<>();
			products.add(p);
			orderDetails.setProducts(products);
			p.setStock(p.getStock()-orderDetails.getQuantity()); //30-2=28
			productRepository.save(p);
			orderRepository.save(orderDetails);
			message="Your order has been placed successfully!!"
					+ "Your total Amount is:"+totalAmount+"Your order will deliver within 7days";
		}
		else
			message="Product is null";
		
		return message;
	}


	@Override
	public String cancelProduct(int orderid) {
		OrderDetails o=orderRepository.findById(orderid).orElseThrow(()->
		new ResourceNotFoundException("Order","Id",orderid));;
		List<Product> products=o.getProducts();
		LocalDate date= LocalDate.now();
		String message=null;;
		if(o!=null)
		{
			int days=Period.between(o.getOrderDate(),date).getDays();
			 if(days<=7)
			 {
				 for(Product p:products )
				 {
				 p.setStock(p.getStock()+o.getQuantity());
				 productRepository.save(p);
				 }
				 
				 orderRepository.delete(o);
				
			message="Your order has been cancelled successfully!!"
					+ "Your total Amount :"+o.getTotalAmount()+" will refund within 10days";
			 }
		}
		else
			message="Order is null";
		
		return message;
	}

	
	

}
