<%--
  Created by IntelliJ IDEA.
  User: TechCare
  Date: 19/06/2023
  Time: 12:27
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html class="no-js" lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="Website demo buy clothes online">
  <meta name="author" content="Trung, Dang Quang">
  <meta name="image" content=" ./assets/images/slider-image-3.jpg">

  <link rel="icon" href="./assets/images/male-clothes.ico">
  <link href="https://fonts.googleapis.com/css?family=Poppins:100,200,300,400,500,600,700,800,900&display=swap" rel="stylesheet">

  <title>trungd3pn.xyz</title>

  <!-- Bootstrap core CSS -->
  <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

  <!-- Additional CSS Files -->
  <link rel="stylesheet" href="assets/css/fontawesome.css">
  <link rel="stylesheet" href="assets/css/style.css">
  <link rel="stylesheet" href="assets/css/owl.css">

</head>

<body>
<jsp:include page="layout/header.jsp"/>
<div class="page-heading about-heading header-text" style="background-image: url(assets/images/headingBack.jpg);">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="text-content">
                    <h4>Welcome everyone with</h4>
                    <h2>ROMROM SHOP</h2>
                </div>
            </div>
        </div>
    </div>
</div>
<!--error section area start-->

  <div class="container">
    <div class="row">
      <div class="col-12">
          <h2 class="text-center text-dark">Thanh toán hoá đơn: #${bill.getBill_id()}!</h2>
          <h4 class="text-center">Số tiền thanh toán: ${total} VND</h4>
          <h4 class="text-center">Vui lòng viết ghi chú: </h4>
          <h5 class="text-center" style="color: red">" Mã đơn hàng #${bill.getBill_id()} "</h5>
          <h6 class="text-center">Chúng tôi sẽ kiểm tra và thông báo đến bạn khi thanh toán hoàn tất !</h6>
          <div class="text-center">
            <img style="align-content: center; margin-top: 10px" height="300px" width="300px"
                 src="/assets/images/fb4629baa74f76112f5e.jpg">
          </div>
          <div class="text-center mt-3">
              <a class="btn btn-dark" style="text-align: center" href="home">Tiếp tục mua hàng</a>
          </div>
        </div>
      </div>
  </div>
<!--error section area end-->
<!--footer area start-->
<jsp:include page="layout/footer.jsp"/>
<!--footer area end-->
<!-- JS
============================================ -->


<!--map js code here-->
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAdWLY_Y6FL7QGW5vcO3zajUEsrKfQPNzI"></script>
<script src="https://www.google.com/jsapi"></script>
<script src="assets/js/map.js"></script>


<!-- Plugins JS -->
<script src="assets/js/plugins.js"></script>

<!-- Main JS -->
<script src="assets/js/main.js"></script>


<!-- Bootstrap core JavaScript -->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>


<!-- Additional Scripts -->
<script src="assets/js/custom.js"></script>
<script src="assets/js/owl.js"></script>


</body>

</html>
