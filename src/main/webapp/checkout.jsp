<%--
  Created by IntelliJ IDEA.
  User: TechCare
  Date: 18/06/2023
  Time: 00:01
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

    <title>trungd3pn.xyz</title>

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
                    <h2>SHOPPING CART</h2>
                </div>
            </div>
        </div>
    </div>
</div>
<!--Shoppingcart-->
<div class="container">
    <form class=" d-flex justify-content-md-between mt-5" action="/checkout?action=payment" method="post">
    <div class="row-cols-2" style="width: 50%;">
        <div class="col" style="width: 100%; margin: 0;">
            <div class="section-heading">
                <h2 class="text-center">THÔNG TIN KHÁCH HÀNG</h2>
            </div>
            <div class="col-12 mb-3">
                <div class="mb-1">Tên khách hàng <label style="color: red;">*</label></div>
                <input class="col-12" readonly="" value="${sessionScope.user.getFirst_name()} ${sessionScope.user.getLast_name()}" type="text" placeholder="">
            </div>
            <div class="col-12 mb-3">
                <div class="mb-1">Email <label style="color: red;">*</label></div>
                <input class="col-12" readonly="" value="${sessionScope.user.getUser_email()}" type="text" placeholder="">
            </div>
            <div class="col-12 mb-3">
                <div class="mb-1">Địa chỉ<label style="color: red;">*</label></div>
                <input class="col-12" required name="address"  value="" type="text" placeholder="">
            </div>
            <div class="col-12 mb-3">
                <div class="mb-1">Số Điện Thoại<label style="color: red;">*</label></div>
                <input class="col-12" required name="phone" value="" type="tel" placeholder="">
            </div>

        </div>
    </div>
    <div class="row-cols-2" style="width: 45%;">
        <div class="col" style="width: 100%;">
            <div class="section-heading">
                <h2 class="text-center">CHI TIẾT HOÁ ĐƠN</h2>
            </div>
            <table class="table table-hover" style="text-align:center;">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Size</th>
                    <th>Quantity</th>
                    <th>Total</th>
                </tr>
                </thead>
                <tbody id="datarow">
                <c:set var="o" value="${sessionScope.cart}"/>
                <c:forEach items="${o.items}" var="i">
                <tr>
                    <td>${i.product.getName()}</td>
                    <td>${i.getSize()}</td>
                    <td> ${i.getQuantity()}</td>
                    <td>${i.product.getProductPrice(i.product.getPrice() * i.getQuantity())}</td>
                </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                    <th colspan="4" class="table-active">TOTAL</th>
                </tr>
                <tr>
                    <th colspan="4" class="table-dark text-center">${total}</th>
                </tr>
                </tfoot>

            </table>
            <div class="payment_method">
                <div class="panel-default">
                    <input id="payment_defult" value="momo" name="payment_method" type="radio"
                           data-target="createp_account" />
                    <label for="payment_defult" data-toggle="collapse" data-target="#collapsedefult"
                           aria-controls="collapsedefult">Momo <img src="assets/img/icon/papyel.png" alt=""></label>
                </div>
                <div class="panel-default">
                    <input id="payment_defult" value="cod" name="payment_method" type="radio"
                           data-target="createp_account" />
                    <label for="payment_defult" data-toggle="collapse" data-target="#collapsedefult"
                           aria-controls="collapsedefult">Thanh toán khi nhận hàng <img src="assets/img/icon/papyel.png" alt=""></label>
                </div>
                <div style="color: red; align-content: center;">
                    ${requestScope.error_payment}
                </div>
                <form action="" method="post" >
                    <div class="modal-footer justify-content-center mt-2" style="margin: 0; padding: 0;border: none;">
                        <button  type="submit" style="margin: 0;" class="btn btn-outline-dark" name="bnt_remove">ĐẶT HÀNG</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    </form>

</div>
<jsp:include page="layout/footer.jsp"/>
</body>
<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/11.7.5/sweetalert2.all.min.js" integrity="sha512-2JsZvEefv9GpLmJNnSW3w/hYlXEdvCCfDc+Rv7ExMFHV9JNlJ2jaM+uVVlCI1MAQMkUG8K83LhsHYx1Fr2+MuA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

<c:if test="${requestScope.message !=null}">
    <script>
        window.onload = function () {
            Swal.fire({
                position: 'top-end',
                icon: 'success',
                title: 'Đặt hàng thành công!',
                showConfirmButton: false,
                timer: 1500
            })
        };
    </script>
</c:if>
<!-- Bootstrap core JavaScript -->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>


<!-- Additional Scripts -->
<script src="assets/js/custom.js"></script>
<script src="assets/js/owl.js"></script>
</html>