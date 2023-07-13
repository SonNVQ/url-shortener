<%-- 
    Document   : links
    Created on : Jun 26, 2023, 9:15:41 PM
    Author     : nguyenson
--%>

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
            <h3 class="text-center mt-3">My links</h3>
            <div class="mb-4">
                <form 
                    <c:choose>
                        <c:when test="${isAdmin}">
                            action="${pageContext.request.contextPath}/admin/links/search"                 
                        </c:when>
                        <c:otherwise>
                            action="${pageContext.request.contextPath}/links/search" 
                        </c:otherwise>
                    </c:choose>
                    method="GET">
                    <div class="row d-flex justify-content-center">
                        <div class="col-md-1" style="min-width: 100px;">
                            <div class="mt-2">
                                <select name="searchField" class="form-control select">
                                    <c:choose>
                                        <c:when test='${empty searchField}'>
                                            <option value="title" selected>Title</option>
                                            <option value="link">Link</option>
                                            <option value="uid">ID</option>
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${searchField eq 'title'}">
                                                    <option value="title" selected>Title</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="title">Title</option>
                                                </c:otherwise>
                                            </c:choose>
                                            <c:choose>
                                                <c:when test="${searchField eq 'link'}">
                                                    <option value="link" selected>Link</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="link">Link</option>
                                                </c:otherwise>
                                            </c:choose>
                                            <c:choose>
                                                <c:when test="${searchField eq 'uid'}">
                                                    <option value="uid" selected>ID</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="uid">ID</option>
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
                        <div class="col-md-2">
                            <div class="form-outline mt-2">
                                <!--                            <input type="date" name="from-date" id="from-date" class="form-control" value="" />
                                                            <label class="form-label" for="from-date">From</label>
                                                        </div>-->
                                <div class="form-outline datepicker mt-2" data-mdb-inline="true">
                                    <input type="text" name="searchFrom" value="${searchFrom}" class="form-control" id="exampleDatepicker2">
                                    <label for="exampleDatepicker2" class="form-label">From</label>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-2">
                            <div class="form-outline mt-2">
                                <!--                            <input type="date" name="from-date" id="from-date" class="form-control" value="" />
                                                            <label class="form-label" for="from-date">From</label>
                                                        </div>-->
                                <div class="form-outline datepicker mt-2" data-mdb-inline="true">
                                    <input type="text" name="searchTo" value="${searchTo}" class="form-control" id="exampleDatepicker2">
                                    <label for="exampleDatepicker2" class="form-label">To</label>
                                </div>
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
                    <tr class="text-center">
                        <th class="text-center">#</th>
                        <th>Shortened link</th>
                        <th>Link</th>
                        <th>Title</th>
                        <th>Status</th>
                        <!--                        <th>Passcode</th>
                                                <th>R_Time</th>
                                                <th>R_Message</th>-->
                        <th class="text-center">Created time</th>                        
                        <th>Note</th>                        
                        <th class="text-center">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="i" value="0" />
                    <c:forEach var="item" items="${urls}">
                        <c:set var="i" value="${Integer.parseInt(i) + 1}" />
                        <tr>
                            <td class="text-center" style="width: 3%; max-width: 15px;">
                                ${i}
                            </td>
                            <td style="width: 10%; max-width: 100px;">
                                <a href="${item.uid}" target="_blank" class="truncate-3">oi.io.vn/${item.uid}</a>
                            </td>
                            <td style="width: 20%; max-width: 160px;">
                                <a href="${item.link}"  target="_blank" class="truncate-3">
                                    ${item.link}
                                </a>
                            </td>
                            <td style="width: 20%; max-width: 160px;">
                                <span class="truncate-3">
                                    ${item.title}                   
                                </span>
                            </td>
                            <td style="width: 8%; max-width: 90px;">
                                <c:if test="${item.isBanned eq true}">
                                    <span class="badge badge-danger rounded-pill w-100">Banned</span>
                                </c:if>
                                <c:set var="currentUrl" value="${item}" />
                                <%
                                    LocalDateTime now = LocalDateTime.now();
                                    Url url = (Url) pageContext.getAttribute("currentUrl");
                                    if (url.getExpirationTime() != null && url.getExpirationTime().compareTo(LocalDateTime.now()) < 0) {
                                        out.println("<span class=\"badge badge-danger rounded-pill w-100\">Expired</span>");
                                        pageContext.setAttribute("isExpired", true);
                                    } else {
                                        pageContext.setAttribute("isExpired", false);
                                    }
                                %>
                                <c:if test="${not isExpired}">
                                    <c:choose>
                                        <c:when test="${not empty item.passcode}">
                                            <span class="badge badge-warning rounded-pill w-100">Passcode</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-success rounded-pill w-100">Public</span>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:if test="${not empty item.redirectTime}">
                                        <span class="badge badge-primary rounded-pill w-100">Time: ${item.redirectTime}</span>
                                    </c:if>
                                    <c:if test="${not empty item.redirectMessage}">
                                        <span class="badge badge-primary rounded-pill w-100">Message</span>
                                    </c:if>
                                </c:if>
                            </td>
        <!--                            <td>${item.passcode}</td>
                            <td>${item.redirectTime}</td>
                            <td style="width: 15%; max-width: 140px;">
                                <span class="truncate-3">
                            ${item.redirectMessage}                     
                        </span>
                    </td>
                    <td style="width: 15%; max-width: 140px;">
                        <span class="truncate-3">
                            ${item.expirationTime}                    
                        </span>
                    </td>-->
                            <td class="text-center" style="width: 15%; max-width: 130px;">
                                <fmt:parseDate value="${ item.createdTime }" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parsedDateTime" type="both" />
                                <fmt:formatDate pattern="dd/MM/yyyy - HH:mm:ss" value="${ parsedDateTime }" />
                                <!--${item.createdTime}-->
                            </td>
                            <td>${item.note}</td>
                            <td class="text-center" style="width: 10%; max-width: 50px;">
                                <a class="text-success mx-1" href="${pageContext.request.contextPath}/links/edit?uid=${item.uid}" role="button">
                                    <i class="fa-solid fa-pen-to-square fa-lg"></i>
                                </a>
                                <!-- Button trigger modal -->
                                <!--                                <button type="button" class="btn btn-primary" data-mdb-toggle="modal" data-mdb-target="#exampleModal">
                                                                    Launch demo modal
                                                                </button>-->
                                <a class="text-danger mx-1" href="#" data-mdb-toggle="modal" data-mdb-target="#delele-${item.id}">
                                    <i class="fa-solid fa-trash fa-lg"></i>
                                </a>

                                <!-- Modal -->
                                <div class="modal fade" id="delele-${item.id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h4 class="modal-title fw-bold" id="exampleModalLabel">Are you sure?</h4>
                                                <button type="button" class="btn-close" data-mdb-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <h5 class="text-warning" style="text-align: left;">
                                                    You are going to delete: 
                                                    <a href="#">${initParam.domain}/${item.uid}</a>
                                                </h5>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-mdb-dismiss="modal">Close</button>
                                                <form action="${pageContext.request.contextPath}/links/delete" method="POST">
                                                    <input type="hidden" name="id" value="${item.id}"/>
                                                    <button type="submit" class="btn btn-danger">Delete</button>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <c:if test="${isAdmin}">
                                    <a class="text-danger mx-1" href="#" data-mdb-toggle="modal" data-mdb-target="#ban-${item.id}">
                                        <i class="fa-solid fa-lock fa-lg"></i>
                                    </a>

                                    <!-- Modal -->
                                    <div class="modal fade" id="ban-${item.id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h4 class="modal-title fw-bold" id="exampleModalLabel">Are you sure?</h4>
                                                    <button type="button" class="btn-close" data-mdb-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <div class="modal-body">
                                                    <h5 class="text-warning" style="text-align: left;">
                                                        You are going to ban: 
                                                        <a href="#">${initParam.domain}/${item.uid}</a>
                                                    </h5>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary" data-mdb-dismiss="modal">Close</button>
                                                    <form action="${pageContext.request.contextPath}/admin/links/ban" method="POST">
                                                        <input type="hidden" name="id" value="${item.id}"/>
                                                        <button type="submit" class="btn btn-danger">Sure</button>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <a class="text-danger mx-1" href="#" data-mdb-toggle="modal" data-mdb-target="#unban-${item.id}">
                                        <i class="fa-solid fa-unlock fa-lg"></i>
                                    </a>

                                    <!-- Modal -->
                                    <div class="modal fade" id="unban-${item.id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h4 class="modal-title fw-bold" id="exampleModalLabel">Are you sure?</h4>
                                                    <button type="button" class="btn-close" data-mdb-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <div class="modal-body">
                                                    <h5 class="text-warning" style="text-align: left;">
                                                        You are going to ban: 
                                                        <a href="#">${initParam.domain}/${item.uid}</a>
                                                    </h5>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary" data-mdb-dismiss="modal">Close</button>
                                                    <form action="${pageContext.request.contextPath}/admin/links/unban" method="POST">
                                                        <input type="hidden" name="id" value="${item.id}"/>
                                                        <button type="submit" class="btn btn-danger">Sure</button>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>


                                </c:if>
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
