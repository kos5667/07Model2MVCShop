package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseDao;


@Repository("purchaseDaoImpl")
public class PurchaseDaoImpl implements PurchaseDao {
	///Field
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	//Constructor
	public PurchaseDaoImpl() {
		System.out.println(this.getClass());
	}
	
	public void addPurchase(Purchase purchase) throws Exception{
		System.out.println("Mapper�� ���� "+purchase);
		this.sqlSession.insert("PurchaseMapper.addPurchase",purchase);
	}
	
	public Purchase getPurchase(int tranNo) throws Exception{ 
		return sqlSession.selectOne("PurchaseMapper.getPurchase",tranNo);
	}
	
	public List<Purchase> getPurchaseList(Map map) throws Exception {
		System.out.println("::::PurchaseDaoImpl::::getPurchaseList::::");
		
		//map�޶�� �� list�ָ� ����� casting�ϴ��� list�� �Ѱ���
		
		return sqlSession.selectList("PurchaseMapper.getPurchaseList", map);
	}
	public HashMap<String,Object> getSaleList(Search search) throws Exception{
		return null;
	}
	
	public void updatePurchase(Purchase purchase) throws Exception{
		sqlSession.update("PurchaseMapper.updatePurchase",purchase);
	}
	public void updateTranCodePurchase(Purchase purchase) throws Exception{
		System.out.println("::::PurchaseDaoImpl::::updateTranCodePurchase::::");
		sqlSession.update("PurchaseMapper.updateTranCodePurchase", purchase);
	}

	@Override
	public int getTotalCount(Search search) throws Exception {
		return sqlSession.selectOne("PurchaseMapper.getTotalCount", search);
		
	}

}