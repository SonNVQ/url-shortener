<%-- 
    Document   : redirect.jsp
    Created on : Jun 25, 2023, 11:40:41 PM
    Author     : nguyenson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Redirect...</title>
        <%@include file="../templates/mdb-link.jsp" %>
    </head>
    <body class="d-flex flex-column min-vh-100">
        <%@include file="../templates/navbar.jsp" %>

        <div class="container mt-auto" >
            <div class="d-lg-flex flex-column justify-content-center align-items-center">
                <div class="fs-5" style="min-width: 400px;">
                    <p class="text-success fw-bold">Link: <a href="${uid}">oi.io.vn/${uid}</a></p>
                    <p>You will be redirected in <time><strong id="seconds">${redirect_time}</strong> seconds</time>.</p>
                    <div>
                        <c:if test="${not empty redirect_message}">
                            <p><b>Message from link creator:</b></p>
                            <textarea class="form-control" style="font-size: 1.1rem;" rows="5" readonly>${redirect_message}</textarea>
                        </c:if>
                    </div>
                    <a href="<c:out value="${link}"/>" class="btn btn-primary btn-block mt-4" style="height: 3rem; font-size: 1.3rem">
                        <i class="fa-solid fa-location-arrow"></i>
                        Go now
                    </a>
                </div>
            </div>
        </div>

        <script>
            history.replaceState(null, '', '${uid}');

            var el = document.getElementById('seconds'),
                    total = el.innerHTML,
                    timeinterval = setInterval(function () {
                        total = --total;
                        el.textContent = total;
                        if (total <= 0) {
                            clearInterval(timeinterval);
                            window.location.replace("<c:out value="${link}" />");
                        }
                    }, 1000);

        </script>
        <%@include file="../templates/footer.html" %>
        <%@include file="../templates/mdb-script.jsp" %>
    </body>
</html>
