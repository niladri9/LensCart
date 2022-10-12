package com.capg.LensKart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.capg.LensKart.profile.entity.Frame;
import com.capg.LensKart.profile.entity.Glass;
import com.capg.LensKart.profile.entity.Lenses;
import com.capg.LensKart.profile.entity.Product;
import com.capg.LensKart.profile.entity.SunGlasses;
import com.capg.LensKart.profile.profilerepositories.ProductRepository;
import com.capg.LensKart.profile.profileservice.UserService;

@SpringBootTest
class LensKart1ApplicationTests {

	/*@Test
	void contextLoads() {
	}*/
	
	@Mock
	ProductRepository prepo;
	
	@InjectMocks
	UserService uService;
	
	public List<Product> myProducts;
	
	@Test
	public void test_addProducts() {
		String[] shape = new String[] {"oval"};
		String[] size = new String[] {"large"};
		String[] type = new String[] {"photochromatic"};
		String[] powerRange = new String[] {"10"};
	     LocalDateTime a = LocalDateTime.of(2017, 2, 13, 15, 56);
		Frame pFrame = new Frame( 1, "Reban", "black", "test", 501.00, shape,size);
		Glass gLass = new Glass(1, "nike", 455.00,type , powerRange);
		Lenses lEnses = new Lenses(1, "baushlomb", "oval", "red", 1200.00, "3");
		SunGlasses sUnglasses = new SunGlasses(1, "Titan", 2000.00, "Black", "aviator","blue","light");
		Product prod = new Product(1,"lenses",null,null,lEnses,null,50,"testImg",a);
		when(prepo.save(prod)).thenReturn(prod);
		System.out.println(prod);
		assertEquals(1,prod.getProductId());
	}
	@Test
	public void test_getAll() {	
		
		List<Product> myproducts = new ArrayList<Product>();
		String[] shape = new String[] {"oval"};
		String[] size = new String[] {"large"};
		String[] type = new String[] {"photochromatic"};
		String[] powerRange = new String[] {"10"};
	     LocalDateTime a = LocalDateTime.of(2017, 2, 13, 15, 56);
		Frame pFrame = new Frame( 1, "Reban", "black", "test", 501.00, shape,size);
		Glass gLass = new Glass(1, "nike", 455.00,type , powerRange);
		Lenses lEnses = new Lenses(1, "baushlomb", "oval", "red", 1200.00, "3");
		SunGlasses sUnglasses = new SunGlasses(1, "Titan", 2000.00, "Black", "aviator","blue","light");
		myproducts.add(new Product(1,"lenses",null,null,lEnses,null,50,"testImg",a));
		when(prepo.findAll()).thenReturn(myproducts);
		assertEquals(1,((List<Product>) uService.getAll()).size());
	}
}
