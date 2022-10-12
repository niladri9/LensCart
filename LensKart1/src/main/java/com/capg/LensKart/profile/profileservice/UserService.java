package com.capg.LensKart.profile.profileservice;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.capg.LensKart.profile.entity.Address;
import com.capg.LensKart.profile.entity.Cart;
import com.capg.LensKart.profile.entity.Items;
import com.capg.LensKart.profile.entity.Order1;
//import com.capg.LensKart.profile.entity.Order;
import com.capg.LensKart.profile.entity.Product;
import com.capg.LensKart.profile.entity.Profile;
import com.capg.LensKart.profile.profilerepositories.CartRepository;
import com.capg.LensKart.profile.profilerepositories.ItemRepository;
import com.capg.LensKart.profile.profilerepositories.OrderRepository;
//import com.capg.LensKart.profile.profilerepositories.orderRepository;
import com.capg.LensKart.profile.profilerepositories.ProductRepository;
import com.capg.LensKart.profile.profilerepositories.ProfileRepository;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;


@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private ProfileRepository prorepo;//profile
	@Autowired
	private ProductRepository productrepo;//product
	@Autowired
	private ItemRepository itemrepo;//item
	@Autowired
	private CartRepository cartrepo;//cart
	//@Autowired
	//private orderRepository orderrepo;//cart
	
	@Autowired
	private OrderRepository orderrepo;
	
	
	@Autowired
	private APIContext apiContext;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<Profile> l=prorepo.findByUserName(username);
		System.out.println(l);
		if(username.indexOf('@')>-1) {
		String emailId=l.get(0).getEmailId();
		String password =l.get(0).getPassword();
		System.out.println("list  "+l);
		System.out.println("entered emailID "+username);
		System.out.println("fetched emailId "+emailId);
		System.out.println("fetched password "+password);
		
		return new User(emailId,password,new ArrayList<>());}
		else
		return new User("admin","password",new ArrayList<>());
		}

	
	
	public Profile addProfile(Profile profile) {
		if((prorepo.findByUserName(profile.getEmailId()).size())>0) {
			return null;
		}
		return prorepo.save(profile);
	}
	
	public Product addProducts(Product product) {
		productrepo.save(product);
		return product;
	}
	public void deleteById(int id) {
		productrepo.deleteById(id);

	}
	public Object getAll() {	
		return productrepo.findAll();
		
	}
	public Optional<Product> findById(int id) {
		return productrepo.findById(id);
	}
	public List<Product> findByBrandName(String name) {
		
		List<Product> l1=(List) productrepo.findAll();
		List<Product>l2=new ArrayList();

		for(int i=0;i<l1.size();i++)
		{
			if(l1.get(i).getCategory().equals("frame")) {
				if(l1.get(i).getFrame().getBrandName().equals(name))
					l2.add(l1.get(i));
			}
			if(l1.get(i).getCategory().equals("glass")) {
				if(l1.get(i).getGlass().getBrandName().equals(name))
					l2.add(l1.get(i));
			}
			
			if(l1.get(i).getCategory().equals("frameandglass")) {
				if(l1.get(i).getFrame()!=null)
				{
					if(l1.get(i).getFrame().getBrandName().equals(name))
						l2.add(l1.get(i));
				}
				if(l1.get(i).getGlass()!=null)
				{
					if(l1.get(i).getGlass().getBrandName().equals(name))
						l2.add(l1.get(i));
				}
					
			}
			if(l1.get(i).getCategory().equals("lenses")) {
				if(l1.get(i).getLenses().getBrandName().equals(name))
					l2.add(l1.get(i));
			}
			if(l1.get(i).getCategory().equals("sunglass")) {
				if(l1.get(i).getSunGlasses().getBrandName().equals(name))
					l2.add(l1.get(i));
			}
			
			
		}
		return l2;
	}
	
	public List<Product> findByCategory(String cat) {
		return productrepo.findByCategory(cat);
	}
	public List getAllProfiles() {
		return (List) prorepo.findAll();
	}
	public Profile viewProfile(String userNam) {
		List<Profile> l=prorepo.findByUserName(userNam);
		
			return l.get(0);
			
	}
	public String updateProfile(String userNam, Profile profile) {
		List<Profile> l=prorepo.findByUserName(userNam);
		if(userNam.indexOf('@')>-1) {
			
			String role=l.get(0).getRole();
			int id=l.get(0).getProfileId();
			String password=l.get(0).getPassword();
			
			String fullName=profile.getFullName();
			String emailId=profile.getEmailId();
			long mobileNumber=profile.getMobileNumber();
			String about=profile.getAbout();
			String age=profile.getAge();
		 	String gender=profile.getGender();
			
			Address address=profile.getAddress();
			
			Profile pro=new Profile(id,fullName,emailId,mobileNumber,about,age,gender,role,password,address);
			
			prorepo.save(pro);
			return "PROFILE UPDATED SUCCESSFULLY";
			
		}
		return "PROFILE UPDATE FAILED TRY AGAIN";
	}
	public String deleteProfile(String userNam) {
		List<Profile> l=prorepo.findByUserName(userNam);
		prorepo.delete(l.get(0));
		return "POFILE DELETED SUCCESSFULLY";
	}

	public String productVerifyAdmin(String userNam) {
		List<Profile> l=prorepo.findByUserName(userNam);
		if(userNam.indexOf('@')>-1) {
			String role=l.get(0).getRole();
			
			if(role.equalsIgnoreCase("admin"))
			{
				return "true";
			}
			else
			{
				return "false";
			}
		}
		else
			return null;
	}



	public void saveItem(Items item) {
		itemrepo.save(item);
		
	}
	
	public List<Items> getItem(String name,String usName) {
		return itemrepo.findByName(name,usName);
		
	}
