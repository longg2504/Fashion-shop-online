package controller.home;
import dao.ProductDAO;
import model.*;
import utils.CurrencyFormat;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

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
                    addCart(request, response);
                    break;
                case "increase" :
                case "decrease":
                    reload(request,response);
                    break;
                default:
                    showCart(request, response);
                    break;
            }
        }

        private void reload(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            HttpSession session = request.getSession();
            Object usercheck = session.getAttribute("user");
            if(usercheck == null){
                response.sendRedirect("/user");
            }else{
                Cart cart = (Cart) session.getAttribute("cart");
                if(cart == null){
                    cart = new Cart();
                }
                int id = Integer.parseInt(request.getParameter("product_id"));
                String size = request.getParameter("sizeName");
                int quantity = Integer.parseInt(request.getParameter("quantity")) ;
                if(quantity < cart.getItemByIdAndSize(id,size).getQuantity()) {
                    quantity = -1;
                }
                else{
                    quantity = 1;
                }
                if (Objects.equals(quantity, "")) {
                    request.setAttribute("error", "Số lượng không đúng! Vui lòng chọn lại...");
                    showProductDetail(request, response);
                } else {
                    try {
                        int sl = quantity;
                        Product product = productDAO.getProductByID(id);
                        Item item = new Item(product, sl, size);
                        cart.addItem(item);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    List<Item> list = cart.getItems();
                    session.setAttribute("cart", cart);
                    session.setAttribute("total", CurrencyFormat.covertPriceToString(cart.getTotalMoney(cart.getItems())));
                    session.setAttribute("size", list.size());
                    request.setAttribute("message", "Thêm sản phẩm thành công");
                    showProductDetail(request, response);
                }
            }
        }


        private void showCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                    addCart(request, response);
                    break;
            case "deleteProduct":
                deleteProduct(request, response);
                break;
                default:
                    break;
            }
        }
        private void addCart(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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
                    Product product = productDAO.getProductByID(id);
                    int category_id = product.getCategory().getId();
                    List<Product> productByCategory = productDAO.getProductByCategory(category_id);
                    List<Item> list = cart.getItems();
                    session.setAttribute("ProductByCategory",productByCategory);
                    session.setAttribute("cart", cart);
                    session.setAttribute("total", CurrencyFormat.covertPriceToString(cart.getTotalMoney(cart.getItems())));
                    session.setAttribute("size", list.size());
                    request.setAttribute("message", "Thêm sản phẩm thành công");
                    showProductDetail(request, response);
            }

            }
        }
        private void showProductDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
//                request.getRequestDispatcher("/cart.jsp").forward(request, response);
                response.sendRedirect("/cart");
            }

        }
    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(true);
        Cart cart = null;
        Object o = session.getAttribute("cart");
        if (o != null) {
            cart = (Cart) o;
        } else {
            cart = new Cart();
        }
        int product_id = Integer.parseInt(request.getParameter("txtId"));
        cart.removeItem(product_id);
        List<Item> list = cart.getItems();
        session.setAttribute("cart", cart);
        session.setAttribute("total",CurrencyFormat.covertPriceToString(cart.getTotalMoney(cart.getItems())));
        session.setAttribute("size", list.size());
        request.setAttribute("message", "Xóa sản phẩm thành công");
        request.getRequestDispatcher("/cart.jsp").forward(request, response);
    }
    }
