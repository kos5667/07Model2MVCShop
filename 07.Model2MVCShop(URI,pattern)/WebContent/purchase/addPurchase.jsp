<%@ page language="java" contentType="text/html; charset=euc-kr"
    pageEncoding="euc-kr"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <%@page import="com.model2.mvc.service.domain.Purchase"%>
<%
Purchase purchase = (Purchase)request.getAttribute("purchaseVO");
System.out.println("addPurchase.jsp ���Դ�.");
%>
 --%>

<html>
<head>
<title>Insert title here</title>
</head>

<body>

<form name="updatePurchase" action="/purchase/updatePurchaseView?tranNo=0" method="post">

������ ���� ���Ű� �Ǿ����ϴ�.

<table border=1>
	<tr>
		<td>��ǰ��ȣ</td>
		<td>${ purchase.purchaseProd.prodNo }</td>
	</tr>
	<tr>
		<td>�����ھ��̵�</td>
		<td>${purchase.buyer.userId }</td>
	</tr>
	<tr>
		<td>���Ź��</td>
		<td>
		<c:if test="${purchase.paymentOption  eq '1'}">
			���ݱ���
		</c:if>
		<c:if test="${purchase.paymentOption  eq '2'}">
			�ſ�ī��
		</c:if>
		</td>
	</tr>
	<tr>
		<td>�������̸�</td>
		<td>${purchase.receiverName}</td>
	</tr>
	<tr>
		<td>�����ڿ���ó</td>
		<td>${purchase.receiverPhone}</td>
	</tr>
	<tr>
		<td>�������ּ�</td>
		<td>${purchase.divyAddr}</td>
	</tr>
		<tr>
		<td>���ſ�û����</td>
		<td>${purchase.divyRequest}</td>
	</tr>
	<tr>
		<td>����������</td>
		<td>${purchase.divyDate}</td>
	</tr>
</table>
</form>

</body>
</html>