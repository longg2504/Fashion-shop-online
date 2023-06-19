<%--
  Created by IntelliJ IDEA.
  User: TechCare
  Date: 16/06/2023
  Time: 10:34
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
                    <h2>SHOPPING CART</h2>
                </div>
            </div>
        </div>
    </div>
</div>
<!--Shoppingcart-->
</br>
<div class="container">
    <form id="frmDelete" action="cart?action=deleteProduct" method="post">
        <input id="txtId" name="txtId" type="hidden"/>
    </form>
        <div class="row">
            <div class="col col-md-12">
                <div class="section-heading">
                    <h2>Shopping cart</h2>
                </div>

                <table class="table table-hover" style="text-align:center;">
                    <thead>
                    <tr>
                        <th>Img</th>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Size</th>
                        <th>Quantity</th>
                        <th>Total</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody id="datarow">
                    <c:set var="o" value="${cart}"/>
                    <c:forEach items="${o.items}" var="i">
                        <tr>
                            <td>
                                <div class="col-sm-4 col-xs-6"
                                     style="max-width: 10rem;display: block;margin-left: auto;margin-right: auto;">
                                    <a href="products?action=product-detail&product_id=${i.product.getId()}">
                                        <img src="${i.product.getImage()}" alt="" class="img-fluid">
                                    </a>
                                </div>
                            </td>
                            <td>${i.product.getName()}</td>
                            <td>${i.product.getProductPrice(i.product.getPrice())}</td>
                            <td id="sizeName">${i.getSize()}</td>
                            <td style="max-width: 5rem;">
                                <input onchange="handleQuantityChange(${i.product.getId()},'${i.getSize()}',${i.getQuantity()},this)"
                                       id="test1" type="number" min="0" max="50" class="form-control" name="quantity"
                                       value="${i.getQuantity()}">
                            </td>
                            <td>${i.product.getProductPrice(i.product.getPrice() * i.getQuantity())}</td>
                            <td>
                                    <a href="javascript:;"
                                       onclick="handleDelete('${i.product.getProduct_id()}')" class="btn btn-outline-dark">
                                        <i class="fa fa-trash"></i>
                                    </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                    <tfoot>
                    <tr>
                        <th colspan="5" class="table-active">TOTAL</th>
                        <th class="table-dark">${total}</th>
                        <td class="table-dark"></td>
                    </tr>
                    </tfoot>
                </table>
            </div>
        </div>
        </br>
        <form action="" method="post">
            <div class="modal-footer">
                <a href="checkout.jsp" type="button" class="btn btn-outline-dark"
                   name="bnt_remove">Thanh Toan</a>
            </div>
        </form>
</div>
<%--<div class="latest-products">--%>
<%--    <div class="container">--%>
<%--        <div class="row">--%>
<%--            <div class="col-md-12">--%>
<%--                <div class="section-heading">--%>
<%--                    <h2>Similar Products</h2>--%>
<%--                    <a href="products.jsp">view more <i class="fa fa-angle-right"></i></a>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--            <jsp:include page="/layout/similarProducts.jsp"/>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</div>--%>
<jsp:include page="/layout/footer.jsp"/>
<!-- Bootstrap core JavaScript -->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>


<!-- Additional Scripts -->
<script src="assets/js/custom.js"></script>
<script src="assets/js/owl.js"></script>
<script>
    function handleQuantityChange(idProduct, size, quantityOld, element) {
        let value = element.value;
        let action = "";
        let sizeName = document.getElementById("sizeName").innerHTML;
        if (quantityOld < value) {
            action = 'increase';
        } else {
            action = 'decrease';
        }
        if (action != "") {
            let url = '/cart?action=' + action + '&product_id=' + idProduct + '&sizeName=' + size + '&quantity=' + value;
            window.location.assign(url);
        }
    }
</script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
<script>
    function handleDelete(id) {
        document.getElementById("txtId").value = id;
        Swal.fire({
            title: 'Bạn có muốn xóa hay không?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes!'
        }).then((result) => {
            if (result.isConfirmed) {
                document.getElementById("frmDelete").submit();
            }
        })
    }
</script>
</body>
</html>