<%-- 
    Document   : homepage
    Created on : Jun 25, 2023, 5:10:47 PM
    Author     : nguyenson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="roleService" class="Services.Impl.RoleService" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <%@include file="templates/mdb-link.html" %>
        <style>
            .divider:after,
            .divider:before {
                content: "";
                flex: 1;
                height: 1px;
                background: #eee;
            }
            .h-custom {
                height: calc(100% - 73px);
            }
            @media (max-width: 450px) {
                .h-custom {
                    height: 100%;
                }
            }
        </style>
    </head>
    <body class="d-flex flex-column min-vh-100">
        <%@include file="templates/navbar.jsp" %>
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-lg-7">
                    <div class="fw-bold text-primary fs-3 text-center mt-5">
                        No <span class="text-danger">fees</span>, no <span class="text-danger">ads</span>, just <span class="text-success">shorten</span> your link!
                    </div>
                    <form action="#" method="post" class="mt-3">
                        <div class="row mb-4 g-2" >
                            <div class="col-lg-10" >
                                <div class="form-outline mb-2">
                                    <input type="text" name="link" id="link" class="form-control" style="height: 3rem; font-size: 1.2rem" />
                                    <label class="form-label" for="link" style="font-size: 1.2rem" autocomplete="off">Put your link here</label>
                                </div>
                            </div>
                            <div class="col-lg-2">
                                <button type="submit" class="btn btn-primary btn-block" style="height: 3rem; font-size: 1.3rem"><i class="fa-solid fa-paper-plane"></i></button>
                            </div>
                        </div>

                        <c:if test="${not empty url}">
                            <p class="text-success fs-5 fw-bold">Your cute link: <a href="${url}" target="_blank">oi.io.vn/${url}</a></p>
                        </c:if>

                        <div class="divider d-flex align-items-center my-3">
                            <p class="text-center fw-bold mx-3 mb-0 text-muted">OPTIONAL OPTIONS</p>
                        </div>

                        <div class="row">
                            <div class="col-lg-4 mb-4">
                                <span>Choose a <b>custom short link</b> by your self</span>
                                <div class="form-outline mt-1">
                                    <input type="text" name="uid" id="uid" class="form-control" />
                                    <label class="form-label" for="uid">Custom short url</label>
                                </div>
                            </div>
                            <div class="col-lg-4 mb-4">
                                <span>Set a <b>passcode</b> for your shortened link</span>
                                <div class="form-outline mt-1">
                                    <input type="text" name="passcode" id="passcode" class="form-control" />
                                    <label class="form-label" for="passcode">Passcode</label>
                                </div>
                            </div>
                            <div class="col-lg-4 mb-4">
                                <span>Set <b>expiration time</b> for your shortened link</span>
                                <div class="form-outline mt-1">
                                    <input type="datetime-local" name="expiration-time" id="datetimepicker" class="form-control" />
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-lg-4 mb-4">
                                <span>Set <b>redirect time</b> for your shortened link</span>
                                <div class="form-outline mt-1">
                                    <input type="number" name="redirect-time" min="5" max="60" id="redirect-time" class="form-control" />
                                    <label class="form-label" for="redirect-time">Redirect time</label>
                                </div>
                            </div>
                            <div class="col-lg-8 mb-4">
                                <span>Set <b>redirect message</b>(only show if there is a redirect time)</span>
                                <div class="form-outline mt-1">
                                    <textarea class="form-control" name="redirect-message" id="textAreaExample" rows="2" maxlength="250"></textarea>
                                    <label class="form-label" for="textAreaExample">Redirect message</label>
                                </div>
                            </div>
                        </div>
                        <c:if test="${roleService.isUser(pageContext.request)}">
                            <div class="row">
                                <div class="col mb-4">
                                    <span>Add <b>note</b>(only for registered user ^^)</span>
                                    <div class="form-outline mt-1">
                                        <textarea class="form-control" name="note" id="textAreaExample" rows="2" maxlength="250"></textarea>
                                        <label class="form-label" for="textAreaExample">Note</label>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </form>
                </div>
            </div>
        </div>
        <%@include file="templates/footer.html" %>
        <c:if test="${roleService.isGuest(pageContext.request)}">
            <div id="g_id_onload"
                 data-client_id="205125337007-vp8gcc90umgim1krgh90e6lcafj12obf.apps.googleusercontent.com"
                 data-context="signin"
                 data-ux_mode="popup"
                 data-login_uri="${pageContext.request.contextPath}/auth/login-google"
                 data-itp_support="true"></div>
            <script src="https://accounts.google.com/gsi/client" async defer></script>
        </c:if>
        <%@include file="templates/mdb-script.html" %>
    </body>
</html>
