<%--
  Created by IntelliJ IDEA.
  User: omyag
  Date: 23.01.2018
  Time: 20:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Client Information</title>
</head>
<body>
	<center>
		<h1>Client Management</h1>
        <h2>
        	<a href="new">Add New Client</a>
        	&nbsp;&nbsp;&nbsp;
        	<a href="list">List All Clients</a>
        	
        </h2>
	</center>
    <div align="center">
        <table border="1" cellpadding="5">
            <caption><h2>List of Books</h2></caption>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Last name</th>
                <th>Birth</th>
                <th>Password</th>
                <th>Client Account</th>
                <th>Action</th>
            </tr>
            <c:forEach var="client" items="${listClient}">
                <tr>
                    <td><c:out value="${client.id}" /></td>
                    <td><c:out value="${client.name}" /></td>
                    <td><c:out value="${client.lastname}" /></td>
                    <td><c:out value="${client.birth}" /></td>
                    <td><c:out value="${client.password}" /></td>
                    <td><c:out value="${client.clientAccount}" /></td>
                    <td>
                    	<a href="edit?id=<c:out value='${client.id}' />">Edit</a>
                    	&nbsp;&nbsp;&nbsp;&nbsp;
                    	<a href="delete?id=<c:out value='${client.id}' />">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>	
</body>
</html>
