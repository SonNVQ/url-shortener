<%-- 
    Document   : links
    Created on : Jun 26, 2023, 9:15:41 PM
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
        <div class="container" >
            <h2 class="text-center my-4">My links</h2>
            <table class="table table-bordered table-striped table-hover table-sm align-middle mb-0 bg-white">
                <thead class="bg-light">
                    <tr>
                        <th>#</th>
                        <th>Shortened link</th>
                        <th>Link</th>
                        <th>Title</th>
                        <th>Status</th>
                        <th>Passcode</th>
                        <th>R_Time</th>
                        <th>R_Message</th>
                        <th>Note</th>                        
                        <th class="text-center">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="i" value="0" />
                    <c:forEach var="item" items="${urls}">
                        <c:set var="i" value="${Integer.parseInt(i) + 1}" />
                        <tr>
                            <td>
                                ${i}
                            </td>
                            <td style="width: 10%; max-width: 100px;">
                                <a href="${item.uid}" target="_blank">oi.io.vn/${item.uid}</a>
                            </td>
                            <td style="width: 20%; max-width: 160px;">
                                <a href="${item.link}"  target="_blank" class="truncate-3">
                                    ${item.link}
                                </a>
                            </td>
                            <td style="width: 15%; max-width: 140px;">
                                <span class="truncate-3">
                                    ${item.title}                   
                                </span>
                            </td>
                            <td>
                                <span class="badge badge-success rounded-pill d-inline">Active</span>
                            </td>
                            <td>${item.passcode}</td>
                            <td>${item.redirectTime}</td>
                            <td style="width: 15%; max-width: 140px;">
                                <span class="truncate-3">
                                    ${item.redirectMessage}                     
                                </span>
                            </td>
                            <td style="width: 15%; max-width: 140px;">
                                <span class="truncate-3">
                                    ${item.note}                    
                                </span>
                            </td>
                            <td class="text-center">
                                <a class="btn btn-link btn-sm btn-rounded" href="links/edit?uid=${item.uid}">
                                    Edit
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <!--            <div class="d-flex mt-3">
                            <ul class="pagination mx-auto">
                                <li class="page-item">
                                    <a class="page-link" href="?size=10&page=1">First</a>
                                </li>
                                <li class="page-item"><a class="page-link" href="#">1</a></li>
                                <li class="page-item active" aria-current="page">
                                    <a class="page-link" href="#">2 <span class="visually-hidden">(current)</span></a>
                                </li>
                                <li class="page-item"><a class="page-link" href="#">3</a></li>
                                <li class="page-item">
                                    <a class="page-link" href="?size=10&page=1">Last</a>
                                </li>
                            </ul>
                        </div>-->
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
                    result += "<li class=\"page-item\">\n" +
                            "<a class=\"page-link\" href=\"?size=" + size + "&page=1\">First</a>\n" +
                            "</li>";
                }
                for (i = start; i <= end; ++i) {
                    if (i === key) {
                        result += "<li class=\"page-item active\" aria-current=\"page\">\n" +
                                "<a class=\"page-link\" href=\"#\">" + i + "<span class=\"visually-hidden\">(current)</span></a>\n" +
                                "</li>";
                    } else {
                        result += '<li class="page-item"><a class="page-link" href="?size=' + size + '&page=' + i + '">' + i + '</a></li>';
                    }
                }
                if (end < total) {
                    result += "<li class=\"page-item\">\n" +
                            "<a class=\"page-link\" href=\"?size=" + size + "&page=" + total + "\">Last</a>\n" +
                            "</li>";
                }
                result += '</ul>';
                document.getElementById('pageBar').innerHTML = result;
            </script>
        </div>
        <%@include file="../templates/footer.html" %>
        <%@include file="../templates/mdb-script.html" %>
    </body>
</html>
