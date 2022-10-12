package com.capg.LensKart.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.capg.LensKart.profile.entity.Cart;
import com.capg.LensKart.profile.entity.Items;
import com.capg.LensKart.profile.entity.JwtRequest;
import com.capg.LensKart.profile.entity.JwtResponse;
import com.capg.LensKart.profile.entity.Product;
import com.capg.LensKart.profile.entity.Profile;
import com.capg.LensKart.profile.jwt.Utility.JwtUtility;
import com.capg.LensKart.profile.profileservice.UserService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "ShoppingProfile")
@CrossOrigin("*")
@RequestMapping("/allowed/")
public class ProfileController {

	@Autowired
	private JwtUtility jwtUtility;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;

	
	private String t;
//======================================PROFILE=========================================================
//--------------------------JWT REQUIRED AUTHENTICATION------------------------------------	
	@GetMapping("/")
	public String test()
	{
		return "Hello Security test";
	}
	
	@PostMapping("/authenticate")
	public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest)throws Exception
	{
		try {
			//GENERATION FIRST 2 PART TOKEN
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails
                = userService.loadUserByUsername(jwtRequest.getUsername());
        System.out.println("user details "+userDetails);
        //SIGNING
        final String token =
                jwtUtility.generateToken(userDetails);

        return  new JwtResponse(token);
	}
	
//---------------------------------------------------------------------------------------------
//---------------------------WITHOUT AUTHENTICATION CREATE NEW USER--------------------------------------------
	@PostMapping("/Signup")
	public ResponseEntity<Profile> addProfile(@RequestBody Profile profile) {

		
		profile.setPassword(bCryptPasswordEncoder.encode(profile.getPassword()));
		
		Profile pro = userService.addProfile(profile);
		if(pro == null) {
			return new ResponseEntity<Profile>(pro, HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<Profile>(pro, HttpStatus.CREATED);

	}

	//--------------------------VERIFY ADMIN-----------------------------------------------------------------------
	
	public Boolean verifyAdmin(String authorization)
	{ 
		String t = null;
      //  String userName = null;

        if(null != authorization && authorization.startsWith("Bearer ")) {
            t = authorization.substring(7);
            
        }
        String userNam=jwtUtility.getUsernameFromToken(t);
        String s=userService.productVerifyAdmin(userNam);
        if(s.equals("true"))
        return true;
        else
        return false;
	}
	//--------------------------GET ALL PROFILES ONLY ADMIN AUTHORIZATION---------------------------
	
		@GetMapping("/admin/GetAllProfiles")
		public List getAllProfiles(@RequestHeader("Authorization") String token)
		{
			
			boolean b= verifyAdmin(token);
			if(b==true) {
	        
	        List l=userService.getAllProfiles();
			return l;
			}
			else
			{
				List l=new ArrayList();
				l.add("MUST BE AN ADMIN TO VIEW ALL PROFILES");
				return l;
			}
		}
		
		//---------------VIEW CURRENT LOGGED IN PROFILE BOTH ADMIN AND USER AUTHORIZATION---------------------------
		@GetMapping("/ViewProfile")
		public Profile viewProfile(@RequestHeader("Authorization") String authorization)
		{
			//String authorization = request.getHeader("Authorization");
	        String t = null;
	        String userName = null;

	        if(null != authorization && authorization.startsWith("Bearer ")) {
	            t = authorization.substring(7);
	            
	        }
	        String userNam=jwtUtility.getUsernameFromToken(t);
	        Profile pf=userService.viewProfile(userNam);
	        return pf;
	        
		}
		//---------------UPDATE CURRENT LOGGED IN PROFILE BOTH ADMIN AND USER AUTHORIZATION---------------------------
		@PutMapping("/UpdateProfile")
		public String updateProfile(@RequestBody Profile profile)
		{
			String authorization = request.getHeader("Authorization");
	        String t = null;
	        String userName = null;

	        if(null != authorization && authorization.startsWith("Bearer ")) {
	            t = authorization.substring(7);
	            
	        }
	        String userNam=jwtUtility.getUsernameFromToken(t);
			profile.setPassword(bCryptPasswordEncoder.encode(profile.getPassword()));
	        String s=userService.updateProfile(userNam,profile);
	        return s;
	        		
		}
		//-----------------DELETE CURRENT PROFILe--------------------------------------------
		@DeleteMapping("/DeleteProfile")
		public String deleteProfile()
		{
			String authorization = request.getHeader("Authorization");
	        String t = null;
	        String userName = null;

	        if(null != authorization && authorization.startsWith("Bearer ")) {
	            t = authorization.substring(7);
	            
	        }
	        String userNam=jwtUtility.getUsernameFromToken(t);
	        String s=userService.deleteProfile(userNam);
	        return s;
		}
//=============================================PRODUCT===================================================================		
	//--------------------------------------------ADMIN CRUD PRODUCTS------------------------------------------
	@PostMapping("/admin/AddProducts")
	public ResponseEntity<Product> addProducts(@RequestBody Product product,@RequestHeader("Authorization") String token) {
		
		
		boolean b= verifyAdmin(token);
		LocalDateTime localDateTime = LocalDateTime.now();
		if(b==true)
		{
		
		product.setArrivalDate(localDateTime);
		Product prod = userService.addProducts(product);
		return new ResponseEntity<Product>(prod, HttpStatus.CREATED);
		}
		else
		{
			return new ResponseEntity("AUTHORIZATION FAILED", HttpStatus.FORBIDDEN);
		}

	}
	@DeleteMapping("/admin/deleteproduct/{id}")
	public ResponseEntity<String> deleteProductById(@PathVariable("id") int id,@RequestHeader("Authorization") String token) {
		
		boolean b= verifyAdmin(token);
		
		if(b==true)
		{
		userService.deleteById(id);
		return new ResponseEntity<String>("DELETE SUCCESFULLY", HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity("AUTHORIZATION FAILED", HttpStatus.FORBIDDEN);
		}
		
		
	}
	@PutMapping("/admin/updateProduct/{id}")
	public ResponseEntity<Object> UpdateListingById(@PathVariable("id") int id, @RequestBody Product product,@RequestHeader("Authorization") String token) {
		
		boolean b= verifyAdmin(token);
		LocalDateTime localDateTime = LocalDateTime.now();
		
		
		if(b==true)
		{
		Optional<Product> op = userService.findById(id);
		if (op.isPresent()) {
			Product ord = op.get();
			
			
			ord.setCategory(product.getCategory());
			
			if(product.getCategory().equals("frame"))
			ord.setFrame(product.getFrame());
			
			if(product.getCategory().equals("glass"))
			ord.setGlass(product.getGlass());
			
			if(product.getCategory().equals("frameandglass")) {
			ord.setGlass(product.getGlass());
			ord.setFrame(product.getFrame());
			}
			
			if(product.getCategory().equals("lenses"))
			ord.setLenses(product.getLenses());
			
			if(product.getCategory().equals("sunglasses"))
			ord.setSunGlasses(product.getSunGlasses());
			
			ord.setStockQuantity(product.getStockQuantity());
			ord.setProductImg(product.getProductImg());
			ord.setArrivalDate(localDateTime);
			
			userService.addProducts(ord);
			return new ResponseEntity<Object>(ord, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>("Not updated successfully", HttpStatus.NOT_FOUND);
		}
		}
		else
		{
			return new ResponseEntity("AUTHORIZATION FAILED", HttpStatus.FORBIDDEN);
		}

	}
	//------------------------------------------------------------------------------------------------------------
	//-------------------------NO AUTHENTICATION GET PRODUCTS-----------------------------------------------------
	/*@PutMapping("/allowed/updateProductQuantity/{id}/{quantity}")
	public String UpdateListingByIdQuantity(@PathVariable("id") int id,@PathVariable("quantity") int quantity) {
		
		
		Optional<Product> op = userservice.findById(id);
		
			Product ord = op.get();
			ord.setStockQuantity(ord.getStockQuantity()-quantity);
			
			userservice.addProducts(ord);
			return "STOCK QUANTITY UPDATED";
		
	}*/
	@GetMapping("/allproducts")
	public List<Product> getAllProducts() {
		List<Product> list = new ArrayList<>();
		((ArrayList<Product>) userService.getAll()).forEach(list::add);
		return list;

	}
	@GetMapping("/getProductById/{id}")
	public ResponseEntity<Object> getProductById(@PathVariable("id") int id) {
		
		
		Optional<Product> op = userService.findById(id);
		if (op.isPresent()) {
			Product product = op.get();
			return new ResponseEntity<Object>(product, HttpStatus.OK);
		}
		return new ResponseEntity<Object>("Problem in Fetching Data", HttpStatus.NOT_FOUND);
		
		
	}
	@GetMapping("/getProductByBrandName/{name}")
	public List<Product> getProductByName(@PathVariable("name") String name) {
		
		
		List<Product> op = userService.findByBrandName(name);
		if (!op.isEmpty()) {
			return op;
		}
		return null;
		
		
	}
	
	
	
	@GetMapping("/getProductByCategory/{cat}")
	public List<Product> getProductByCategory(@PathVariable("cat") String cat) {
		
		
		List<Product> op = userService.findByCategory(cat);
		if (!op.isEmpty()) {
			return op;
		}
		return null;
		
		
	}
	//-------------------------------------------------------------------------------------------------------------
	//============================================ITEMS============================================================
	//------------ADD ITEMS BY PRODUCTID-----------------------------------------------------
		@PostMapping("items/{id}/{qty}")
		public List getProductwithCartByName(@PathVariable("id") int id,@PathVariable("qty") int qty) {
			
			String authorization = request.getHeader("Authorization");
	        String t = null;
	      //  String userName = null;

	        if(null != authorization && authorization.startsWith("Bearer ")) {
	            t = authorization.substring(7);
	            
	        }
	        String usName=jwtUtility.getUsernameFromToken(t);
			
			List l1=new ArrayList();	
			
			Optional<Product> op= userService.findById(id);
			Product product = op.get();
			if(op!=null)
			
			{
				
				String naa="";
				double price=0.0;
				int idd= product.getProductId();
				String cat=product.getCategory();
				String img=product.getProductImg();
				
				if(cat.equals("frame"))
				{
				naa=product.getFrame().getBrandName()+" Frame";
				price=product.getFrame().getPrice();
				}
				else if(cat.equals("glass"))
				{
				naa=product.getGlass().getBrandName()+" Glass";
				price=product.getGlass().getPrice();
				}
				else if(cat.equals("frameandglass"))
				{
				naa=product.getFrame().getBrandName()+" Frame & "+product.getGlass().getBrandName()+" Glass";
				price=product.getFrame().getPrice()+product.getGlass().getPrice();
				}
				else if(cat.equals("lenses"))
				{
				naa=product.getLenses().getBrandName()+" Lenses";
				price=product.getLenses().getPrice();
				}
				else if(cat.equals("sunglasses"))
				{
				naa=product.getSunGlasses().getBrandName()+" SunGlasses";
				price=product.getSunGlasses().getPrice();
				}
				System.out.println(idd);
				System.out.println(naa);
				System.out.println(price);
				System.out.println(qty);
				System.out.println(cat);
				Items item=new Items(idd,naa,price,qty,cat,usName,img);
				l1.add(item);
				userService.saveItem(item);

				return l1;
			}
			else
			{
				List l=new ArrayList();
				l.add("AUTHORIZATION FAILED");
				return l;
			}
			
				
		}
		
		//--------------------CHANGE ITEM QUANTITY----------------------------------------------------------------------
		
		@PutMapping("/itemChangeQuantity/{name}/{qty}")
		public List<Items> changeQuantity(@PathVariable("name") String name,@PathVariable("qty") int qty)
		{
			String authorization = request.getHeader("Authorization");
	        String t = null;
	        String userName = null;

	        if(null != authorization && authorization.startsWith("Bearer ")) {
	            t = authorization.substring(7);
	            
	        }
	        String usName=jwtUtility.getUsernameFromToken(t);
			
			List<Items> l=userService.getItem(name,usName);
			//Items [productId=0, productName=laptop, price=50000.22, quantity=1, category=electronics]
			List l1=new ArrayList();
			for(int i=0;i<l.size();i++)
			{
				
				String naa = l.get(i).getProductName();
				int idd = l.get(i).getProductId();
				double pr = l.get(i).getPrice();
				String cat = l.get(i).getCategory();
				int quantity = l.get(i).getQuantity();
				String img=l.get(i).getProductImg();
				
				System.out.println(idd);
				System.out.println(naa);
				System.out.println(pr);
				System.out.println(qty);
				System.out.println(cat);
				
				Items item=new Items(idd,naa,pr,quantity,cat,usName,img);
				userService.deleteItems(item);
				Items item1=new Items(idd,naa,pr,qty,cat,usName,img);
				l1.add(item1);
				userService.saveItem(item1);
				
				
			}
			return l1;
			
		}
	//-------------------------------DELETE ITEMS-----------------------------------------------------------------
		@DeleteMapping("/deleteItem/{name}")
		public List updateDelProductwithCartByName(@PathVariable("name") String name) {
			
			String authorization = request.getHeader("Authorization");
	        String t = null;
	        String userName = null;

	        if(null != authorization && authorization.startsWith("Bearer ")) {
	            t = authorization.substring(7);
	            
	        }
	        String usName=jwtUtility.getUsernameFromToken(t);
	        
	        List<Items> l=userService.getItem(name,usName);
	        userService.deleteItem(l.get(0));
		
		return l;
		
	}
		//------------------------------GET ALL ITEMS  BY USERNAME------------------
				@GetMapping("/GetAllItemsByUsername")
				public List getAllItemsByUsername()
				{
					try {
					String authorization = request.getHeader("Authorization");
			        String t = null;
			        String userName = null;

			        if(null != authorization && authorization.startsWith("Bearer ")) {
			            t = authorization.substring(7);
			            
			        }
			        String usName=jwtUtility.getUsernameFromToken(t);
					List<Items> list = userService.getItemsByUserName(usName);
					return list;
					}catch(Exception e)
					{
						List l=new ArrayList();
						l.add("AUTHORIZATION FAILED");
						return l;
					}
					
					
					
				}
	//=======================================================CART=========================================================
				//----------------------------GET LAST CART ID INTERNAL---------------------------------
				
				public int getLastCartId()
				{
					int id=userService.getLastCartId();
					return id;
					
				}
				
				//-------------ADD TO CART-----------------------------------	
				@PostMapping("/addcart")
				public ResponseEntity<Cart> addcart() {

					String authorization = request.getHeader("Authorization");
			        String t = null;
			        String userName = null;
			        
			        if(null != authorization && authorization.startsWith("Bearer ")) {
			            t = authorization.substring(7);
			            
			        }
			        String usName=jwtUtility.getUsernameFromToken(t);
						List<Items> list = userService.getItemsByUserName(usName);
						double totalPrice=0.0;
						for(int i=0;i<list.size();i++)
						{
							totalPrice=totalPrice+(list.get(i).getPrice()*list.get(i).getQuantity());
						}
						Cart cart=new Cart(getLastCartId()+1,totalPrice,usName);
						Cart c = userService.saveCart(cart);
						return new ResponseEntity<Cart>(c, HttpStatus.CREATED);
						
						
						
				}
				
				
				
				//----------------------------GET CART BY USERNAME----------------------------------
				@GetMapping("/GetCartByUsername")
				public Object getCartByUserName()
				{
					String authorization = request.getHeader("Authorization");
			        String t = null;
			        String userName = null;
			        Cart c1;
			        if(null != authorization && authorization.startsWith("Bearer ")) {
			            t = authorization.substring(7);
			            
			        }
			        String usName=jwtUtility.getUsernameFromToken(t);
						List<Cart> l= userService.getCartByUserName(usName);
						return l.get(0);
					
				}
				//-------------UPDATE  CART-----------------------------------	
				@PutMapping("/updatecart")
				public ResponseEntity<Cart> updatecart() {

					String authorization = request.getHeader("Authorization");
			        String t = null;
			        String userName = null;

			        if(null != authorization && authorization.startsWith("Bearer ")) {
			            t = authorization.substring(7);
			            
			        }
			        String usName=jwtUtility.getUsernameFromToken(t);
						List<Items> list = userService.getItemsByUserName(usName);
						
						double totalPrice=0.0;
						for(int i=0;i<list.size();i++)
						{
							totalPrice=totalPrice+(list.get(i).getPrice()*list.get(i).getQuantity());
						}
						
						List<Cart> l1=userService.getCartByUserName(usName);
						
								
						int id=l1.get(0).getCatrId();
						Cart cart=new Cart(id,totalPrice,usName);
						Cart c = userService.saveCart(cart);
						return new ResponseEntity<Cart>(c, HttpStatus.CREATED);
						
			
				}
//===================================================ORDER=====================================================================================
				@PostMapping("/PlaceOrder/{payMode}")
				public List PlaceOrder(@PathVariable("payMode") String payMode)
				{
					String authorization = request.getHeader("Authorization");
			        String t = null;
			        String userName = null;

			        if(null != authorization && authorization.startsWith("Bearer ")) {
			            t = authorization.substring(7);
			            
			        }
			        String usName=jwtUtility.getUsernameFromToken(t);
			    	System.out.println(usName);
					if(payMode.equalsIgnoreCase("CASH") || payMode.equalsIgnoreCase("PAYPAL") || payMode.equalsIgnoreCase("INTERNET BANKING") || payMode.equalsIgnoreCase("CREDIT CARD") || payMode.equalsIgnoreCase("DEBIT CARD")) {
						List l=userService.placeOrder(usName,payMode);
					
						return l;
					}
					else {
						List l = new ArrayList();
						l.add("INVALID PAYMENT MODE");
						l.add("AVAILABLE PAYMENT MODES : ");
						l.add("CASH");
						l.add("PAYPAL");
						l.add("INTERNET BANKING");
						l.add("CREDIT CARD");
						l.add("DEBIT CARD");
						
						return l;
					}
			    
					
				}
				@GetMapping("/GetMyOrder")
				public List getMyOrder(@RequestHeader("Authorization") String token)
				{
					
					String authorization = request.getHeader("Authorization");
			        String t = null;
			        String userName = null;

			        if(null != authorization && authorization.startsWith("Bearer ")) {
			            t = authorization.substring(7);
			            
			        }
			        String usName=jwtUtility.getUsernameFromToken(t);
						
						List l=userService.findOrderById(usName);
						return l;
						
					
				}
				
				
				// ============================================================== PAYMENT ==============================================================
				
				public static final String SUCCESS_URL = "Payment/pay/success";
				public static final String CANCEL_URL = "Payment/pay/cancel";

							

				@PostMapping("/pay")
				public String payment(ModelAndView modelAndView) {
					
					String authorization = request.getHeader("Authorization");
			        String t = null;
			        String userName = null;

			        if(null != authorization && authorization.startsWith("Bearer ")) {
			            t = authorization.substring(7);
			            
			        }
			        String usName=jwtUtility.getUsernameFromToken(t);
					double amt = userService.getMyOrderAmt(usName);
					
					try {

						Payment payment = userService.createPayment(amt / 79.86, "USD",
								"PAYPAL", "sale", "shopping cart",
								"http://localhost:8001/" + CANCEL_URL,
								"http://localhost:8001/" + SUCCESS_URL);
						for (Links link : payment.getLinks()) {
							if (link.getRel().equals("approval_url")) {
								//modelAndView.setViewName(link.getHref().toString());
								return link.getHref();
							}
						}
					} catch (PayPalRESTException e) {

						e.printStackTrace();
					}

					return "/";

				}

				@GetMapping(value = CANCEL_URL)
				public ModelAndView cancelPay(ModelAndView modelAndView) {
					modelAndView.setViewName("cancel");
					return modelAndView;
				}

				@GetMapping(value = SUCCESS_URL)
				public ModelAndView successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId,
						ModelAndView modelAndView) {
					try {
						Payment payment = userService.executePayment(paymentId, payerId);
						System.out.println(payment.toJSON());
						if (payment.getState().equals("approved")) {
							modelAndView.setViewName("success");
							return modelAndView;
						}
					} catch (PayPalRESTException e) {
						System.out.println(e.getMessage());
					}
					modelAndView.setViewName("redirect:/");
					return modelAndView;

				}

				@GetMapping("/show")
				public String show() {
					return "CANNOT RETURN MODEL AND VIEW USE BROWSER";
				}


				
				
				
				
}