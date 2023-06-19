<%--
  Created by IntelliJ IDEA.
  User: TechCare
  Date: 14/06/2023
  Time: 14:16
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
<!-- Header -->
<header class="">
    <nav class="navbar navbar-expand-lg">
        <div class="container">
            <div class="collapse navbar-collapse" id="navbarResponsive">
                <ul class="navbar-nav ml-left">
                    <li class="nav-item">
                        <a class="nav-link" href="home">Home
                        </a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="home" role="button" id="dropdownMenuLink" data-bs-toggle="dropdown" aria-expanded="false">Products</a>
                        <div class="dropdown-menu" >
                            <a class="dropdown-item" href="/products">ALL</a>
                            <c:forEach items='${requestScope["ListAllCategory"]}' var="cate">
                                <a  class="dropdown-item" href="/products?category_id=${cate.getId()}">${cate.getName()}</a>
                            </c:forEach>
                        </div>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/cart">Cart</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="contact" role="button"  data-bs-toggle="dropdown" aria-expanded="false">More</a>
                        <div class="dropdown-menu">
                            <a class="dropdown-item" href="contact">Contact</a>
                            <a class="dropdown-item" href="about-us">About us</a>
                        </div>
                    </li>

                    <c:if test="${sessionScope.user != null}">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle"  drole="button"  data-bs-toggle="dropdown" aria-expanded="false">${sessionScope.user.getLast_name()}</a>
                            <div class="dropdown-menu">
                                <a class="dropdown-item" href="information.jsp">Information</a>
                                <a class="dropdown-item" href="user?action=showHistory">History</a>
                                <a class="dropdown-item" href="change-passwork.jsp">Change password</a>
                                <hr class="dropdown-divider">
                                <a class="dropdown-item" href="/user?action=logout">Sign-out</a>
                            </div>
                        </li>
                    </c:if>
                    <c:if test="${sessionScope.user == null}">
                        <li class="nav-item"><a class="nav-link" href="/home/login.jsp">Sign-In</a></li>
                    </c:if>
                </ul>
            </div>
            <a class="navbar-brand" href="index">
                <h2>Clothers<em> Store</em></h2>
            </a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
        </div>
    </nav>
</header>
</body>
</html>