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
                    <h2 class="mt-5 text-center">
                        Profile
                    </h2>
                    <form action="${pageContext.request.contextPath}/profile" method="POST">
                        <div class="row">
                            <div class="col-lg-6">
                                <div class="form-outline mt-3">
                                    <input
                                        type="text"
                                        name="firstname"
                                        id="typePasswordX-2"
                                        class="form-control form-control-lg"
                                        value="${firstname}"
                                        />
                                    <label class="form-label" for="typePasswordX-2"
                                           >First name</label
                                    >
                                </div>
                            </div>
                            <div class="col-lg-6">
                                <div class="form-outline mt-3">
                                    <input
                                        type="text"
                                        name="lastname"
                                        id="typePasswordX-2"
                                        class="form-control form-control-lg"
                                        value="${lastname}"
                                        />
                                    <label class="form-label" for="typePasswordX-2"
                                           >Last name</label
                                    >
                                </div>
                            </div>
                            <c:if test="${LASTNAME_IS_EMPTY eq true}">
                                <p class="text-warning text-start mt-1">Last name must not be empty!</p>
                            </c:if>
                        </div>

                        <div class="form-outline my-3">
                            <input
                                type="email"
                                disabled
                                id="typePasswordX-2"
                                class="form-control form-control-lg bg-white"
                                value="${email}"
                                />
                            <label class="form-label" for="typePasswordX-2">Email</label>
                        </div>

                        <div class="mt-4">
                            <button
                                class="btn btn-warning btn-lg btn-block"
                                type="submit"
                                >
                                Update
                            </button>
                        </div>
                    </form>
                    <c:if test="${status eq 'failed'}">
                        <p class="text-danger mt-3">Updated failed! Please try again!</p>
                    </c:if>
                    <c:if test="${status eq 'success'}">
                        <p class="text-success mt-3">Updated successfully!</p>
                    </c:if>
                    <c:if test="${!roleService.isGoogleLoginOnlyUser(pageContext.request)}">
                        <div class="mt-4">
                            <p>
                                <a href="${pageContext.request.contextPath}/changePass">&#149; Change password</a>
                            </p>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>

        <%@include file="../templates/footer.html" %>
        <%@include file="../templates/mdb-script.jsp" %>
    </body>
</html>