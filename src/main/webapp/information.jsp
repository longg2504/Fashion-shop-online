<%--
  Created by IntelliJ IDEA.
  User: TechCare
  Date: 19/06/2023
  Time: 15:24
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
<!-- Header -->
<jsp:include page="/layout/header.jsp"/>

<!-- Page Content -->
<div class="page-heading about-heading header-text" style="background-image: url(assets/images/headingBack.jpg);">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="text-content">
                    <h4>Welcome everyone with</h4>
                    <h2>Information</h2>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="products">
    <div class="container">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <div class="section-heading">
                        <h2>Information</h2>
                    </div>
                </div>
                <div class="col-md-8">
                    <div class="contact-form">
                        <form method="post">
                            <div class="row">
                                <div class="col-lg-12 col-md-12 col-sm-12">
                                    <fieldset>
                                        <input name="Use_Name" type="text" class="form-control"
                                               value="${sessionScope.user.getLast_name()}" readonly>
                                    </fieldset>
                                </div>
                                <div class="col-lg-12 col-md-12 col-sm-12">
                                    <fieldset>
                                        <input name="Full_Name" type="text" class="form-control"
                                               placeholder="Full Name" value="${sessionScope.user.getFirst_name()} ${sessionScope.user.getLast_name()}">
                                    </fieldset>
                                </div>
                                <div class="col-lg-12 col-md-12 col-sm-12">
                                    <fieldset>
                                        <input name="Email" type="text" class="form-control"
                                               placeholder="E-Mail Address" value="${sessionScope.user.getUser_email()}">
                                    </fieldset>
                                </div>
                                <div class="col-lg-12">
                                    <fieldset>
                                        <button type="submit" name="bnt_update"
                                                class="filled-button">Update</button>
                                    </fieldset>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="col-md-4">
                    <c:choose>
                        <c:when test="${sessionScope.user.getImage()!=null}">
                            <div class="imgHover">
                                <img src="" class="avatar">
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="imgHover ">
                                <img src="assets/images/default-avatar.jpg" class="avatar">
                            </div>
                        </c:otherwise>
                    </c:choose>
                    <h5 class="text-center" style="margin-top: 15px;">${sessionScope.user.getLast_name()}</h5>
                    <br>
                    <form action="" method="post" enctype="multipart/form-data">
                        <input type="file" class="form-control" id="fileToUpload" name="fileToUpload">
                        <input type="submit" value="Upload" name="Upload" class="form-control">
                    </form>
                </div>
            </div>
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