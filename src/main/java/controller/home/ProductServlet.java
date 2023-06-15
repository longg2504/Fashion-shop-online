package controller.home;

import dao.ProductDAO;
import model.Category;
import model.Product;
import model.ProductSize;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
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
            case "listByCategory":
                try {
                    showlistByCategory(request, response);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                break;
            case "product-detail":
                showProductDetail(request, response);
                break;
            case "sortProductByPriceDesc":
                try {
                    showSortProductByPriceDesc(request, response);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                break;
            case "sortProductByPriceAsc":
                try {
                    showSortProductByPriceAsc(request, response);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                break;
            case "sortProductByName":
                try {
                    showSortProductByName(request, response);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                try {
                    showList(request, response);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
        }
    }

    private void showProductDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int product_id = Integer.parseInt(request.getParameter("product_id"));
        List<ProductSize> sizeList = productDAO.getSizeByID(product_id);
        Product product = productDAO.getProductByID(product_id);
        if (product == null) {
            request.getRequestDispatcher("/404.jsp").forward(request, response);
        }
        int category_id = product.getCategory().getId();
        List<Product> productByCategory = productDAO.getProductByCategory(category_id);

        request.setAttribute("ProductData", product);
        request.setAttribute("SizeData", sizeList);
        request.setAttribute("ProductByCategory", productByCategory);
        request.getRequestDispatcher("/product-detail.jsp").forward(request, response);
    }

    private void showList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Product> productList;
        if(request.getParameter("category_id") == null){
            productList = productDAO.getProduct();
        }else{
            productList = productDAO.getProductByCategory(Integer.parseInt(request.getParameter("category_id")));
        }
        List<Category> category = productDAO.getCategory();
        int page, numperpage = 6;
        int size = productList.size();
        int num = (size % 6 == 0 ? (size / 6) : ((size / 6)) + 1);//so trang
        String xpage = request.getParameter("page");
        if (xpage == null) {
            page = 1;
        } else {
            page = Integer.parseInt(xpage);
        }
        int start, end;
        start = (page - 1) * numperpage;
        end = Math.min(page * numperpage, size);
        List<Product> product = productDAO.getListByPage(productList, start, end);
        request.setAttribute("page", page);
        request.setAttribute("num", num);
        request.setAttribute("ListAllCategory", category);
        request.setAttribute("ProductData", product);
        request.getRequestDispatcher("/products.jsp").forward(request, response);
    }
    private void showlistByCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String category_id = request.getParameter("category_id");
        int category_id1 = Integer.parseInt(category_id);
        List<Product> productList = productDAO.getProductByCategory(category_id1);
        List<Category> category = productDAO.getCategory();
        int page, numperpage = 6;
        int size = productList.size();
        int num = (size % 6 == 0 ? (size / 6) : ((size / 6)) + 1);//so trang
        String xpage = request.getParameter("page");
        if (xpage == null) {
            page = 1;
        } else {
            page = Integer.parseInt(xpage);
        }
        int start, end;
        start = (page - 1) * numperpage;
        end = Math.min(page * numperpage, size);
        List<Product> product = productDAO.getListByPage(productList, start, end);
        request.setAttribute("page", page);
        request.setAttribute("num", num);
        request.setAttribute("CategoryData", category);
        request.setAttribute("ProductData", product);
        request.getRequestDispatcher("/products.jsp").forward(request, response);
    }

    private void showSortProductByPriceDesc(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Product> productList = productDAO.getProductByPriceDesc();
        List<Category> category = productDAO.getCategory();
        int page, numperpage = 6;
        int size = productList.size();
        int num = (size % 6 == 0 ? (size / 6) : ((size / 6)) + 1);//so trang
        String xpage = request.getParameter("page");
        if (xpage == null) {
            page = 1;
        } else {
            page = Integer.parseInt(xpage);
        }
        int start, end;
        start = (page - 1) * numperpage;
        end = Math.min(page * numperpage, size);
        List<Product> product = productDAO.getListByPage(productList, start, end);
        request.setAttribute("page", page);
        request.setAttribute("num", num);
        request.setAttribute("CategoryData", category);
        request.setAttribute("ProductData", product);
        request.getRequestDispatcher("/products.jsp").forward(request, response);
    }

    private void showSortProductByPriceAsc(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Product> productList = productDAO.getProductByPriceAsc();
        List<Category> category = productDAO.getCategory();
        int page, numperpage = 6;
        int size = productList.size();
        int num = (size % 6 == 0 ? (size / 6) : ((size / 6)) + 1);//so trang
        String xpage = request.getParameter("page");
        if (xpage == null) {
            page = 1;
        } else {
            page = Integer.parseInt(xpage);
        }
        int start, end;
        start = (page - 1) * numperpage;
        end = Math.min(page * numperpage, size);
        List<Product> product = productDAO.getListByPage(productList, start, end);
        request.setAttribute("page", page);
        request.setAttribute("num", num);
        request.setAttribute("CategoryData", category);
        request.setAttribute("ProductData", product);
        request.getRequestDispatcher("/products.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "search":
                try {
                    searchProduct(request, response);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
        }
    }

    private void searchProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String text = request.getParameter("text");
        List<Product> productList = productDAO.SearchAll(text);
        List<Category> category = productDAO.getCategory();
        int page, numperpage = 6;
        int size = productList.size();
        int num = (size % 6 == 6 ? (size / 6) : ((size / 6)) + 1);//so trang
        String xpage = request.getParameter("page");
        if (xpage == null) {
            page = 1;
        } else {
            page = Integer.parseInt(xpage);
        }
        int start, end;
        start = (page - 1) * numperpage;
        end = Math.min(page * numperpage, size);
        List<Product> product = productDAO.getListByPage(productList, start, end);
        request.setAttribute("page", page);
        request.setAttribute("num", num);
        request.setAttribute("CategoryData", category);
        request.setAttribute("ProductData", product);
        request.getRequestDispatcher("/products.jsp").forward(request, response);
    }


    private void showSortProductByName(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Product> productList = productDAO.getProductByName();
        List<Category> category = productDAO.getCategory();
        int page, numperpage = 6;
        int size = productList.size();
        int num = (size % 6 == 0 ? (size / 6) : ((size / 6)) + 1);//so trang
        String xpage = request.getParameter("page");
        if (xpage == null) {
            page = 1;
        } else {
            page = Integer.parseInt(xpage);
        }
        int start, end;
        start = (page - 1) * numperpage;
        end = Math.min(page * numperpage, size);
        List<Product> product = productDAO.getListByPage(productList, start, end);
        request.setAttribute("page", page);
        request.setAttribute("num", num);
        request.setAttribute("CategoryData", category);
        request.setAttribute("ProductData", product);
        request.getRequestDispatcher("/products.jsp").forward(request, response);
    }

}
