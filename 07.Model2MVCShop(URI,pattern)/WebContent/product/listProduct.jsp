<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%-- <%
<%@ page import="com.model2.mvc.common.util.CommonUtil"%>
<%@ page import="com.model2.mvc.service.domain.Product"%>
<%@ page import="java.util.*"%>
<%@ page import="com.model2.mvc.common.*"%>

List<Product> list = (List<Product>) request.getAttribute("list");
Page resultPage = (Page) request.getAttribute("resultPage");

Search search = (Search) request.getAttribute("search");
//==> null 을 ""(nullString)으로 변경
//null 을 ""(nullString)으로 변경 ======>null방지
String searchCondition = CommonUtil.null2str(search.getSearchCondition());
String searchKeyword = CommonUtil.null2str(search.getSearchKeyword());

%> --%>
<html>
<head>
<title>상품 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
function fncGetProductList(currentPage) {
	<%-- document.getElementById("menu").value = <%="\"" + request.getParameter("menu") + "\""%>; --%>
	document.getElementById("menu").value = "${param.menu}";
	document.getElementById("currentPage").value = currentPage;
	document.detailForm.submit();
}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

	<div style="width: 98%; margin-left: 10px;">
	<form name="detailForm" action="/product/listProduct?" method="post">

		<table width="100%" height="37" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="15" height="37">
				<img src="/images/ct_ttl_img01.gif" width="15" height="37" />
				</td>
				<td background="/images/ct_ttl_img02.gif" width="100%"
					style="padding-left: 10px;">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
						<td width="93%" class="ct_ttl01">
							<c:if test="${param.menu=='manage' }">
								상품 관리
							</c:if>
							<c:if test="${param.menu=='search' }">
								상품 목록조회 
							</c:if> 
						</td>
						</tr>
					</table>
				</td>
				<td width="12" height="37">
				<img src="/images/ct_ttl_img03.gif" width="12" height="37" />
				</td>
			</tr>
		</table>

		<!-- 여기는 우측 상단에 상품번호 상품명 상품가격을 getSearchCondition 으로 선택할 수 있는 jsp -->
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			style="margin-top: 10px;">
			<tr>
				<td align="right">
				<select name="searchCondition" class="ct_input_g" style="width: 80px">
						<option value="0" ${! empty search.searchCondition && search.searchCondition==0 ? "selected" : ""  }>상품번호</option>
						<option value="1" ${! empty search.searchCondition && search.searchCondition==1 ? "selected" : ""  }>상품명</option>
						<option value="2" ${! empty search.searchCondition && search.searchCondition==2 ? "selected" : ""  }>상품가격</option>
				</select> <%-- value 값으로 getSearchKeyword() 찾아주기 --%> 
				<input type="text" name="searchKeyword" value="${! empty search.searchKeyword ? search.searchKeyword : "" }" class="ct_input_g" style="width: 200px; height: 19px" />
				</td>
				<!-- 여기는 상품 keyword를 넣고 검색 링크를 달아놓는 jsp -->
				<td align="right" width="70">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23"></td>
						<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top: 3px;">
							<a href="javascript:fncGetProductList('1');">검색</a></td>
						<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23"></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>

		<!--  여기는 현재 페이지의 상태를 보여주는 jsp -->
		<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
			<tr>
				<td colspan="11">
					전체  ${resultPage.totalCount } 건수, 현재 ${resultPage.currentPage}  페이지
				</td>
			</tr>
			<tr>
				<td class="ct_list_b" width="100">No</td>
				<td class="ct_line02"></td>
				<td class="ct_list_b" width="150">상품명</td>
				<td class="ct_line02"></td>
				<td class="ct_list_b" width="150">가격</td>
				<td class="ct_line02"></td>
				<td class="ct_list_b">등록일</td>
				<td class="ct_line02"></td>
				<td class="ct_list_b">현재상태</td>
			</tr>
			<tr>
				<td colspan="11" bgcolor="808285" height="1"></td>
			</tr>
			
			<c:set var="i" value="0"/>
			<c:forEach var="product" items="${list}" >
				<c:set var="i" value="${ i+1 }"/>
				<tr class="ct_list_pop">
					<td align="center">${ i }</td>
					<td></td>
					<td align="left">
					<c:if test="${param.menu=='search' }">
						<a href="/product/getProduct?menu=search&prodNo=${ product.prodNo}">${product.prodName}</a>
					</c:if>
					<c:if test="${param.menu=='manage' }">
						<a href="/product/updateProductView?menu=manage&prodNo=${ product.prodNo}">${product.prodName}</a>
					</c:if>
					</td>
					
					<td></td>
					<td align="left">${product.price}</td>

					<td></td>
					<td align="left">${product.manuDate}</td>
					
					<td></td>
					<td align="left">
					<c:if test="${user.role eq 'user'}">
						<c:if test="${ empty product.proTranCode }">판매중</c:if>
						${! empty product.proTranCode&&product.proTranCode eq '0'? '판매완료':''}
						${! empty product.proTranCode&&product.proTranCode eq '1'? '판매완료':''}
						${! empty product.proTranCode&&product.proTranCode eq '2'? '재고없음':''}
					</c:if>
					
					
					<c:if test="${user.role eq 'admin' }">
						<c:if test="${ empty product.proTranCode }">판매중</c:if>
						<c:if test="${! empty product.proTranCode&&product.proTranCode eq '0'}">
							판매완료  <a href="/purchase/updateTranCode?tranCode=1&prodNo=${product.prodNo}"> 배송하기</a>
						</c:if>
						 ${! empty product.proTranCode&&product.proTranCode eq '1'? '배송중':''}
						 ${! empty product.proTranCode&&product.proTranCode eq '2'? '재고없음':''}
					</c:if>
						 
						 
					</td>	
				 
				</tr>
				<tr>
					<td colspan="11" bgcolor="D6D7D6" height="1"></td>
				</tr>
			</c:forEach>
		</table>
		<!--  여기는 페이지 옮기기 위해 각 페이지별 링크를 for문으로 바꿔줌  -->
		<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
			<tr>
			<td align="center"><input type="hidden" id="currentPage" name="currentPage" value="" />
				<input type="hidden" id="menu" name="menu" value="${param.menu}" />
				
				<jsp:include page="../common/pageNavigator.jsp" >
					<jsp:param name="name" value="Product"/>
				</jsp:include>	
			</td>
			</tr>
		</table>
		<!--  페이지 Navigator 끝 -->
		</form>
	</div>
</body>
</html>
