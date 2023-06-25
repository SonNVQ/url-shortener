<%-- 
    Document   : passcode
    Created on : Jun 25, 2023, 11:46:24 PM
    Author     : nguyenson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Passcode Protection</title>
        <%@include file="../templates/mdb-link.html" %>
    </head>
    <body class="d-flex flex-column min-vh-100">
        <%@include file="../templates/navbar-homepage.jsp" %>

        <div class="container mt-auto" >
            <div class="row fs-5">
                <div class="col-lg-4 mx-auto">
                    <p class="text-success fw-bold">Link: <a href="${uid}">oi.io.vn/${uid}</a></p>
                    <form action="passcode" method="post">
                        <span>You must enter <b>passcode</b> to access this link</span>
                        <div class="form-outline mt-2">
                            <input type="password" name="passcode" id="passcode" class="form-control" style="height: 3rem; font-size: 1.3rem" />
                            <label class="form-label" for="passcode">Passcode</label>
                        </div>
                        <c:if test="${wrong_passcode}">
                            <p class="text-danger fw-bold mt-1">Entered passcode is wrong. Please try again!</p>
                        </c:if>
                        <button type="submit" class="btn btn-primary btn-block mt-3" style="height: 3rem; font-size: 1.3rem">
                            <i class="fa-sharp fa-solid fa-unlock"></i> Unlock
                        </button>
                        <input type="hidden" name="uid" id="uid" value="${uid}">
                    </form>
                </div>
            </div>
        </div>
        <script>
            history.replaceState(null, '', '${uid}');
        </script>
        <%@include file="../templates/footer.html" %>
        <%@include file="../templates/mdb-script.html" %>
    </body>
</html>
