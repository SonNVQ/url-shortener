<%-- 
    Document   : expirated
    Created on : Jun 26, 2023, 2:59:43 AM
    Author     : nguyenson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Link expired</title>
        <%@include file="../templates/mdb-link.html" %>
    </head>
    <body class="d-flex flex-column min-vh-100">
        <%@include file="../templates/navbar.jsp" %>

        <div class="container mt-auto" >
            <div class="mx-auto text-center">
                <div class="fs-5" style="min-width: 400px;">
                    <p class="text-danger fw-bolder" style="font-size: 7rem">OOPS!!!</p>
                    <p class="text-warning fw-bold" style="font-size: 3rem">
                        Link expired at 
                        <fmt:formatDate value="${expiration_time}" pattern="dd-MM-YYYY HH:mm:ss" />
                    </p>
                    <%--<fmt:formatDate value="${expiration_time}" />--%>
                    <%--<fmt:parseDate value="${expiration_time}" pattern="dd-MM-yyyy HH:mm:ss" var="exp_time"/>--%>
                    <%--<fmt:formatDate value="${expiration_time}" pattern="dd/MM/yyyy HH:mm" />--%>
                </div>
            </div>
        </div>

        <%@include file="../templates/footer.html" %>
        <%@include file="../templates/mdb-script.html" %>
    </body>
</html>
