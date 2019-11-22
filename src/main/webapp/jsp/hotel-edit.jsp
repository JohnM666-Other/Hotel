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
<h1 class="text-light text-center text">Hotel details</h1>
<div class="container w-25">
    <form class="m-2" action="/api/hotels/update" method="POST">
        <div class="form-group">
            <label class="text-light">Name</label>
            <input required value="${hotel.name}" placeholder="Name" class="form-control" type="text" id="name" name="name"/>
        </div>
        <div class="form-group">
            <label class="text-light">Description</label>
            <input required value="${hotel.description}" placeholder="Description" class="form-control" type="text" id="description" name="description"/>
        </div>
        <div class="form-group">
            <label class="text-light">Country</label>
            <input required value="${hotel.country}" placeholder="Country" class="form-control" type="text" id="country" name="country"/>
        </div>
        <div class="form-group">
            <label class="text-light">City</label>
            <input required value="${hotel.city}" placeholder="City" class="form-control" type="text" id="city" name="city"/>
        </div>
        <div class="form-group d-none">
            <label class="text-light">Url</label>
            <input required value="${hotel.url}" placeholder="Url" class="form-control" type="text" id="url" name="url"/>
        </div>
        <div class="form-group">
            <label class="text-light">Score</label>
            <input required value="${hotel.score}" placeholder="Score" class="form-control" type="text" id="score" name="score"/>
        </div>
        <div class="form-group">
            <button class="form-control btn btn-primary" type="submit">Save</button>
        </div>
        <input class="d-none" name="id" value="${hotel.id}"/>
    </form>
</div>
</body>
</html>