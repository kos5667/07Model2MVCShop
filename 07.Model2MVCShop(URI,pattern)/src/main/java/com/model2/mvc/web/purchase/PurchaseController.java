package com.model2.mvc.web.purchase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;


//==> ȸ������ Controller
@Controller
@RequestMapping("/purchase/*")
public class PurchaseController {
	
	///Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method ���� ����
	
	public PurchaseController(){
		System.out.println(this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml ���� �Ұ�
	//==> �Ʒ��� �ΰ��� �ּ��� Ǯ�� �ǹ̸� Ȯ�� �Ұ�
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
//	@RequestMapping("/addPurchase.do")
	@RequestMapping(value="addPurchase",method=RequestMethod.POST)
	public ModelAndView addPurchase( @ModelAttribute("purchase") Purchase purchase,
									 HttpServletRequest request)throws Exception {
		System.out.println("\n==> addPurchase() ����......");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/purchase/addPurchase.jsp");
		

		
		Product product = new Product();
		product.setProdNo(Integer.parseInt(request.getParameter("prodNo")));
		User user = new User();
		user.setUserId(request.getParameter("buyerId"));
		
		purchase.setBuyer(user);
		purchase.setPurchaseProd(product);
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyDate(request.getParameter("receiverDate"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		
		//Business Logic
		purchaseService.addPurchase(purchase);
		modelAndView.addObject("purchase", purchase);
		System.out.println("\n==> addPurchase() ��......");
		return modelAndView;
	}
//	@RequestMapping("/addPurchaseView.do")
	@RequestMapping(value="addPurchaseView",method=RequestMethod.GET)
	public ModelAndView addPurchaseView( @RequestParam("prodNo") int prodNo,
										 @ModelAttribute("product")Product product,
										 HttpSession session) throws Exception {
		System.out.println("\n==> addPurchaseView() ����......");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/purchase/addPurchaseView.jsp");
		
		
		User user = (User)session.getAttribute("user");
		
		//Business Logic
	
		product = productService.getProduct(prodNo);

		modelAndView.addObject("user", user);
		modelAndView.addObject("product",product);
		System.out.println("\n==> addPurchaseView() ��......");
		return modelAndView;
	}
	
//	@RequestMapping("/getPurchase.do")
	@RequestMapping(value="getPurchase",method=RequestMethod.GET)
	public ModelAndView getPurchase( @RequestParam("tranNo") int tranNo) throws Exception {
		System.out.println("\n==> getPurchase() ����......");
		System.out.println("/getPurchase.do");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/purchase/getPurchase.jsp");
		//Business Logic
		Purchase purchase = purchaseService.getPurchase(tranNo);
		// Model �� View ����
		
		modelAndView.addObject("purchase", purchase);
		
		System.out.println("getPurchase() ��......");
		return modelAndView;
	}
	
//	@RequestMapping("/updatePurchaseView.do")
	@RequestMapping(value="updatePurchaseView",method=RequestMethod.GET)
	public ModelAndView updatePurchaseView( @RequestParam("tranNo") int tranNo , Model model ) throws Exception{
		System.out.println("\n==> updatePurchaseView() ����......");
		System.out.println("/updatePurchaseView.do");

		//Business Logic
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/purchase/updatePurchaseView.jsp");
		Purchase purchase = purchaseService.getPurchase(tranNo);
		// Model �� View ����
		modelAndView.addObject("purchase", purchase);
		
		
		System.out.println("updatePurchase() ��......");
		return modelAndView; 
	}
	
//	@RequestMapping("/listPurchase.do")
	@RequestMapping(value="listPurchase")
	public ModelAndView listPurchase( @ModelAttribute("search") Search search ,HttpSession session) throws Exception{
		System.out.println("\n==> ::Controller::listPurchase() ����......");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		// Business logic ����
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/purchase/listPurchase.jsp");
		
		User user = (User)session.getAttribute("user");
//		modelAndView.addObject("buyer", user.getUserId());
//		modelAndView.addObject("search", search);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("buyer", user.getUserId());
		map.put("search", search);
//		System.out.println(":::PurchaseController:::userId = "+user.getUserId());
//		System.out.println(":::PurchaseController:::search = "+search);
		map = purchaseService.getPurchaseList(map);
		//�����Ͻ� ����
	 	 
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		modelAndView.addObject("list",map.get("list"));
		modelAndView.addObject("resultPage",resultPage);
//		System.out.println(map);
//		System.out.println(resultPage);
		System.out.println("\n :::Controller:::listPurchase:::modelAndView = "+modelAndView);
		System.out.println("\n==> ::Controller::listPurchase() ��......");
		return modelAndView;
	}
	@RequestMapping(value="updateTranCode")
	public ModelAndView updateTranCode(@ModelAttribute("purchase") Purchase purchase, 
									   @RequestParam("tranCode") String tranCode,
									   @RequestParam(value="prodNo",defaultValue="0") int prodNo,
									   @RequestParam(value="tranNo",defaultValue="0") int tranNo,
									   HttpSession session)throws Exception{
		System.out.println("\n==> ::Controller:::updqteTranCode() ���� ......");
		ModelAndView modelAndView = new ModelAndView();
		User user = (User)session.getAttribute("user");
		Product product = new Product();
		
		purchase.setTranNo(tranNo);
		product.setProdNo(prodNo);
		purchase.setPurchaseProd(product);
		purchase.setTranCode(tranCode);
		System.out.println("purchase"+purchase);
		purchaseService.updateTranCode(purchase);
		System.out.println(":::Controller:::updateTranCode:::purchase = "+purchase);
		
		if(user.getRole().equals("user")) {
			modelAndView.setViewName("redirect:/purchase/listPurchase");
		}else {
			modelAndView.setViewName("redirect:/product/listProduct");
		}
		System.out.println("\n==> ::Controller:::updqteTranCode() �� ......");
		return modelAndView;
	}
	
}