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
		<h1>Phone Store</h1>
		
			<div class="header">
				<a href="${pageContext.request.contextPath}/" class="header-button">VIEW ALL</a>
				<a href="${pageContext.request.contextPath}/add" class="header-button">Add A Phone</a>
			</div>		
		</div>
		
		
		<div>
			<c:if test="${phone != null }">
				<h2>Edit Phone</h2>
				<form action="update" method="post">
					<input type="hidden" name="id" value="<c:out value="${phone.id}" />" />
					
					<label>Name<input type="text" name="name" value= "<c:out value="${phone.name}" />" /></label>
					<label>Manufacturer<input type="text" name="manufacturer" value= "<c:out value="${phone.manufacturer}" />" /></label>
					<label>
						# of Units
						<select name="units">
							<c:forEach begin="1" end="100" varStatus="loop">
								<option value="${loop.index}"
									<c:if test="${phone.units == loop.index}">selected</c:if>
								>
									${loop.index}
								</option>
							</c:forEach>
						</select>
					</label>
					<label>
						Supply
						<select name="supply">
							<c:forEach begin="1" end="${phone.units}" varStatus="loop">
								<option value="${loop.index}"
									<c:if test="${phone.supply == loop.index}">selected</c:if>
								>
									${loop.index}
								</option>
							</c:forEach>
						</select>
					</label>
					
					<label>Cost<input type="text" name="cost" value= "<c:out value="${phone.cost}" />" /></label>
					<label>Description<input type="text" name="description" value= "<c:out value="${phone.description}" />" /></label>
					<div class="form-actions">
						<input type="submit" value="SAVE" name="submit" />
						<input type="submit" value="DELETE" name="submit" />
					</div>
				</form>
			</c:if>
			<c:if test="${phone == null}">
				<h2>Add Phone</h2>
				<form action="insert" method="post">
				<input type="hidden" name="id" />
				
				<label>Name<input type="text" name="name" /></label>
				<label>Manufacturer<input type="text" name="manufacturer" /></label>
				<label>
				# of Units
					<select name="units">
						<c:forEach begin="1" end="100" varStatus="loop">
							<option value="${loop.index}">${loop.index}</option>
						</c:forEach>
					</select> 
				</label>
				<label>Cost<input type="text" name="cost" /></label>
				<label>Description<input type="text" name="description" /></label>
				<input type="submit" value="ADD" name="submit" />
				</form>
			</c:if>
			</div>
	</body>
</html>