package com.model2.mvc.service.purchase.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseDao;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;


/*
 *	FileName :  UserServiceTest.java
 * ㅇ JUnit4 (Test Framework) 과 Spring Framework 통합 Test( Unit Test)
 * ㅇ Spring 은 JUnit 4를 위한 지원 클래스를 통해 스프링 기반 통합 테스트 코드를 작성 할 수 있다.
 * ㅇ @RunWith : Meta-data 를 통한 wiring(생성,DI) 할 객체 구현체 지정
 * ㅇ @ContextConfiguration : Meta-data location 지정
 * ㅇ @Test : 테스트 실행 소스 지정
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/context-common.xml",
									"classpath:config/context-aspect.xml",
									"classpath:config/context-mybatis.xml",
									"classpath:config/context-transaction.xml"})
public class PurchaseServiceTest {

	//==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;

	@Test
	public void testAddPurchase() throws Exception {
		
		Product product = new Product();
		product.setProdNo(10020);
		
		User user = new User();
		user.setUserId("user10");
		user.setUserName(null);
		user.setPhone(null);
		user.setAddr(null);
		
		Purchase purchase = new Purchase();
		purchase.setPurchaseProd(product);
		purchase.setBuyer(user);
		purchase.setPaymentOption("1");
		purchase.setReceiverName(user.getUserName());
		purchase.setDivyAddr(user.getAddr());
		purchase.setDivyRequest("빠배");
		purchase.setDivyDate(null);
		
		purchaseService.addPurchase(purchase);
		
	
		//==> console 확인
		System.out.println(purchase);
		
		//==> API 확인
//		Assert.assertEquals(product, purchase.getPurchaseProd());
//		Assert.assertEquals(user, purchase.getBuyer());
//		Assert.assertEquals("1",purchase.getPaymentOption());
//		Assert.assertEquals(user.getUserName(), purchase.getReceiverName());
//		Assert.assertEquals(user.getAddr(), purchase.getDivyAddr());
//		Assert.assertEquals("빠배", purchase.getDivyRequest());
//		Assert.assertEquals(null, purchase.getDivyDate());
	
	}
	
//	@Test
	public void testGetPurchase() throws Exception {
		
		Purchase purchase = new Purchase();
		purchase = purchaseService.getPurchase(10028);

		//==> console 확인
		//System.out.println(user);
		
		//==> API 확인
//		Assert.assertEquals(10001, purchase.getPurchaseProd());
//		Assert.assertEquals("user10", purchase.getBuyer());
//		Assert.assertEquals("1",purchase.getPaymentOption());
//		Assert.assertEquals(null, purchase.getReceiverName());
//		Assert.assertEquals(null, purchase.getDivyAddr());
//		Assert.assertEquals("빠배", purchase.getDivyRequest());
//		Assert.assertEquals(null, purchase.getDivyDate());
		System.out.println(purchase);
		Assert.assertNotNull(purchaseService.getPurchase(10028));
		Assert.assertNotNull(purchaseService.getPurchase(10027));
	}
	
//	@Test
	 public void testUpdatePurchase() throws Exception{
		 
		Purchase purchase = purchaseService.getPurchase(10028);
		Assert.assertNotNull(purchase);
		
//		Assert.assertEquals("testProductName", product.getProdName());
//		Assert.assertEquals("testProductDetail", product.getProdDetail());
//		Assert.assertEquals(123,product.getPrice());
//		Assert.assertEquals(null, product.getManuDate());
//		Assert.assertEquals(null, product.getFileName());
		
		System.out.println(purchase);
		purchase.setPaymentOption("2");
		purchase.setReceiverName("쿠쿠");
		purchase.setReceiverPhone("010");
		purchase.setDivyAddr("우리집");
		purchase.setDivyRequest("천촌히");
		purchase.setDivyDate(null);
//		
		
		
		purchaseService.updatePurcahse(purchase);
		purchase = purchaseService.getPurchase(10028);
		
		Assert.assertNotNull(purchase);
		
		//==> console 확인
		System.out.println(purchase);
			
		//==> API 확인
//		Assert.assertEquals("testProductName", product.getProdName());
//		Assert.assertEquals("testProductDetail", product.getProdDetail());
//		Assert.assertEquals(123,product.getPrice());
//		Assert.assertEquals(null, product.getManuDate());
//		Assert.assertEquals(null, product.getFileName());
	 }
	 
	 //==>  주석을 풀고 실행하면....
//	 @Test
	 public void testGetPurchaseListAll() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	
	 	Map<String, Object> map = new HashMap<String, Object>();
	 	map.put("buyer", "user10");
	 	map.put("search", search);
	 	
	 	List<Purchase> list = (List<Purchase>) purchaseService.getPurchaseList(map);
	 	/*list = (List<Purchase>)map.get("list");*/
	 		 
	 	//Assert.assertEquals(3, list.size());
	 	
	 	//==> console 확인
	 	//System.out.println(list);
	 	
	    int totalCount = ((Integer)map.get("totalCount"));
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	
	 	map.put("buyer", "user10");
	 	map.put("search", search);
	 	
	 	list = (List<Purchase>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
//	 @Test
	 public void testUpdateTranCode() throws Exception{
		 
		 Purchase purchase = purchaseService.getPurchase(10028);
		 Assert.assertNotNull(purchase);
			
		 purchase.setTranCode("2");
			
		 purchaseService.updateTranCode(purchase);
//			
		 purchase = purchaseService.getPurchase(10028);
			
		 Assert.assertNotNull(purchase);
			
		 System.out.println(purchase);
			
			

				
	 }
	 
	
}