public void  deleteItems(Items item2) {
		
		itemrepo.deleteById(item2.getProductId());
	}



public void deleteItem(Items items) {
	itemrepo.delete(items);
	
}

public List<Items> getItemsByUserName(String usName) {
	
	return itemrepo.findItemsByUSerName(usName);
}



public Cart saveCart(Cart cart) {
	return cartrepo.save(cart);
}
public List<Cart> getCartByUserName(String usName) {
	List l=cartrepo.getCartByUserName(usName);
	return l;
}



public int getLastCartId() {
	List<Cart> l=(List<Cart>) cartrepo.findAll();
	if(l.size()>0)
		return (l.get(l.size()-1)).getCatrId();
	else
		return 0;
}

public int getLastOrderId() {
	List<Order1> l=(List<Order1>) orderrepo.findAll();
	if(l.size()>0)
		return (l.get(l.size()-1)).getOrderId();
	else
		return 0;
}

public String UpdateListingByIdQuantity(int id,int quantity) {
	
	
	Optional<Product> op = findById(id);
	
		Product ord = op.get();
		ord.setStockQuantity(ord.getStockQuantity()-quantity);
		
		addProducts(ord);
		return "STOCK QUANTITY UPDATED";
	
}

public List placeOrder(String usName,String payMode) {
	
	int customerId=getCartByUserName(usName).get(0).getCatrId();
	double amount=getCartByUserName(usName).get(0).getTotalPrice();
	String status="Placed";
	LocalDateTime localDateTime = LocalDateTime.now();
	System.out.println(customerId);
	System.out.println(amount);
	
	Order1 order=new Order1(localDateTime,customerId,amount,payMode,status);
	orderrepo.save(order);
	List l=new ArrayList();
	l.add("ORDER DETAILS");
	l.add(order);
	l.add("DELIVERY ADDRESS DETAILS");
	l.add(viewProfile(usName).getAddress());
	l.add("ORDER ITEMS DETAILS");
	List<Items> items=getItemsByUserName(usName);
	
	for(int i=0;i<items.size();i++)
	{
		int quantity=items.get(i).getQuantity();
		int id=items.get(i).getProductId();
		
			String r = UpdateListingByIdQuantity(id,quantity);
		if(r.equals("STOCK QUANTITY UPDATED"))
			System.out.println("Item Stock Quantity chnage successful");
		else
			System.out.println("Item Stock Quantity chnage unsuccessful");
		l.add(items.get(i));
		System.out.println(id);
	}
	return l ;
	
	
	
	}



public List<Order1> findOrderById(String usName) {
	int id=getCartByUserName(usName).get(0).getCatrId();
	Optional<Order1> op=orderrepo.findById(id);
	if(op!=null)
	{
	Order1 o =op.get();
	List<Order1> l=new ArrayList();
	l.add(o);
	return l;
	}
	else
	{
		List l=new ArrayList();
		l.add("NO ORDERS FOUND FOR GIVEN ID");
		return l;
	}
	
}



public double getMyOrderAmt(String usName) {
	double amt = findOrderById(usName).get(0).getAmountPaid();
	return amt;
}


public Payment createPayment(
		Double total, 
		String currency, 
		String method,
		String intent,
		String description, 
		String cancelUrl, 
		String successUrl) throws PayPalRESTException{
	Amount amount = new Amount();
	amount.setCurrency(currency);
	total = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
	amount.setTotal(String.format("%.2f", total));

	Transaction transaction = new Transaction();
	transaction.setDescription(description);
	transaction.setAmount(amount);

	List<Transaction> transactions = new ArrayList<>();
	transactions.add(transaction);

	Payer payer = new Payer();
	payer.setPaymentMethod(method.toString());

	Payment payment = new Payment();
	payment.setIntent(intent.toString());
	payment.setPayer(payer);  
	payment.setTransactions(transactions);
	RedirectUrls redirectUrls = new RedirectUrls();
	redirectUrls.setCancelUrl(cancelUrl);
	redirectUrls.setReturnUrl(successUrl);
	payment.setRedirectUrls(redirectUrls);

	return payment.create(apiContext);
}

public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
	Payment payment = new Payment();
	payment.setId(paymentId);
	PaymentExecution paymentExecute = new PaymentExecution();
	paymentExecute.setPayerId(payerId);
	return payment.execute(apiContext, paymentExecute);
}


	

	
}

