package controller.admin;

import dao.AdminDAO;
import dao.ProductDAO;
import model.Category;
import model.Product;
import model.ProductSize;
import model.User;
import utils.ValidateUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ProductManagerServlet", urlPatterns ="/productManager")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 50, // 50MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB

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
            request.getRequestDispatcher("admin/insertProduct.jsp").forward(request, response);
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
            case "insertProduct":
                insertProduct(request, response);
                break;
            case "updateProduct":
                try {
                    updateProduct(request,response);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "deleteProduct":
                deleteProduct(request, response);
                break;
        }
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int product_id = Integer.parseInt(request.getParameter("product_id"));
            productDAO.DeleteProduct(product_id);
//            request.getRequestDispatcher("/admin/product.jsp").forward(request, response);
            response.sendRedirect("/productManager");
        } catch (Exception e) {
            response.sendRedirect("404.jsp");
        }

    }
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }
    private void insertProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> errors = new ArrayList<>();
        Product product = new Product();
        try {
            String product_size = request.getParameter("size");
            if (!ValidateUtils.isSize(product_size)) {
                errors.add("Size sản phẩm không hợp lệ. Phải là các size \"S, M, L, XL, XXL\"");
            }
            String fileNameProductImg = "";

            String urlApp = getServletContext().getRealPath("/");
            System.out.println(urlApp);

            for (Part part : request.getParts()) {
                String fileName = extractFileName(part);
                if (!fileName.equals("")) {
                    fileName = new File(fileName).getName();

                    fileNameProductImg = fileName;
                    part.write("D:\\CodeG\\casestudy_3\\src\\main\\webapp\\assets\\images" + File.separator + fileName);
                    part.write(urlApp + "\\assets\\images" + File.separator + fileName);
                }
            }
            String product_img = "\\assets\\images" + fileNameProductImg;

            String product_describe = request.getParameter("describe");
            String category_id = request.getParameter("category_id");
            if (category_id.equals("-- Chọn danh mục --")) {
                errors.add("Category sản phẩm không hợp lệ. Vui lòng chọn category sản phẩm");
            } else {
                int cid = Integer.parseInt(category_id);
                Category cate = new Category(cid);
                product.setCategory(cate);
            }
            String[] size_rw = product_size.split("\\s*,\\s*");
            int[] size = new int[size_rw.length];
            List<ProductSize> sizeList = new ArrayList<>();
            try {
                for (int i = 0; i < size.length; i++) {
                    ProductSize s = new ProductSize(size_rw[i]);
                    sizeList.add(s);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            String product_name = request.getParameter("product_name");
            product.setName(product_name);
            validatePrice(request, errors, product);
            validateQuantity(request, errors, product);
            product.setDescrible(product_describe);
            product.setImage(product_img);
            product.setProductSizes(sizeList);
            if (errors.isEmpty()) {
                productDAO.insertProduct(product);
                request.setAttribute("message", "Thêm sản phẩm thành công");
            } else {
                request.setAttribute("errors", errors);
            }
            showProduct(request, response);
//            request.getRequestDispatcher("/admin/insertProduct.jsp").forward(request, response);
//            response.sendRedirect("/productManager?action=insertProduct");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("404.jsp");
        }
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


    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<String> errors = new ArrayList<>();
        Product product = new Product();
            int product_id = Integer.parseInt( request.getParameter("product_id"));
            String product_name = request.getParameter("product_name");
            validatePrice(request, errors, product);
            validateQuantity(request, errors, product);
            String category_id = request.getParameter("category_id");
            String product_size = request.getParameter("product_size");
            if (!ValidateUtils.isSize(product_size)) {
                errors.add("Size sản phẩm không hợp lệ. Phải là các size \"S, M, L, XL, XXL\"");
            }

        String fileNameProductImg = "";
        String urlApp = getServletContext().getRealPath("/");
        System.out.println(urlApp);

        for (Part part : request.getParts()) {
            String fileName = extractFileName(part);
            if (!fileName.equals("")) {
                fileName = new File(fileName).getName();

                fileNameProductImg = fileName;
                part.write("D:\\CodeG\\casestudy_3\\src\\main\\webapp\\assets\\images" + File.separator + fileName);
                part.write(urlApp + "\\assets\\images" + File.separator + fileName);
            }
        }
            String product_img = "\\assets\\images" + File.separator + fileNameProductImg;
            String product_describe = request.getParameter("product_describe");
            int cid = Integer.parseInt(category_id);
            Category cate = new Category(cid);
            String[] size_rw = product_size.split("\\s*,\\s*");
            //size
            List<ProductSize> list = new ArrayList<>();
            try {
                for (int i = 0; i < size_rw.length; i++) {
                    ProductSize s = new ProductSize(product_id, size_rw[i]);
                    list.add(s);
                }
            } catch (Exception e) {
            }
            product.setId(product_id);
            product.setName(product_name);
            product.setCategory(cate);
            product.setDescrible(product_describe);
            product.setImage(product_img);
            product.setProductSizes(list);
            if (errors.isEmpty()) {
                productDAO.UpdateProduct(product);
                request.setAttribute("message", "Thêm sản phẩm thành công");
            } else {
                request.setAttribute("errors", errors);
            }
            showProduct(request,response);
        }

}