<%--
  Created by IntelliJ IDEA.
  User: TechCare
  Date: 15/06/2023
  Time: 23:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row flex-wrap m-auto">
    <c:forEach items='${requestScope["ProductByCategory"]}' begin="${p.getId()+1}" end="${p.getId()+3}" var="p">
        <div class="col-4" style="padding: 5px;">
            <div class="card product-item">
                <a style="text-align: center" href="/products?action=product-detail&product_id=${p.getId()}">
                    <img style="width: 300px"src="${p.getImage()}" style="width: 100%; height: 250px">
                </a>
                <div class="down-content text-center" >
                    <a href="/products?action=product-detail&product_id=${p.getId()}" >
                        <h4>${p.getName()}</h4>
                    </a>
                    <strong style="font-size: 26px"  class="text-danger" href="products?action=product-detail&product_id=${p.getId()}">${(p.getProductPrice(p.getPrice()))}</strong>
                    <form>
                        <a type="submit" href="/products?action=product-detail&product_id=${p.getId()}"class="btn btn-outline-dark w-100">
                            <i class="fa fa-shopping-cart"></i>View Detail
                        </a>
                    </form>
                </div>

            </div>`
        </div>
    </c:forEach>
</div>