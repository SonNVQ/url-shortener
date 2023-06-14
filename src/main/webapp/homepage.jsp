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
        <p>${role}</p>
        <h1><a href="auth/login">Login</a>|<a href="auth/logout">Logout</a></h1>
        <h2>Hello ${sessionScope.user.firstName} ${sessionScope.user.lastName}</h2>
        <h2>Role: </h2>
        <ul>
            <c:forEach var="role" items="${sessionScope.roles}">
                <li>${role}</li>
                </c:forEach>
        </ul>
        <c:if test="${role eq 'guest'}">            
            <div id="g_id_onload"
                 data-client_id="205125337007-vp8gcc90umgim1krgh90e6lcafj12obf.apps.googleusercontent.com"
                 data-context="signin"
                 data-ux_mode="popup"
                 data-login_uri="http://localhost:8088/url/auth/login-google"
                 data-itp_support="true">
            </div>
            <script src="https://accounts.google.com/gsi/client" async defer></script>
        </c:if>
    </body>
</html>
