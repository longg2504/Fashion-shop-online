package controller.home;

import dao.ProductDAO;
import model.Category;
import model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ProductDAO productDAO = new ProductDAO();
        List<Category> categories = productDAO.getCategory();
        List<Product> product = productDAO.getProduct9();
        request.setAttribute("ListALlProduct", product);
        request.setAttribute("ListAllCategory",categories);
        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }



    @Override
    public void destroy() {
    }
}
