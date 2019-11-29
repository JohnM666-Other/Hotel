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
<div class="container">
    <a href="/admin-hotels">Edit hotels</a>
    <div class="my-4">
        <form method="POST" action="/admin/changeLogLevel">
            <label class="text-light">Change log level:</label>
            <input name="LogLevel"/>
            <button type="submit">OK</button>
        </form>
    </div>
</div>
</body>
</html>