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
        <title>Login</title>
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
                            <div class="card shadow-2-strong" style="border-radius: 1rem">
                                <div class="card-body p-5 text-center">
                                    <h3 class="mb-3">SIGN UP</h3>
                                    <c:if test="${status eq 'failed'}">
                                        <p class="text-warning">Added failed!</p>
                                    </c:if>
                                    <c:if test="${status eq 'successful'}">
                                        <p class="text-success">Added successfully!</p>
                                    </c:if>
                                    <form action="register" method="POST">

                                        <div class="form-outline mb-4">
                                            <input
                                                type="username"
                                                name="username"
                                                id="typeUsernameX-2"
                                                class="form-control form-control-lg"
                                                />
                                            <label class="form-label" for="typeUsernameX-2"
                                                   >Username</label
                                            >
                                        </div>

                                        <div class="form-outline mb-4">
                                            <input
                                                type="password"
                                                name="password"
                                                id="typePasswordX-2"
                                                class="form-control form-control-lg"
                                                />
                                            <label class="form-label" for="typePasswordX-2"
                                                   >Password</label
                                            >
                                        </div>

                                        <div class="row">
                                            <div class="col-lg-6">
                                                <div class="form-outline mb-4">
                                                    <input
                                                        type="text"
                                                        name="firstname"
                                                        id="typePasswordX-2"
                                                        class="form-control form-control-lg"
                                                        />
                                                    <label class="form-label" for="typePasswordX-2"
                                                           >First name</label
                                                    >
                                                </div>
                                            </div>
                                            <div class="col-lg-6">
                                                <div class="form-outline mb-4">
                                                    <input
                                                        type="text"
                                                        name="lastname"
                                                        id="typePasswordX-2"
                                                        class="form-control form-control-lg"
                                                        />
                                                    <label class="form-label" for="typePasswordX-2"
                                                           >Last name</label
                                                    >
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-outline mb-4">
                                            <input
                                                type="email"
                                                name="email"
                                                id="typePasswordX-2"
                                                class="form-control form-control-lg"
                                                />
                                            <label class="form-label" for="typePasswordX-2"
                                                   >Email</label
                                            >
                                        </div>

                                        <button
                                            class="btn btn-warning btn-lg btn-block"
                                            type="submit"
                                            >
                                            Register
                                        </button>
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
        </section>
        <script
            type="text/javascript"
            src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.3.1/mdb.min.js"
        ></script>
    </body>
</html>