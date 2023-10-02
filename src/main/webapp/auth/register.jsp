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
        <title>Sign up</title>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <!-- Font Awesome -->
        <link
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
            rel="stylesheet"
            />
        <!-- Google Fonts -->
        <link
            href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
            rel="stylesheet"
            />
        <!-- MDB -->
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
                                    <h3 class="mb-3">SIGN UP</h3>

                                    <c:if test="${status eq 'successful'}">
                                        <p class="text-success"><a href="login">Successfully. Please go to sign in!</a></p>
                                    </c:if>
                                    <form action="register" method="POST">

                                        <div class="form-outline">
                                            <input
                                                type="username"
                                                name="username"
                                                id="typeUsernameX-2"
                                                class="form-control form-control-lg"
                                                value="${username}"
                                                required
                                                />
                                            <label class="form-label" for="typeUsernameX-2"
                                                   >Username</label
                                            >
                                        </div>
                                        <c:if test="${USERNAME_FORMAT_INVALID eq true}">
                                            <div class="mt-1">
                                                <p class="text-warning text-start m-0">Username must:</p>
                                                <ul class="text-warning text-start mt-0">
                                                    <li>have length between 6 and 20</li>
                                                    <li>does not include special characters</li>
                                                </ul>
                                            </div>
                                        </c:if>
                                        <c:if test="${USERNAME_IS_EXISTED eq true}">
                                            <p class="text-warning text-start mt-1">Username is existed!</p>
                                        </c:if>

                                        <div class="form-outline mt-3">
                                            <input
                                                type="password"
                                                name="password"
                                                id="typePasswordX-2"
                                                class="form-control form-control-lg"
                                                required
                                                />
                                            <label class="form-label" for="typePasswordX-2"
                                                   >Password</label
                                            >
                                        </div>
                                        <c:if test="${PASSWORD_FORMAT_INVALID eq true}">
                                            <div class="mt-1">
                                                <p class="text-warning text-start m-0">Password must:</p>
                                                <ul class="text-warning text-start mt-0">
                                                    <li>have length between 6 and 20</li>
                                                    <li>has at least one special character</li>
                                                    <li>has at least one upper character A-Z</li>
                                                    <li>has at least one digit character 0-9</li>
                                                </ul>
                                            </div>
                                        </c:if>

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
                                                        required
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
                                                name="email"
                                                id="typePasswordX-2"
                                                class="form-control form-control-lg"
                                                value="${email}"
                                                required
                                                />
                                            <label class="form-label" for="typePasswordX-2"
                                                   >Email</label
                                            >
                                        </div>
                                        <c:if test="${EMAIL_FORMAT_INVALID eq true}">
                                            <p class="text-warning text-start mt-1">Email format is invalid!</p>
                                        </c:if>
                                        <c:if test="${EMAIL_IS_EXISTED eq true}">
                                            <p class="text-warning text-start mt-1">Email is existed!</p>
                                        </c:if>

                                        <div class="mt-4">
                                            <button
                                                class="btn btn-warning btn-lg btn-block"
                                                type="submit"
                                                >
                                                Register
                                            </button>
                                        </div>
                                    </form>
                                    <p class="small fw-bold mt-3 pt-1 mb-0 text-start">
                                        Already have account?
                                        <a href="login" class="link-primary">Login</a>
                                    </p>
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
        </section>
        <script
            type="text/javascript"
            src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.3.1/mdb.min.js"
        ></script>
    </body>
</html>