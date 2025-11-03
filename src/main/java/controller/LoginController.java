package controller;

import java.io.IOException;
import java.sql.SQLException;

import dao.CustomerDao;
import dao.EmpDao;
import dto.Customer;
import dto.Emp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/out/login")
public class LoginController extends HttpServlet {
	// form
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/out/login.jsp").forward(request, response);
	}
	
	// action
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String customerOrEmpSel = request.getParameter("customerOrEmpSel");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		HttpSession session = request.getSession();
		
		if(customerOrEmpSel.equals("customer")) {
			Customer paramCustomer = new Customer();
			paramCustomer.setCustomerId(id);
			paramCustomer.setCustomerPw(pw);
			CustomerDao customerDao = new CustomerDao();
			Customer loginCustomer = null;
			try {
				loginCustomer = customerDao.selectCustomerByLogin(paramCustomer);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (loginCustomer == null) {
				System.out.println("Customer Login 실패");
				response.sendRedirect(request.getContextPath()+"/out/login");
				return;
			}
			session.setAttribute("loginCustomer", loginCustomer);
			System.out.println("Customer Login SUCCESS");
			response.sendRedirect(request.getContextPath()+"/customer/customerIndex");

		} else if(customerOrEmpSel.equals("emp")) {
			Emp paramEmp = new Emp();
			paramEmp.setEmpId(id);
			paramEmp.setEmpPw(pw);
			EmpDao empDao = new EmpDao();
			
			Emp loginEmp = null;
			try {
				loginEmp = empDao.selectEmpByLogin(paramEmp);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (loginEmp == null) {			
				System.out.println("Emp Login 실패");
				response.sendRedirect(request.getContextPath()+"/out/login");
				return;
			}			
			session.setAttribute("loginEmp", loginEmp);
			response.sendRedirect(request.getContextPath()+"/emp/empIndex");
		}
		
	}

}
