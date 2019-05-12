package com.model2.mvc.web.product;

import java.io.File;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

import jdk.nashorn.internal.ir.RuntimeNode.Request;


//==> 회원관리 Controller
@Controller
@RequestMapping("/product/*")
public class ProductController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method 구현 않음
		
	public ProductController(){
		System.out.println(this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml 참조 할것
	//==> 아래의 두개를 주석을 풀어 의미를 확인 할것
//	@Value("#{commonProperties['pageUnit']}")
	@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	@Value("#{commonProperties['uploadPath']}")
	String uploadPath;
	
//	@RequestMapping("/addProduct.do")
	@RequestMapping(value="addProduct",method=RequestMethod.POST)
	public String addProduct( @ModelAttribute("product") Product product,
							  @RequestParam("file") MultipartFile[] files,
							  HttpServletResponse response
							  /*@RequestParam("temDir")String temDir*/) throws Exception {
		System.out.println("/product/addProduct : POST");
		//FileUpload
//		String temDir = "C:\\Users\\USER\\git\\07Model2MVCShop\\07.Model2MVCShop(URI,pattern)\\WebContent\\images\\uploadFiles";
		String safeFile ="";
		for(MultipartFile file : files) {
			String originFileName = file.getOriginalFilename(); 
			File target = new File(uploadPath, originFileName);
			FileCopyUtils.copy(file.getBytes(),target);
			safeFile += originFileName;
		}
		product.setFileName(safeFile);

		//Business Logic
		productService.addProduct(product);
		return "forward:/product/addProduct.jsp";
	}
	
//	@RequestMapping("/getProduct.do")
	@RequestMapping(value="getProduct", method=RequestMethod.GET)
	public String getProduct( @RequestParam("prodNo") int prodNo , Model model,
							  HttpServletRequest request,HttpServletResponse response/*,@CookieValue(value="history",required=false)String history*/) throws Exception {
		
		System.out.println("/product/getProduct.do");
		
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model 과 View 연결
		model.addAttribute("product", product);
		
		Cookie[] cookies = request.getCookies();
		Cookie c = null;
		String history = null;
		if (cookies!=null && cookies.length > 0) {//쿠키 null체크			
			for (int i=0; i < cookies.length; i++) {
				c = cookies[i];					
				System.out.println("쿠키쿠키 = "+c.getValue());
				if (c.getName().equals("history")) {
					history = c.getValue();
					if(history.indexOf(Integer.toString(prodNo)) == -1) {
						history += ","+prodNo;
						c = new Cookie("history", history);
					}
					break;
				}
			}
			if (history == null) {
//				history += prodNo;
				c = new Cookie("history",Integer.toString(prodNo));
				
			}
		}else {
			c = new Cookie("history", history);
		}
		c.setMaxAge(60*60);
		c.setPath("/history.jsp");
//		System.out.println(c.getPath());
		response.addCookie(c);
//		System.out.println("history = "+c.getValue());
		return "forward:/product/getProduct.jsp";
	}
	
//	@RequestMapping("/updateProductView.do")
	@RequestMapping(value="updateProductView",method=RequestMethod.GET)
	public String updateProductView( @RequestParam("prodNo") int prodNo , Model model ) throws Exception{

		System.out.println("/product/updateProductView : GET");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model 과 View 연결
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
		
		// Business logic 수행
		Map<String , Object> map = productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/product/listProduct.jsp";
	}
}