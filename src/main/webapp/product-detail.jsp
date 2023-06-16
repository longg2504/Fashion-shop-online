<%--
  Created by IntelliJ IDEA.
  User: TechCare
  Date: 15/06/2023
  Time: 17:38
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

    <title>romromhop.com</title>

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
                    <h2>Product Details</h2>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="products">
    <div class="container">
        <div class="row d-flex justify-content-center">
            <div class="col-md-4 col-xs-12">
                <div style="border: 0px solid gray;box-shadow: 7px 5px 17px 5px gray;border-radius: 12px;">
                    <img style="border-radius: 5px" src="${ProductData.getImage()}" alt="" class="img-fluid wc-image">
                </div>
            </div>
            <div class="col-md-8 col-xs-12">
                <form action="/cart?action=addCart&product_id=${ProductData.getId()}" method="post" class="form">
                    <div class="mt-4 mb-4"><h2>${ProductData.getName()}</h2></div>
                    <div class="mt-4 mb-4">
                        <p class="lead">
                            <strong style="font-size: 30px" class="text-primary">${ProductData.getProductPrice(ProductData.getPrice())}</strong>
                        </p>
                    </div>
                    <div class="mt-4 mb-4">
                    <p class="lead" style="text-align: justify;">
                        ${ProductData.getDescrible()}
                    </p>
                    </div>
                    <div class="row justify-content-start">
                        <div class="col-sm-4">
                            <label class="control-label mb-2">Size</label>
                            <div class="form-group">
                                <select class="form-control" name="size">
                                    <c:forEach items="${SizeData}" var="s">
                                        <option value="${s.getSize()}">${s.getSize()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <label class="control-label mb-2">Quantity</label>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <input class="form-control" min="1" max="${ProductData.getQuantity()}" name="quantity" type="number" value="1" style="text-align: center;padding: 6px 10px;width: 150px">
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                    <div class="row justify-content-center mt-5">
                        <div class="col-sm-4">
                            <button type="submit" class="btn btn-outline-dark w-100" name="btn_submit">
                                <i class="fa fa-shopping-cart"></i> Add to Cart
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="latest-products">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="section-heading">
                    <h2>Similar Products</h2>
                    <a href="products?category_id=${ProductData.category.getId()}">view more <i class="fa fa-angle-right"></i></a>
                </div>
            </div>
            <jsp:include page="/layout/similarProducts.jsp"/>
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