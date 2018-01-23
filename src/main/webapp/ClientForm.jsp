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
        <a href="list">List All Client</a>

    </h2>
</center>
<div align="center">
    <c:if test="${client != null}">
    <form action="update" method="post">
        </c:if>
        <c:if test="${client == null}">
        <form action="insert" method="post">
            </c:if>
            <table border="1" cellpadding="5">
                <caption>
                    <h2>
                        <c:if test="${client != null}">
                            Edit Client
                        </c:if>
                        <c:if test="${client == null}">
                            Add New Client
                        </c:if>
                    </h2>
                </caption>
                <c:if test="${client != null}">
                    <input type="hidden" name="id" value="<c:out value='${client.id}' />" />
                </c:if>
                <tr>
                    <th>Name: </th>
                    <td><input type="text" name="name" size="45" value="<c:out value='${client.name}' />"/></td>
                </tr>

                <tr>
                    <th>Last Nane: </th>
                    <td><input type="text" name="lastname" size="45" value="<c:out value='${client.lastname}' />"/></td>
                </tr>
                <tr>
                    <th>Birth: </th>
                    <td><input type="text" name="birth" size="5" value="<c:out value='${client.birth}' />"/></td>
                </tr>
                <tr>
                    <th>Password: </th>
                    <td><input type="text" name="password" size="5" value="<c:out value='${client.password}' />"/></td>
                </tr>
                <tr>
                    <th>Client Account: </th>
                    <td><input type="text" name="clientAccount" size="5" value="<c:out value='${client.clientAccount}' />"/></td>
                </tr>
                <tr><td colspan="2" align="center"><input type="submit" value="Save" />
                    </td>
                </tr>
            </table>
        </form>
</div>
</body>
</html>
