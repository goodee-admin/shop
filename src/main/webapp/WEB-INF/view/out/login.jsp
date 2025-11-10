<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>shop</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<h1>login</h1>
	<form method="post" action="${pageContext.request.contextPath}/out/login">
		<div>
			<div>
				<table>
					<tr>
						<td>id</td>
						<td><input value="customer1" type="text" name="id" id="id"></td>
					</tr>
					<tr>
						<td>pw</td>
						<td><input value="1234" type="paswword" name="pw" id="pw"></td></td>
					</tr>
				</table>
				<button type="submit">로그인</button>
			</div>
			<div>
				<input type="radio" name="customerOrEmpSel" class="customerOrEmpSel" value="customer" checked>customer
				<input type="radio" name="customerOrEmpSel" class="customerOrEmpSel" value="emp">emp
			</div>
		</div>
	</form>
	<a href="${pageContext.request.contextPath}/out/addCustomer">회원가입</a>
</body>
</html>