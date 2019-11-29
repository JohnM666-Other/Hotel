<!DOCTYPE HTML>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
    <head>
        <title>Getting Started: Serving Web Content</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link href="/css/app.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="../js/index.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    </head>
    <body>
        <h1 class="text-light text-center">Hotel &laquo;${hotel.name}&raquo;</h1>
        <div class="container">
            <div class="my-2 light-background rounded-p">
                <img src="/res/hotel.png" class="float-left img-invert" alt="hotel icon">
                <div class="p-2">
                    <p>
                        <c:forEach begin="1" end="${hotel.stars}">
                            <i class="star"></i>
                        </c:forEach>
                    </p>
                    <p class="text-light">Country: <c:out value="${hotel.country}"/></p>
                    <p class="text-light">City: <c:out value="${hotel.city}"/></p>
                    <p class="text-light">Url: <a href="${hotel.url}">${hotel.url}</a></p>
                    <p class="text-light"><c:out value="${hotel.description}"/></p>
                    <div class="clearfix"></div>
                </div>
            </div>
            <div class="feedback-list my-2 py-2 light-background rounded-p-top">
                <h4 class="px-2 text-light border-bottom border-light">Feedback
                    <i class="float-right score-circle">
                        <fmt:formatNumber value="${hotel.avgScore}" type="number" maxFractionDigits="1"/>
                    </i>
                </h4>
                <div class="clearfix"></div>
                <c:choose>
                    <c:when test="${fn:length(feedbacks)>0}">
                        <c:forEach items="${feedbacks}" var="item">
                            <div class="m-0 p-2 d-flex flex-row align-items-center">
                                <div class="d-flex flex-column">
                                    <h6 class="text-light">
                                            ${item.userEntity.firstname}
                                            ${item.userEntity.secondname}
                                        (Age: ${item.customerAge})
                                        <fmt:formatDate value="${item.visitDate}" pattern="yyyy-MM-dd"/>
                                        <c:choose>
                                            <c:when test="${isAdmin || item.userEntity.email == username}">
                                                <a href="/api/feedbacks/user-delete/${hotel.id}/${item.id}">Delete</a>
                                            </c:when>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${item.userEntity.email == username}">
                                                <a href="/hotel/${hotel.id}/${item.id}">Edit</a>
                                            </c:when>
                                        </c:choose>
                                    </h6>
                                    <p class="text-light">${item.text}</p>
                                </div>
                                <c:choose>
                                    <c:when test="${item.score>3}">
                                        <h3 class="ml-auto mr-2 d-inline-block float-right color-green">${item.score}</h3>
                                    </c:when>
                                    <c:otherwise>
                                        <h3 class="ml-auto mr-2 d-inline-block float-right color-red">${item.score}</h3>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="text-light m-2 p-2 text-center">No comments yet</div>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${not empty username}">
                        <form class="p-4 border" method="POST" action="/api/feedbacks/send">
                            <div class="form-group">
                                <label class="text-light">Score</label>
                                <input type="text" value="${edit_score}" class="form-control" id="score" name="score"/>
                            </div>
                            <div class="form-group">
                                <label class="text-light">Message</label>
                                <textarea class="form-control" id="message" name="message">${edit_message}</textarea>
                            </div>
                            <div class="d-none">
                                <input name="hotel" value="${hotel.id}"/>
                                <input name="username" value="${username}"/>
                                <c:choose>
                                    <c:when test="${not empty edit_id}">
                                        <input name="feedback" value="${edit_id}"/>
                                    </c:when>
                                </c:choose>
                            </div>
                            <div class="form-group">
                                <button class="w-100 btn btn-primary" type="submit">Send</button>
                            </div>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <div class="text-light m-2 p-2 text-center">Please login to write a comment</div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </body>
</html>