<%--
  Created by IntelliJ IDEA.
  User: TechCare
  Date: 19/06/2023
  Time: 15:55
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
    <link href="https://fonts.googleapis.com/css?family=Poppins:100,200,300,400,500,600,700,800,900&display=swap"
          rel="stylesheet">

    <title>trungd3pn.xyz</title>

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
<div class="page-heading about-heading header-text" style="background-image: url(assets/images/headingBack.jpg);">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="text-content">
                    <h4>Welcome everyone with</h4>
                    <h2>HISTORY BILL</h2>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Page Content -->
<!-- Bill -->
<div class="container mt-5">
    <div class="d-flex justify-content-center row">
        <div class="col-md-8">
            <div class="p-3 bg-white rounded">
                <div class="row">
                    <div class="col-md-6">
                        <h1 class="text-uppercase">Invoice</h1>
                        <div class="billed"><span class="font-weight-bold text-uppercase">UserName:${sessionScope.user.getLast_name()}</span><span
                                class="ml-1"> <?=$Use_Name?> </span></div>
                        <div class="billed"><span class="font-weight-bold text-uppercase">User ID:${sessionScope.user.getUser_id()}</span><span
                                class="ml-1"> <?=$Use_ID?> </span></div>

                    </div>
                    <div class="col-md-6 text-right mt-3">
                        <h4 class="text-danger mb-0">BILL HISTORY</h4>
                    </div>
                </div>
                <div class="mt-3">
                    <div class="table-responsive">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Mã đơn hàng</th>
                                <th>Ngày khởi tạo</th>
                                <th>Hình thức GD</th>
                                <th>Địa chỉ</th>
                                <th>Tổng đơn</th>
                                <th>Actions</th>
                            </tr>
                            </thead>
                            <!-- data -->
                            <tbody>
                            <c:forEach items="${requestScope.bill}" var="b">
                                <tr>
                                    <td>${b.getBill_id()}</td>
                                    <td>${b.getCreateDate()}</td>
                                    <td><span class="success">${b.getPayment()}</span></td>
                                    <td>${b.getAddress()}</td>
                                    <td>${b.getBillTotal(b.getTotal())}</td>
                                    <td><a href="user?action=showDetail&bill_id=${b.getBill_id()}" class="view">view</a></td>
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

<!-- Your Plugin chat code -->
</div>
</body>
</html>