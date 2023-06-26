package controller.admin;

import dao.AdminDAO;
import dao.BillDAO;
import dao.ProductDAO;
import model.Bill;
import model.User;
import utils.CurrencyFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name="DashboardServlet",value ="/dashboard")
public class DashboardServlet extends HttpServlet {
    private AdminDAO adminDAO;
    private BillDAO billDAO;

    @Override
    public void init() throws ServletException {
        adminDAO = new AdminDAO();
        billDAO = new BillDAO();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if (user.getRole().equalsIgnoreCase("true")) {
                int count = adminDAO.CountProduct();
                int countuser = adminDAO.CountUser();
                int countbill = adminDAO.CountBill();
                int countproductlow = adminDAO.CountProductLow();
                String revenue = CurrencyFormat.covertPriceToString(billDAO.revenue());
                List<Bill> billbyday = billDAO.getBillByDay();
                request.setAttribute("product", count);
                request.setAttribute("user", countuser);
                request.setAttribute("bill", countbill);
                request.setAttribute("low", countproductlow);
                request.setAttribute("revenue", revenue);
                request.setAttribute("billbyday", billbyday);
                request.getRequestDispatcher("/admin/index.jsp").forward(request, response);
            } else {
                response.sendRedirect("/home/user");
            }
        } catch (Exception e) {
            response.sendRedirect("404.jsp");
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public void destroy() {

    }

}
