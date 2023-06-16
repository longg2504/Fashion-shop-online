package controller.home;
import dao.ProductDAO;
import model.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
    @WebServlet(name = "CartServlet", urlPatterns = "/cart")
    public class CartServlet extends HttpServlet {
        private ProductDAO productDAO;
        @Override
        public void init(ServletConfig config) throws ServletException {
            productDAO = new ProductDAO();
        }
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            String action = request.getParameter("action");
            if (action == null) {
                action = "";
            }
            switch (action) {
                case "addCart":
                    try {
                        addCart(request, response);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                default:
                    try {
                        showCart(request, response);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }
        }
        private void showCart(HttpServletRequest request, HttpServletResponse response) throws Exception {
            List<Category> categories = productDAO.getCategory();
            request.setAttribute("ListAllCategory",categories);
            request.getRequestDispatcher("/cart.jsp").forward(request, response);
        }
        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            String action = request.getParameter("action");
            if (action == null) {
                action = "";
            }
            switch (action) {
                case "addCart":
                    try {
                        addCart(request, response);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
//            case "deleteProduct":
//                deleteProduct(request, response);
//                break;
                default:
            }
        }
        private void addCart(HttpServletRequest request, HttpServletResponse response) throws Exception {
            HttpSession session = request.getSession();
            Object usercheck = session.getAttribute("user");
            if(usercheck == null){
               response.sendRedirect("/user");
            }else{
                Cart cart = null;
                Object o = session.getAttribute("cart");
                if (o != null) {
                    cart = (Cart) o;
                } else {
                    cart = new Cart();
                }
                int id = Integer.parseInt(request.getParameter("product_id"));
                String size = request.getParameter("size");
                String quantity = request.getParameter("quantity");
                if (quantity == "") {
                    request.setAttribute("error", "Số lượng không đúng! Vui lòng chọn lại...");
                    showProductDetail(request, response);
                } else {
                    try {
                        int sl = Integer.parseInt(quantity);
                        Product product = productDAO.getProductByID(id);
                        Item item = new Item(product, sl, size);
                        cart.addItem(item);
                    } catch (Exception e) {
                    }
                    List<Item> list = cart.getItems();
                    session.setAttribute("cart", cart);
                    session.setAttribute("total", cart.getTotalMoney());
                    session.setAttribute("size", list.size());
                    request.setAttribute("message", "Thêm sản phẩm thành công");
                    showProductDetail(request, response);
            }

            }
        }
        private void showProductDetail(HttpServletRequest request, HttpServletResponse response) throws
                Exception {
            int id = Integer.parseInt(request.getParameter("product_id"));
            List<ProductSize> sizeList = productDAO.getSizeByID(id);
            Product product = productDAO.getProductByID(id);
            if (product == null) {
                request.getRequestDispatcher("/404.jsp").forward(request, response);
            }
            else{
                int category_id = product.getCategory().getId();

                List<Product> productByCategory = productDAO.getProductByCategory(category_id);
                request.setAttribute("ProductData", product);
                request.setAttribute("SizeData", sizeList);
                request.setAttribute("ProductByCategory", productByCategory);
                request.getRequestDispatcher("/product-detail.jsp").forward(request, response);
            }

        }
    }
