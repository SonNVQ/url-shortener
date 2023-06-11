<%-- 
    Document   : index
    Created on : Jun 11, 2023, 11:53:14 AM
    Author     : nguyenson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <h2>Hello ${sessionScope.user.firstName} ${sessionScope.user.lastName}</h2>
        <h2>Role: </h2>
        <ul>
            <c:forEach var="role" items="${sessionScope.roles}">
                <li>${role}</li>
            </c:forEach>
        </ul>

    </body>
</html>
