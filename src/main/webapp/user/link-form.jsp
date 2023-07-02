<%-- 
    Document   : link-form
    Created on : Jun 27, 2023, 1:02:50 AM
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
        <%@include file="../templates/navbar.jsp" %>

        <div class="container mt-3" >
            <div class="row">
                <div class="col-lg-5 mx-auto">
                    <c:if test="${status eq 'updated-success'}">
                        <p class="text-success fw-bold fs-5 mb-1">Status: Updated successfully!</p>
                    </c:if>
                    <c:if test="${status eq 'updated-fail'}">
                        <p class="text-danger fw-bold fs-5 mb-1">Status: Update failed!</p>
                    </c:if>
                        <p class="text-success fw-bold fs-5 mb-1">Editing: <a href="${pageContext.request.contextPath}/${url.uid}" target="_blank">oi.io.vn/${url.uid}</a></p>
                    <p class="text-primary fw-bold fs-6">Link: <a href="${url.link}">${url.link}</a></p>
                    <form action="#" method="post">
                        <div class="mb-2">
                            <span><b>Title:</b></span>
                            <div class="form-outline mt-1">
                                <textarea class="form-control" name="title" id="textAreaExample" rows="2" maxlength="250">${url.title}</textarea>
                            </div>
                        </div>
                        <div class="mb-2">
                            <span><b>Passcode:</b></span>
                            <div class="form-outline mt-1">
                                <input type="text" name="passcode" id="passcode" value="${url.passcode}" class="form-control" />
                            </div>
                        </div>
                        <div class="mb-2">
                            <span><b>Redirect time:</b></span>
                            <div class="form-outline mt-1">
                                <input type="number" name="redirect-time" value="${url.redirectTime}" min="5" max="60" id="redirect-time" class="form-control" />
                            </div>
                        </div>
                        <div class="mb-2">
                            <span><b>Redirect message:</b></span>
                            <div class="form-outline mt-1">
                                <textarea class="form-control" name="redirect-message" id="textAreaExample" rows="2" maxlength="250">${url.redirectMessage}</textarea>
                            </div>
                        </div>
                        <div class="mb-2">
                            <span><b>Expiration time:</b></span>
                            <div class="form-outline mt-1">
                                <input type="datetime-local" name="expiration-time" value="${url.expirationTime}" id="datetimepicker" class="form-control" />
                            </div>
                        </div>
                        <div class="my-4">
                            <button type="submit" class="btn btn-primary btn-block" style="height: 2.8rem; font-size: 1rem">
                                <i class="fa-solid fa-pen-to-square"></i>
                                Edit
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <%@include file="../templates/footer.html" %>
        <%@include file="../templates/mdb-script.html" %>
    </body>
</html>
