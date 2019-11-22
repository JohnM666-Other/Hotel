<!DOCTYPE HTML>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Getting Started: Serving Web Content</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="../css/index.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="../js/index.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">

</nav>
<h1 class="text-light text-center text">Sign up</h1>
<div class="container w-25">
    <form class="m-2" action="/api/user/signup" method="POST">
        <div class="form-group">
            <label class="text-light">Email </label>
            <input required placeholder="Email" class="form-control" type="text" id="email" name="email"/>
        </div>
        <div class="form-group">
            <label class="text-light">First name</label>
            <input required placeholder="First name" class="form-control" type="text" id="firstname" name="firstname"/>
        </div>
        <div class="form-group">
            <label class="text-light">Last name</label>
            <input required placeholder="Last name" class="form-control" type="text" id="lastname" name="lastname"/>
        </div>
        <div class="form-group">
            <label class="text-light">Birth date</label>
            <input required placeholder="Birth date" class="form-control" type="text" id="birthdate" name="birthdate"/>
        </div>
        <div class="form-group d-none">
            <label class="text-light">Sex</label>
            <input required value="F" placeholder="Sex" class="form-control" type="text" id="sex" name="sex"/>
        </div>
        <div class="form-group">
            <label class="text-light">Password</label>
            <input required placeholder="New password" class="form-control" type="password" id="password" name="password"/>
        </div>
        <div class="form-group">
            <button class="form-control btn btn-primary" type="submit">Sign up</button>
        </div>
    </form>
</div>
</body>
</html>