<%-- 
    Document   : profile
    Created on : Jul 8, 2023, 5:30:31 PM
    Author     : nguyenson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Profile</title>
        <%@include file="../templates/mdb-link.jsp" %>
    </head>
    <body class="d-flex flex-column min-vh-100">
        <%@include file="../templates/navbar.jsp" %>

        <div class="container" >
            <div class="row">
                <div class="col-4 mx-auto">
                    <h2 class="mt-5 text-center">Change password</h2>
                    <form action="${pageContext.request.contextPath}/changePass" method="POST">
                        <div class="form-outline my-3">
                            <input
                                type="password"
                                name="oldPassword"
                                id="typePasswordX-2"
                                class="form-control form-control-lg bg-white"
                                />
                            <label class="form-label" for="typePasswordX-2">Old password</label>
                        </div>

                        <c:if test="${OLD_PASSWORD_IS_WRONG}">
                            <p class="text-danger text-start mt-1">Old password is wrong!</p>
                        </c:if>

                        <div class="form-outline my-3">
                            <input
                                type="password"
                                name="newPassword"
                                id="typePasswordX-2"
                                class="form-control form-control-lg bg-white"
                                />
                            <label class="form-label" for="typePasswordX-2">New password</label>
                        </div>
                        <div class="form-outline my-3">
                            <input
                                type="password"
                                name="newPasswordRetype"
                                id="typePasswordX-2"
                                class="form-control form-control-lg bg-white"
                                />
                            <label class="form-label" for="typePasswordX-2">Retype new password</label>
                        </div>
                        <c:if test="${NEW_PASSWORD_DOES_NOT_MATCH}">
                            <p class="text-danger text-start mt-1">New password does not match!</p>
                        </c:if>
                        <c:if test="${INPUT_IS_INVALID}">
                            <p class="text-danger text-start mt-1">Input fields is invalid!</p>
                        </c:if>

                        <div class="mt-4">
                            <button
                                class="btn btn-warning btn-lg btn-block"
                                type="submit"
                                >
                                Change
                            </button>
                        </div>
                    </form>
                    <c:if test="${status eq 'failed'}">
                        <p class="text-danger mt-3">Changed password failed! Please try again!</p>
                    </c:if>
                    <c:if test="${status eq 'success'}">
                        <p class="text-success mt-3">Changed password successfully!</p>
                    </c:if>
                </div>
            </div>
        </div>

        <%@include file="../templates/footer.html" %>
        <%@include file="../templates/mdb-script.jsp" %>
    </body>
</html>