package com.model2.mvc.web.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;


//==> ȸ������ Controller
@Controller
@RequestMapping("/product/*")
public class ProductController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method ���� ����
		
	public ProductController(){
		System.out.println(this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml ���� �Ұ�
	//==> �Ʒ��� �ΰ��� �ּ��� Ǯ�� �ǹ̸� Ȯ�� �Ұ�
//	@Value("#{commonProperties['pageUnit']}")
	@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	
//	@RequestMapping("/addProduct.do")
	@RequestMapping(value="addProduct",method=RequestMethod.POST)
	public String addProduct( @ModelAttribute("product") Product product ) throws Exception {

		System.out.println("/product/addProduct : POST");
		//Business Logic
		productService.addProduct(product);

		return "forward:/product/addProduct.jsp";
	}
	
//	@RequestMapping("/getProduct.do")
	@RequestMapping(value="getProduct", method=RequestMethod.GET)
	public String getProduct( @RequestParam("prodNo") int prodNo , Model model ) throws Exception {
		
		System.out.println("/product/getProduct.do");
		
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model �� View ����
		model.addAttribute("product", product);
		
		return "forward:/product/getProduct.jsp";
	}
	
//	@RequestMapping("/updateProductView.do")
	@RequestMapping(value="updateProductView",method=RequestMethod.GET)
	public String updateProductView( @RequestParam("prodNo") int prodNo , Model model ) throws Exception{

		System.out.println("/product/updateProductView : GET");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model �� View ����
		model.addAttribute("product", product);
		
		return "forward:/product/updateProductView.jsp"; 
	}
	
//	@RequestMapping("/updateProduct.do")
	@RequestMapping(value="updateProduct",method=RequestMethod.POST)
	public String updateProduct( @ModelAttribute("product") Product product) throws Exception{
		System.out.println("/product/updateProduct.do");
		//Business Logic
		System.out.println(product);
		productService.updateProduct(product);
//		model.addAttribute("product", product);
//		model.addAttribute("menu", menu);
		
		return "forward:/product/getProduct.jsp";
	}

	
//	@RequestMapping("/listProduct.do")
	@RequestMapping(value="listProduct")
	public String listProduct( @ModelAttribute("search") Search search , Model model , HttpServletRequest request) throws Exception{
		
		System.out.println("/product/listProduct");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic ����
		Map<String , Object> map = productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model �� View ����
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/product/listProduct.jsp";
	}
}