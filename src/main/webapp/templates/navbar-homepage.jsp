<%-- 
    Document   : nav-bar
    Created on : Jun 25, 2023, 5:13:20 PM
    Author     : nguyenson
--%>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <!-- Container wrapper -->
    <div class="container align-items-baseline">
        <!-- Navbar brand -->
        <a class="fs-5 fw-bolder" href="">
            oi.io.vn
        </a>

        <!-- Toggle button -->
        <button
            class="navbar-toggler"
            type="button"
            data-mdb-toggle="collapse"
            data-mdb-target="#navbarButtonsExample"
            aria-controls="navbarButtonsExample"
            aria-expanded="false"
            aria-label="Toggle navigation"
            >
            <i class="fas fa-bars"></i>
        </button>

        <!-- Collapsible wrapper -->
        <div class="collapse navbar-collapse" id="navbarButtonsExample">
            <!-- Left links -->
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item ms-2">
                    <a class="nav-link" href="#" style="color: #3b3a3a;">Shorten links in milliseconds!</a>
                </li>
            </ul>
            <!-- Left links -->

            <div class="d-flex align-items-center">
                <!--                <a href="auth/login" class="btn btn-link px-3 me-2">
                                    Login
                                </a>-->
                <!--                <a href="auth/login" class="btn btn-primary me-3">
                                    Sign in
                                </a>-->
                <a href="auth/login">
                    <i class="fa-solid fa-right-to-bracket"></i>
                    Sign in/Sign up
                </a>
            </div>
        </div>
        <!-- Collapsible wrapper -->
    </div>
    <!-- Container wrapper -->
</nav>
<!-- Navbar -->
