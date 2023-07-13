<%-- 
    Document   : user-pass
    Created on : Jul 13, 2023, 6:34:44 AM
    Author     : nguyenson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Set user password</title>
        <%@include file="../templates/mdb-link.jsp" %>
    </head>
    <body class="d-flex flex-column min-vh-100">
        <%@include file="../templates/navbar.jsp" %>

        <div class="container" >
            <div class="row">
                <div class="col-4 mx-auto">
                    <h2 class="mt-5 text-center">Set new password</h2>
                    <form action="#" method="POST">
                        <input type="hidden" name="id" value="${id}">
                        <div class="form-outline my-3">
                            <input
                                type="text"
                                id="typePasswordX-2"
                                class="form-control form-control-lg"
                                name="password"
                                value="${password}"
                                />
                            <label class="form-label" for="typePasswordX-2">New password</label>
                        </div>

                        <div class="mt-4">
                            <button
                                class="btn btn-warning btn-lg btn-block"
                                type="submit"
                                >
                                Set
                            </button>
                        </div>
                    </form>
                    <c:if test="${status eq 'updated-failed'}">
                        <p class="text-danger mt-3">Updated failed! Please try again!</p>
                    </c:if>
                    <c:if test="${status eq 'updated-success'}">
                        <p class="text-success mt-3">Updated successfully!</p>
                    </c:if>
                </div>
            </div>
        </div>

        <%@include file="../templates/footer.html" %>
        <%@include file="../templates/mdb-script.jsp" %>
    </body>
</html>
