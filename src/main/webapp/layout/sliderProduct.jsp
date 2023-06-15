<%--
  Created by IntelliJ IDEA.
  User: TechCare
  Date: 15/06/2023
  Time: 09:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %><section class="product_section womens_product bottom">
    <div class="container">
        <div class="row">
            <div class="col-12">
                <div class="section_title">
                    <h2>Sản phẩm của chúng tôi</h2>
                    <p>Sản phẩm dành cho bạn</p>
                </div>
            </div>
        </div>
        <div class="product_area">
            <div class="row">
                <div class="product_carousel product_three_column4 owl-carousel">
                    <c:forEach items="${requestScope.top10}" var="top10">
                        <div class="col-lg-3">
                            <div class="single_product">
                                <div class="product_thumb">
                                    <a class="primary_img"
                                       href="product?action=productDetail&product_id=${top10.getProduct_id()}"><img
                                            src="${top10.getImg()}" width="10px;" alt=""></a>
                                    <div class="quick_button">
                                        <a href="product?action=productDetail&product_id=${top10.getProduct_id()}"
                                           title="quick_view">Xem sản phẩm</a>
                                    </div>
                                </div>
                                <div class="product_content">
                                    <h3>
                                        <a href="product?action=productDetail&product_id=${top10.getProduct_id()}">${top10.getProduct_name()}</a>
                                    </h3>
                                    <span class="current_price">${top10.getProductPrice(top10.getProduct_price())}</span>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</section>
