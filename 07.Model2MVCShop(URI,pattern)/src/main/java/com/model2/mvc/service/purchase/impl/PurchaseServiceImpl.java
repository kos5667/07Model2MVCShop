package com.model2.mvc.service.purchase.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductDao;
import com.model2.mvc.service.purchase.PurchaseDao;
import com.model2.mvc.service.purchase.PurchaseService;

@Service("purchaseServiceImpl")
public class PurchaseServiceImpl implements PurchaseService {
	
	//Field
	@Autowired
	@Qualifier("purchaseDaoImpl")
	private PurchaseDao purchaseDao;
	public void setPurchase(PurchaseDao purchaseDao) {
		this.purchaseDao = purchaseDao;
	}
	
	//Constructor
	public PurchaseServiceImpl() {
		System.out.println(this.getClass());
	}
	@Override
	public void addPurchase(Purchase purchase) throws Exception {
		purchaseDao.addPurchase(purchase);
	}
	
	@Override
	public Purchase getPurchase(int tranNo) throws Exception {
		return purchaseDao.getPurchase(tranNo);
	}
	@Override
	public Purchase getPurchase2(int ProdNo) throws Exception {
		
		return null;
	}
	@Override
	public Map<String,Object> getPurchaseList(Map map) throws Exception {
		System.out.println(":::PurchaseServiceImpl:::getPurchaseList");

		Search search = new Search();
		search = (Search)map.get("search");
		
		List<Purchase> list = purchaseDao.getPurchaseList(map);
		int totalCount = purchaseDao.getTotalCount(search);
		
		map.put("list", list);
		map.put("totalCount", totalCount);
		return map;
	}
/*	
	@Override
	public Map<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {
		// TODO Auto-generated method stub
		return purchaseDao.getPurchaseList(search, buyerId);
	}
*/	@Override
	public HashMap<String, Object> getSaleList(Search search) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void updatePurcahse(Purchase purchase) throws Exception {
		purchaseDao.updatePurchase(purchase);
		
	}
	@Override
	public void updateTranCode(Purchase purchase) throws Exception {
		System.out.println(":::PurchaseServiceImpl:::updateTranCode");
		purchaseDao.updateTranCodePurchase(purchase);
	}
	
	
}
