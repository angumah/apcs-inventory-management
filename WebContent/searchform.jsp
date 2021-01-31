<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>UCVTS Library</title>
		
		<style type="text/css">
			<%@ include file="css/styles.css"%>
		</style>
	
	</head>
	<body>
		
		<div>
		<h1>Inventory Management</h1>
		
			<div class="header">
				<a href="${pageContext.request.contextPath}/" class="header-button">VIEW ALL</a>
				<a href="${pageContext.request.contextPath}/add" class="header-button">Add A Phone</a>
			</div>		
		</div>
		
		
		<div>
			<h2>Search Results</h2>
			<table border="1">
			<caption>All Phones in Collection</caption>
			<tr>
				<td>Name</td>
				<td>Manufacturer</td>
				<td>Units</td>
				<td>Supply</td>
				<td>Cost</td>
				<td>Description</td>
			</tr>
			<c:forEach var="phone" items="${phones}">
				<tr>
					<td><c:out value="${phone.name}" /></td>
					<td><c:out value="${phone.manufacturer}" /></td>
					<td><c:out value="${phone.units}" /></td>
					<td><c:out value="${phone.supply}" /></td>
					<td><c:out value="${phone.cost}" /></td>
					<td><c:out value="${phone.description}" /></td>
					<td>
						<a href="${pageContext.request.contextPath}/update?action=purchase&id=${phone.id}" class="button">Purchase</a>
						<a href="${pageContext.request.contextPath}/update?action=return&id=${phone.id}" class="button">RETURN</a>
						<a href="${pageContext.request.contextPath}/edit?id=${phone.id}" class="button">EDIT</a>
					</td>
				</tr>
			</c:forEach>
		</table>
		</div>
	</body>
</html>