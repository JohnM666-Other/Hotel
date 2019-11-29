<!DOCTYPE HTML>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
        <title>Hotels</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="../css/app.css" >
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="../js/index.js" type="application/javascript"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container">
                <form action="/search/0" method="GET" class="form-inline mr-auto my-2 my-lg-0">
                    <div class="col-10">
                        <input value="${text}" class="form-control mr-sm-2 w-100" name="text" type="search" placeholder="Search..."/>
                    </div>
                    <div class="col-2">
                        <button class="btn btn-primary my-2 my-sm-0" type="submit">Search</button>
                    </div>
                </form>
                <div class="ml-auto">
                    <c:choose>
                        <c:when test="${not empty username}">
                            <div class="dropdown">
                                <a href="#" data-toggle="dropdown">${username}</a>
                                <div class="dropdown-menu dropdown-menu-right bg-dark">
                                    <a class="dropdown-item bg-dark" href="/profile">Profile</a>
                                    <a class="dropdown-item bg-dark" href="/logout">Logout</a>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <a class="mx-2" href="/login">Login</a>
                            <a href="/signup">Sign up</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </nav>
        <h1 class="text-light text-center text">Hotels</h1>
        <div class="w-100 text-center text-light">
            <c:choose>
                <c:when test="${not empty page}">
                    <a href="/search/${page-1}?text=${text}&minStars=${minStars}">${page-1}</a>
                    <span>${page}</span>
                    <a href="/search/${page+1}?text=${text}&minStars=${minStars}">${page+1}</a>
                </c:when>
            </c:choose>
        </div>
        <div class="container">
            <c:forEach items="${hotels}" var="item">
                <div class="hotel p-2 hover-glow rounded-p d-flex flex-column light-background" data-hotel-id="${item.id}">
                    <div class="hotel-head">
                        <h3 class="text-light"><c:out value="${item.name}"/></h3>
                    </div>
                    <div class="hotel-body d-flex flex-row">
                        <div class="hotel-img-container">
                            <img class="hotel-img img-invert" src="../res/hotel.png" alt="img"/>
                        </div>
                        <div class="hotel-details d-flex flex-column">
                            <p class="p-0 m-0 text-light">
                                <c:forEach begin="1" end="${item.stars}" var="i">
                                    <i class="star"></i>
                                </c:forEach>
                            </p>
                            <p class="p-0 m-0 text-light">Country: <c:out value="${item.country}"/></p>
                            <p class="p-0 m-0 text-light">City: <c:out value="${item.city}"/></p>
                            <p class="p-0 m-0 text-light"><a href="${item.url}"><c:out value="${item.url}"/></a></p>
                        </div>
                        <div class="hotel-avg-score">
                            <p class="text-light"><fmt:formatNumber value="${item.avgScore}" type="number" maxFractionDigits="1"/></p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </body>
</html>