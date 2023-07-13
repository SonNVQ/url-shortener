<%-- 
    Document   : nav-bar
    Created on : Jun 25, 2023, 5:13:20 PM
    Author     : nguyenson
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="navRoleService" class="Services.Impl.RoleService" />
<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <!-- Container wrapper -->
    <div class="container align-items-baseline">
        <!-- Navbar brand -->
        <a class="fs-5 fw-bolder" href="${pageContext.request.contextPath}/">
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
                <c:choose>
                    <c:when test="${navRoleService.isGuest(pageContext.request)}">                
                        <li class="nav-item ms-2">
                            <a class="nav-link" href="#" style="color: #3b3a3a;">Shorten links in milliseconds!</a>
                        </li>
                    </c:when>
                    <c:when test="${navRoleService.isAdmin(pageContext.request)}">                
                        <li class="nav-item ms-3">
                            <a class="nav-link" href="${pageContext.request.contextPath}/">
                                <i class="fa-solid fa-paper-plane"></i>
                                Shorten link
                            </a>
                        </li>
                        <!--                        <li class="nav-item">
                                                    <a class="nav-link" href="#">-</a>
                                                </li>-->
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin/links/search">
                                <i class="fa-solid fa-bookmark"></i>
                                All Links
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin/users/search">
                                <i class="fa-solid fa-user"></i>
                                Users
                            </a>
                        </li>
                    </c:when>
                    <c:otherwise>                
                        <li class="nav-item ms-3">
                            <a class="nav-link" href="${pageContext.request.contextPath}/">
                                <i class="fa-solid fa-paper-plane"></i>
                                Shorten link
                            </a>
                        </li>
                        <!--                        <li class="nav-item">
                                                    <a class="nav-link" href="#">-</a>
                                                </li>-->
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/links/search">
                                <i class="fa-solid fa-bookmark"></i>
                                My links
                            </a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
            <!-- Left links -->

            <ul class="navbar-nav ms-auto d-flex flex-row">

                <!-- Avatar -->
                <li class="nav-item dropdown">
                    <c:choose>
                        <c:when test="${navRoleService.isGuest(pageContext.request)}">                
                            <div class="d-flex align-items-center">
                                <!--                <a href="auth/login" class="btn btn-link px-3 me-2">
                                                    Login
                                                </a>-->
                                <!--                <a href="auth/login" class="btn btn-primary me-3">
                                                    Sign in
                                                </a>-->
                                <a href="${pageContext.request.contextPath}/auth/login">
                                    <i class="fa-solid fa-right-to-bracket"></i>
                                    Sign in/Sign up
                                </a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <a
                                class="nav-link dropdown-toggle hidden-arrow d-flex align-items-center"
                                href="#"
                                id="navbarDropdownMenuLink"
                                role="button"
                                data-mdb-toggle="dropdown"
                                aria-expanded="false"
                                >
                                <div class="me-2">
                                    Hello, ${navRoleService.getUserLastName(pageContext.request)}
                                </div> 
                                <img
                                    src="https://mdbootstrap.com/img/Photos/Avatars/img (31).jpg"
                                    class="rounded-circle"
                                    height="22"
                                    alt=""
                                    loading="lazy"
                                    />
                            </a>
                            <ul
                                class="dropdown-menu dropdown-menu-end"
                                aria-labelledby="navbarDropdownMenuLink"
                                >
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/profile">My profile</a></li>
                                <!--<li><a class="dropdown-item" href="#">Settings</a></li>-->
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/auth/logout">Logout</a></li>
                            </ul>
                        </c:otherwise>
                    </c:choose>
                </li>
            </ul>



        </div>
        <!-- Collapsible wrapper -->
    </div>
    <!-- Container wrapper -->
</nav>
<!-- Navbar -->
