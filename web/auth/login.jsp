<%-- 
    Document   : login.jsp
    Created on : Jun 10, 2023, 11:28:24 PM
    Author     : nguyenson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                                    <h3 class="mb-3">SIGN IN</h3>
                                    <button
                                        class="btn btn-lg btn-block btn-primary"
                                        style="background-color: #dd4b39"
                                        type="submit"
                                        >
                                        <i class="fab fa-google me-2"></i> Continue with google
                                    </button>

                                    <div class="divider d-flex align-items-center my-3">
                                        <p class="text-center fw-bold mx-3 mb-0 text-muted">OR</p>
                                    </div>

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

                                    <div
                                        class="d-flex justify-content-between align-items-center mb-4 my-0"
                                        >
                                        <!-- Checkbox -->
                                        <div class="form-check my-0">
                                            <input
                                                class="form-check-input"
                                                type="checkbox"
                                                value=""
                                                id="form1Example3"
                                                checked
                                                />
                                            <label class="form-check-label" for="form1Example3">
                                                Remember me
                                            </label>
                                        </div>
                                        <a href="#!">Forgot password?</a>
                                    </div>
                                    <button
                                        class="btn btn-primary btn-lg btn-block"
                                        type="submit"
                                        >
                                        Login
                                    </button>
                                    <p class="small fw-bold mt-3 pt-1 mb-0 text-start">
                                        Don't have an account?
                                        <a href="#!" class="link-danger">Register</a>
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