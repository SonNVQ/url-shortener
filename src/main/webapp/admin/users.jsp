<%-- 
    Document   : users
    Created on : Jul 13, 2023, 4:30:37 AM
    Author     : nguyenson
--%>

<%@page import="Models.User"%>
<%@page import="Models.Role"%>
<%@page import="java.util.HashSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Models.Url,java.time.LocalDateTime"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="roleService" class="Services.Impl.RoleService" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>My links</title>
        <%@include file="../templates/mdb-link.jsp" %>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/SonNVQ/mdb@main/css/select.min.css"/>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/SonNVQ/mdb@main/css/datepicker.min.css"/>
        <style>
            .truncate-3 {
                display: -webkit-box;
                -webkit-line-clamp: 3;
                -webkit-box-orient: vertical;
                width: 100%;
                overflow: hidden;
            }
        </style>
    </head>
    <body class="d-flex flex-column min-vh-100">
        <%@include file="../templates/navbar.jsp" %>
        <c:choose>
            <c:when test="${roleService.isAdmin(pageContext.request)}">                
                <c:set var="isAdmin" value="${true}" />
            </c:when>
        </c:choose>
        <div class="container" >
            <h3 class="text-center mt-3">Users</h3>
            <div class="mb-4">
                <form 
                    action="${pageContext.request.contextPath}/admin/users/search"                 
                    method="GET">
                    <div class="row d-flex justify-content-center">
                        <div class="col-md-2" style="min-width: 100px;">
                            <div class="mt-2">
                                <select name="searchField" class="form-control select">
                                    <c:choose>
                                        <c:when test='${empty searchField}'>
                                            <option value="name" selected>Name</option>
                                            <option value="username">Username</option>
                                            <option value="email">Email</option>
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${searchField eq 'name'}">
                                                    <option value="name" selected>Name</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="name">Name</option>
                                                </c:otherwise>
                                            </c:choose>
                                            <c:choose>
                                                <c:when test="${searchField eq 'username'}">
                                                    <option value="username" selected>Username</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="username">Username</option>
                                                </c:otherwise>
                                            </c:choose>
                                            <c:choose>
                                                <c:when test="${searchField eq 'email'}">
                                                    <option value="email" selected>Email</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="email">Email</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
                                </select>
                                <label class="form-label select-label">Field</label>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-outline mt-2">
                                <input
                                    type="text"
                                    name="searchText"
                                    id="typePasswordX-2"
                                    class="form-control"
                                    value="${searchText}"
                                    />
                                <label class="form-label" for="typePasswordX-2">Text</label>
                            </div>
                        </div>
                        <div class="col-md-1">
                            <div class="col-md-2">
                                <button
                                    class="btn btn-primary mt-2"
                                    type="submit"
                                    >
                                    Search
                                </button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>

            <c:if test="${param.status eq 'deleted-success'}">
                <h5 class="text-success mt-1">Notification: Deleted successfully!</h5>
            </c:if>
            <c:if test="${param.status eq 'deleted-failed'}">
                <h5 class="text-danger mt-1">Notification: Deleted failed! Please try again!</h5>
            </c:if>

            <table class="table table-bordered table-striped table-hover table-sm align-middle mt-2 mb-0 bg-white">
                <thead class="bg-light">
                    <tr>
                        <th class="text-center">#</th>
                        <th>Username</th>
                        <th>Firstname</th>
                        <th>Lastname</th>
                        <th>Email</th>                     
                        <th>Google Email</th>    
                        <th class="text-center">Role</th>
                        <th class="text-center">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="i" value="0" />
                    <c:forEach var="item" items="${users}">
                        <c:set var="i" value="${Integer.parseInt(i) + 1}" />
                        <tr>
                            <td class="text-center" style="width: 3%; max-width: 15px;">${i}</td>
                            <td>${item.username}</td>
                            <td>${item.firstName}</td>
                            <td>${item.lastName}</td>
                            <td>${item.email}</td>
                            <td>${item.googleEmail}</td>
                            <td>
                                <span class="badge badge-primary rounded-pill w-100">User</span>
                                <c:set var="currentUser" value="${item}" />
                                <%
                                    User user = (User) pageContext.getAttribute("currentUser");
                                    HashSet<Role> roles = user.getRoles();
                                    if (roles != null && roles.contains(Role.ADMIN)) {
                                        out.println("<span class=\"badge badge-danger rounded-pill w-100\">Admin</span>");
                                    }
                                %>

                            </td>
                            <td class="text-center" style="width: 10%; max-width: 50px;">
                                <a class="text-success mx-1" href="${pageContext.request.contextPath}/admin/users/changePass?id=${item.id}" role="button">
                                    <i class="fa-solid fa-pen-to-square fa-lg"></i>
                                </a>

                                <a class="text-danger mx-1" href="#" data-mdb-toggle="modal" data-mdb-target="#set-${item.id}">
                                    <i class="fa-solid fa-unlock fa-lg"></i>
                                </a>

                                <!-- Modal -->
                                <div class="modal fade" id="set-${item.id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h4 class="modal-title fw-bold" id="exampleModalLabel">Are you sure?</h4>
                                                <button type="button" class="btn-close" data-mdb-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <h5 class="text-warning" style="text-align: left;">
                                                    You are going to set admin for account: ${item.username} / ${item.email}
                                                </h5>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-mdb-dismiss="modal">Close</button>
                                                <form action="${pageContext.request.contextPath}/admin/users/setAdmin" method="POST">
                                                    <input type="hidden" name="id" value="${item.id}"/>
                                                    <button type="submit" class="btn btn-danger">Sure</button>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <a class="text-danger mx-1" href="#" data-mdb-toggle="modal" data-mdb-target="#unset-${item.id}">
                                    <i class="fa-solid fa-lock fa-lg"></i>
                                </a>

                                <!-- Modal -->
                                <div class="modal fade" id="unset-${item.id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h4 class="modal-title fw-bold" id="exampleModalLabel">Are you sure?</h4>
                                                <button type="button" class="btn-close" data-mdb-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <h5 class="text-warning" style="text-align: left;">
                                                    You are going to remove admin role for account: ${item.username} / ${item.email}
                                                </h5>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-mdb-dismiss="modal">Close</button>
                                                <form action="${pageContext.request.contextPath}/admin/users/removeAdmin" method="POST">
                                                    <input type="hidden" name="id" value="${item.id}"/>
                                                    <button type="submit" class="btn btn-danger">Sure</button>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <div class="d-flex mt-3" id="pageBar"></div>
            <script>
                var key = ${page},
                        start = ${start},
                        end = ${end},
                        size = ${size},
                        total = ${total},
                        result = '';
                result += '<ul class=\"pagination mx-auto\">';
                if (start > 1) {
                    const url = setPaginationParams(size, 1);
                    result += "<li class=\"page-item\">\n" +
                            "<a class=\"page-link\" href=\"" + url + "\">First</a>\n" +
                            "</li>";
                }
                for (i = start; i <= end; ++i) {
                    if (i === key) {
                        result += "<li class=\"page-item active\" aria-current=\"page\">\n" +
                                "<a class=\"page-link\" href=\"#\">" + i + "<span class=\"visually-hidden\">(current)</span></a>\n" +
                                "</li>";
                    } else {
                        const url = setPaginationParams(size, i);
                        result += '<li class="page-item"><a class="page-link" href="' + url + '">' + i + '</a></li>';
                    }
                }
                if (end < total) {
                    const url = setPaginationParams(size, total);
                    result += "<li class=\"page-item\">\n" +
                            "<a class=\"page-link\" href=\"" + url + "\">Last</a>\n" +
                            "</li>";
                }
                result += '</ul>';
                document.getElementById('pageBar').innerHTML = result;

                function setPaginationParams(size, page) {
                    const url = new URL(document.location.href);
                    url.searchParams.set('size', size);
                    url.searchParams.set('page', page);
                    return url.toString();
                }

                function getDeletedStatusUrl() {
                    const url = new URL(document.location.href);
                    url.searchParams.delete('status');
                    return url.toString();
                }

                history.replaceState(null, '', getDeletedStatusUrl());
            </script>
        </div>
        <%@include file="../templates/footer.html" %>
        <%@include file="../templates/mdb-script.jsp" %>
        <script src="https://cdn.jsdelivr.net/gh/SonNVQ/mdb@main/js/select.min.js"></script>
        <script src="https://cdn.jsdelivr.net/gh/SonNVQ/mdb@main/js/datepicker.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
    </body>
</html>

