<%--
  Created by IntelliJ IDEA.
  User: TechCare
  Date: 15/06/2023
  Time: 13:44
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="./assets/images/male-clothes.ico">

    <link href="https://fonts.googleapis.com/css?family=Poppins:100,200,300,400,500,600,700,800,900&display=swap" rel="stylesheet">

    <title>romromshop.com</title>

    <!-- Bootstrap core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Additional CSS Files -->
    <link rel="stylesheet" href="assets/css/fontawesome.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="stylesheet" href="assets/css/owl.css">

</head>

<body>

<!-- Header -->
<jsp:include page="/layout/header.jsp"/>

<!-- Page Content -->
<div class="page-heading about-heading header-text" style="background-image: url(assets/images/headingBack.jpg);">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="text-content">
                    <h4>Welcome everyone with</h4>
                    <h2>Products</h2>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="products">
    <div class="container">
        <div class="section-heading">
            <h2>Products</h2>
        </div>
            <div class="row flex-wrap m-auto">
                <c:forEach items='${requestScope["ProductData"]}' var="p">
                    <div class="col-4" style="padding: 5px;">
                        <div class="card product-item">
                            <a style="text-align: center" href="/products?action=product-detail&product_id=${p.getId()}">
                                <img style="width: 300px"src="${p.getImage()}" style="width: 100%; height: 250px">
                            </a>
                            <div class="down-content text-center" >
                                <a href="/products?action=products-detail&product_id=${p.getId()}" >
                                    <h4>${p.getName()}</h4>
                                </a>
                                <strong style="font-size: 26px"  class="text-danger" href="/products?action=product-detail&product_id=${p.getId()}">${(p.getProductPrice(p.getPrice()))}</strong>
                                <form >
                                    <a href="/products?action=product-detail&product_id=${p.getId()}" type="submit" class="btn btn-outline-dark w-100">
                                        <i class="fa fa-shopping-cart"></i>Add to Cart>
                                    </a>
                                </form>
                            </div>

                        </div>
                    </div>
                </c:forEach>
            </div>
            <nav aria-label="Page navigation">
                <c:set var="page" value='${requestScope["page"]}'/>
                <ul class ="pagination justify-content-center">
                    <c:forEach begin="${1}" end='${requestScope["num"]}' var="i">
                        <li class="${i==page?"current":""} page-item"><a class="page-link" href="products?page=${i}">${i}</a></li>
                    </c:forEach>
                </ul>
            </nav>
        </div>
    </div>
</div>
<jsp:include page="/layout/footer.jsp"/>
<!-- Bootstrap core JavaScript -->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>


<!-- Additional Scripts -->
<script src="assets/js/custom.js"></script>
<script src="assets/js/owl.js"></script>
</body>

</html>