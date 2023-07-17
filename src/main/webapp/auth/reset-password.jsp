<%-- 
    Document   : login.jsp
    Created on : Jun 10, 2023, 11:28:24 PM
    Author     : nguyenson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Sign in</title>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
            rel="stylesheet"
            />
        <link
            href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
            rel="stylesheet"
            />
        <link
            href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.3.1/mdb.min.css"
            rel="stylesheet"
            />
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
    <body>
        <section class="vh-100">
            <div class="container-fluid h-custom">
                <div
                    class="row d-flex flex-row-reverse justify-content-center align-items-center h-100"
                    >
                    <div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1">
                        <div class="row d-flex justify-content-center align-items-center">
                            <div class="card shadow-2-strong" style="border-radius: 1rem; max-width: 450px;">
                                <div class="card-body p-5 text-center">
                                    <h3 class="mb-3">Reset Password</h3>

                                    <!--                                    <button
                                                                            class="btn btn-lg btn-block btn-primary"
                                                                            style="background-color: #dd4b39"
                                                                            type="submit"
                                                                            >
                                                                            <i class="fab fa-google me-2"></i> Continue with google
                                                                        </button>-->

                                    <!--<div>-->

                                    <!--</div>-->

                                    <c:if test="${LINK_IS_INVALID}">
                                        <p class="text-danger text-start mt-1">
                                            This reset link is invalid. Please <a href="${pageContext.request.contextPath}/auth/forgotPassword">request</a> for a new reset link!
                                        </p>
                                    </c:if>
                                    <c:if test="${TOKEN_IS_EXPIRED}">
                                        <p class="text-danger text-start mt-1">
                                            This reset link is expired. Please <a href="${pageContext.request.contextPath}/auth/forgotPassword">request</a> for a new reset link!
                                        </p>
                                    </c:if>
                                    <c:if test="${PASSWORD_IS_INVALID}">
                                        <p class="text-danger text-start mt-1">New password is invalid. Please try again!</p>
                                    </c:if>
                                    <c:if test="${status eq 'failed'}">
                                        <p class="text-danger text-start mt-1">
                                            Change password failed. Please try again!
                                        </p>
                                    </c:if> 
                                    <c:if test="${status eq 'success'}">
                                        <p class="text-success text-start mt-1">
                                            Successful. Go to <a href="${pageContext.request.contextPath}/auth/login">login</a> now!
                                        </p>
                                    </c:if> 
                                    <form action="#" method="POST">
                                        <input type="hidden" name="id" value="${id}" >
                                        <div class="form-outline">
                                            <input
                                                type="text"
                                                name="password"
                                                value="${password}"
                                                id="typeUsernameX-2"
                                                class="form-control form-control-lg"
                                                />
                                            <label class="form-label" for="typeUsernameX-2"
                                                   >Enter new password</label
                                            >
                                        </div>
                                        <div class="form-outline mt-4">
                                            <button
                                                class="btn btn-primary btn-lg btn-block"
                                                type="submit"
                                                >
                                                Set new password
                                            </button>
                                        </div>

                                    </form>

                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-9 col-lg-6 col-xl-5">
                        <img
                            src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.webp"
                            class="img-fluid"
                            alt="Sample image"
                            />
                    </div>
                </div>
            </div>
            <div id="myModal" class="modal fade" tabindex="-1">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title text-warning">Warning</h5>
                            <button type="button" class="btn-close" data-mdb-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <c:choose>
                                <c:when test="${status eq 'google-failed'}">
                                    <p>Login failed. Please try again!</p>
                                </c:when>  
                                <c:when test="${REGISTER_FAILED__EMAIL_IS_EXISTED}">
                                    <p>Registration failed, email already exists. <br>
                                        Please continue or register with another google account!
                                    </p>
                                </c:when> 
                                <c:when test="${REGISTER_FAILED}">
                                    <p>Register failed. Please try again!</p>
                                </c:when>     
                            </c:choose>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-mdb-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <c:if test="${status eq 'google-failed' || REGISTER_FAILED__EMAIL_IS_EXISTED || REGISTER_FAILED}">
            <script>
                document.addEventListener('DOMContentLoaded', function () {
                    myModal = new mdb.Modal(document.getElementById('myModal'));
                    myModal.show();
                });
            </script>
        </c:if>
        <script src="https://accounts.google.com/gsi/client" async defer></script>
        <script
            type="text/javascript"
            src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.3.1/mdb.min.js"
        ></script>
    </body>
</html>