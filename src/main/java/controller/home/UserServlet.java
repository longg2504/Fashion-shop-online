package controller.home;

import dao.BillDAO;
import dao.UserDAO;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import model.Bill;
import model.BillDetail;
import model.User;
import utils.ValidateUtils;

@WebServlet(name="UserServlet",value = "/user")
public class UserServlet extends HttpServlet {
    private UserDAO userDAO;
    private BillDAO billDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
        billDAO = new BillDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "login":
                showLogin(req, resp);
                break;
            case "logout":
                checkOut(req,resp);
                break;
            case "showHistory":
                showHistory(req, resp);
                break;
            case "showDetail":
                showBillDetail(req, resp);
                break;
            default:
                showLogin(req, resp);
                break;
        }
    }

    private void showBillDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bill_id = request.getParameter("bill_id");
        int id = Integer.parseInt(bill_id);
        List<BillDetail> detail = billDAO.getDetail(id);
        request.setAttribute("detail", detail);
        request.getRequestDispatcher("/billdetail.jsp").forward(request, response);
    }

    private void showHistory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);
        try {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                int user_id = user.getUser_id();
                List<Bill> bill = billDAO.getBillByID(user_id);
                request.setAttribute("bill", bill);
                request.getRequestDispatcher("/history.jsp").forward(request, response);
            } else {
                response.sendRedirect("user?action=login");
            }
        } catch (ServletException e) {
            response.sendRedirect("user?action=login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "checkLogin":
                try {
                    checkLogin(request, response);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                break;
            case "updateInfo":
                try {
                    updateInfo(request, response);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                break;
            case "register":
                try {
                    register(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "logout":
                checkOut(request,response);
                break;
            default:
                showLogin(request, response);
                break;
        }
    }

    private void checkOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect("/home");
    }

    private void showLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/home/login.jsp").forward(request, response);
    }

    private void register(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String user_email = request.getParameter("user_email_re");
        String first_name = request.getParameter("first_name");
        String last_name = request.getParameter("last_name");
        if (!ValidateUtils.isEmail(user_email)) {
            request.setAttribute("error_email", "Email không hợp lệ. Hãy nhập lại...");
            request.getRequestDispatcher("/home/login.jsp").forward(request, response);
        }
        String user_pass = request.getParameter("user_pass_re");
        String re_pass = request.getParameter("re_pass_re");
        if (!user_pass.equals(re_pass)) {
            request.setAttribute("error_pass", "Mật khẩu không trùng khớp. Hãy nhập lại...");
            request.getRequestDispatcher("/home/login.jsp").forward(request, response);
        } else {
            User a = userDAO.checkAcc(user_email);
            if (a == null) {
                userDAO.signup(first_name,last_name,user_email, user_pass);
                request.setAttribute("message", "Đăng ký thành công");
                request.getRequestDispatcher("/home/login.jsp").forward(request, response);
            } else {
                request.setAttribute("emailavailable", "Email đã tồn tại!");
                request.getRequestDispatcher("/home/login.jsp").forward(request, response);
            }
        }
    }
//D:\CodeG\casestudy_3\src\main\webapp\assets\images\Avatars
    private void updateInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if (user == null) {
                response.sendRedirect("user?action=login");
            } else {
                String first_name = request.getParameter("first_name");
                String last_name = request.getParameter("last_name");
                String user_pass = request.getParameter("user_pass");
                int user_id = user.getUser_id();
                userDAO.UpdateUser(user_id ,user_pass);
                user.setUser_pass(user_pass);
                session.setAttribute("user", user);
                request.setAttribute("message", "Đã cập nhật thành công");
                request.getRequestDispatcher("/change-passwork.jsp").forward(request, response);
                }
            }catch (Exception e){
            e.printStackTrace();
        }}


    private void checkLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String user_email = request.getParameter("user_email");
        String user_pass = request.getParameter("user_pass");
        User user = userDAO.checkUser(user_email, user_pass);
        if (user == null) {
            request.setAttribute("error", "Tài khoản không tồn tại hoặc mật khẩu không đúng !");
            request.getRequestDispatcher("/home/login.jsp").forward(request, response);
        } else {
            if(user.getRole().equals("true")) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect("/admin/index.jsp");
            }
            else{
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect("/home");
            }
        }
    }

    @Override
    public void destroy() {
    }


}
