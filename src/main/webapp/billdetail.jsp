
<%--
  Created by IntelliJ IDEA.
  User: TechCare
  Date: 19/06/2023
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<?php
session_start();
?>
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
<jsp:include page="/layout/header.jsp"/>
<!-- Messenger Plugin chat Code -->
<div id="fb-root"></div>
<div class="page-heading about-heading header-text" style="background-image: url(assets/images/headingBack.jpg);">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="text-content">
                    <h4>Welcome everyone with</h4>
                    <h2>BILL</h2>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="container mt-5">
    <div class="d-flex justify-content-center row">
        <div class="col-md-8">
            <div class="p-3 bg-white rounded">
                <div class="row">
                    <div class="col-md-6 text-right mt-3">
                        <h4 class="text-danger mb-0">BILL DETAIL</h4>
                    </div>
                </div>
                <div class="mt-3">
                    <div class="table-responsive">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Image</th>
                                <th>Name</th>
                                <th>Size</th>
                                <th>Quantity</th>
                                <th>total</th>
                            </tr>
                            </thead>
                            <!-- data -->
                            <tbody>
                                <c:forEach items="${requestScope.detail}" var="d">
                                    <tr>
                                        <td><a  href="product?action=productDetail&product_id=${d.product.getId()}"><img
                                                src="${d.product.getImage()}" alt="" style="height: 70px;width: 70px"></a></td>
                                        <td><a href="product?action=productDetail&product_id=${i.product.getId()}">${d.product.getName()}</a>
                                        </td>
                                        <td>${d.getSize()}</td>
                                        <td>${d.getQuantity()}</td>
                                        <td>${d.getBillDetailPrice(d.getPrice())}</td>
                                     </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Bill -->
<jsp:include page="/layout/footer.jsp"/>
<!-- Bootstrap core JavaScript -->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>


<!-- Additional Scripts -->
<script src="assets/js/custom.js"></script>
<script src="assets/js/owl.js"></script>

</div>

</body>

</html>
