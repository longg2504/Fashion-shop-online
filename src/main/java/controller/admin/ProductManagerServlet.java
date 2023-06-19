package controller.admin;

import dao.AdminDAO;
import dao.ProductDAO;
import model.Category;
import model.Product;
import model.ProductSize;
import model.User;
import utils.ValidateUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ProductManagerServlet", urlPatterns = "/productManager")
public class ProductManagerServlet extends HttpServlet {
    private AdminDAO adminDAO;
    private ProductDAO productDAO;


    @Override
    public void init() throws ServletException {
        adminDAO = new AdminDAO();
        productDAO = new ProductDAO();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "productLow":
                showProductLow(request, response);
                break;
            case "insert":
                showInsertProduct(request, response);
                break;
            default:
                showProduct(request, response);
                break;
        }
    }

    private void showProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if (user.getRole().equalsIgnoreCase("true")) {
                List<Product> product = productDAO.getProduct();
                List<ProductSize> size = productDAO.getSize();
                List<Category> category = productDAO.getCategory();
                request.setAttribute("CategoryData", category);
                request.setAttribute("ProductData", product);
                request.setAttribute("SizeData", size);
                request.getRequestDispatcher("/admin/product.jsp").forward(request, response);
            } else {
                response.sendRedirect("user?action=login");
            }
        } catch (Exception e) {
            response.sendRedirect("404.jsp");
        }
    }

    private void showInsertProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            List<Category> categories = productDAO.getCategory();
            request.setAttribute("CategoryData", categories);
            request.getRequestDispatcher("/admin/insertProduct.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendRedirect("404.jsp");
        }

    }

    private void showProductLow(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            List<Product> products = adminDAO.getProductLow();
            List<ProductSize> sizes = productDAO.getSize();
            List<Category> categories = productDAO.getCategory();
            request.setAttribute("CategoryData", categories);
            request.setAttribute("ProductLow", products);
            request.setAttribute("SizeData", sizes);
            request.getRequestDispatcher("/admin/productLow.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendRedirect("404.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "insertCategory":
                insertCategory(request, response);
                break;
//            case "insertProduct":
//                insertProduct(request, response);
//                break;
//            case "updateProduct":
//                updateProduct(request,response);
//                break;

        }
    }

//    private void insertProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        List<String> errors = new ArrayList<>();
//        Product product = new Product();
//        try {
//            int product_id = Integer.parseInt(request.getParameter("id"));
//            validateProductId(request, errors, product, Integer.parseInt(String.valueOf(product_id)));
//            validateProductName(request, errors, product);
//            validatePrice(request, errors, product);
//            validateQuantity(request, errors, product);
//            String product_size = request.getParameter("size");
//            if (!ValidateUtils.isSize(product_size)) {
//                errors.add("Size sản phẩm không hợp lệ. Phải là các size \"S, M, L, XL, XXL\"");
//            }
//
//            String product_img = "images/" + request.getParameter("image");
//
//            if (product_img.equals("images/")) {
//                errors.add("Chưa có hình ảnh! Thêm hình ảnh sản phẩm để thêm sản phẩm.");
//            }
//            String product_describe = request.getParameter("describe");
//
//            String category_id = request.getParameter("category_id");
//            if (category_id.equals("-- Chọn danh mục --")) {
//                errors.add("Category sản phẩm không hợp lệ. Vui lòng chọn category sản phẩm");
//            } else {
//                int cid = Integer.parseInt(category_id);
//                Category cate = new Category(cid);
//                product.setCategory(cate);
//            }
//
//            product.setDescrible(product_describe);
//            product.setImage(product_img);
//            product.setProductSizes(product.getProductSizes());
//
//
//            if (errors.isEmpty()) {
//                productDAO.insertProduct(product);
//                request.setAttribute("message", "Thêm sản phẩm thành công");
//            } else {
//                request.setAttribute("errors", errors);
//            }
//            showInsertProduct(request, response);
////            request.getRequestDispatcher("/admin/insertProduct.jsp").forward(request, response);
////            response.sendRedirect("/productManager?action=insertProduct");
//        } catch (Exception e) {
//            response.sendRedirect("404.jsp");
//        }
//    }

    private void validateProductId(HttpServletRequest req, List<String> errors, Product product, int product_id) {
        if (!ValidateUtils.isIdProduct(String.valueOf(product_id))) {
            errors.add("Id không hợp lệ. Phải bắt đầu là chữ và không quá 10 kí tự!");
        }
        product.setId(product.getId());
    }

    private void validateProductName(HttpServletRequest req, List<String> errors, Product product) {
        String product_name = req.getParameter("product_name");
        if (!ValidateUtils.isNameProduct(product_name)) {
            errors.add("Tên sản phẩm không hợp lệ. Phải bắt đầu là chữ số và có từ 6-255 kí tự!");
        }
        product.setName(product.getName());
    }

    private void validatePrice(HttpServletRequest req, List<String> errors, Product product) {
        try {
            double price = Double.parseDouble(req.getParameter("product_price"));
            if (price <= 0 || price > 10000000) {
                errors.add("Giá phải lớn hơn 0 và nhỏ hơn 10000000");
            } else {
                product.setPrice(price);
            }
        } catch (NumberFormatException numberFormatException) {
            errors.add("Định dạng giá không hợp lệ");
        }
    }

    private void validateQuantity(HttpServletRequest req, List<String> errors, Product product) {
        try {
            int quantity = Integer.parseInt(req.getParameter("product_quantity"));
            if (quantity <= 0 || quantity > 1000) {
                errors.add("Số lượng phải lớn hơn 0 và nhỏ hơn 1000");
            } else {
                product.setQuantity(quantity);
            }
        } catch (NumberFormatException numberFormatException) {
            errors.add("Định dạng số lượng không hợp lệ");
        }
    }

    // -----------chưa thêm danh mục vô được-----------------
    private void insertCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String name = request.getParameter("product_name");
            Category category = productDAO.getCategoryByName(name);
            if (category != null) {
                request.setAttribute("error", name + " already");
                request.getRequestDispatcher("admin/insertProduct.jsp").forward(request, response);
            } else {
                productDAO.insertCategory(name);
                request.setAttribute("message", "Thêm danh mục thành công!");
                showInsertProduct(request, response);
//                request.getRequestDispatcher("productManager?action=insert").forward(request, response);

            }
        } catch (Exception e) {
            response.sendRedirect("404.jsp");
        }
    }
}