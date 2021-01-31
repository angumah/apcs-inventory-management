package application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import inventory.Phone;
import inventory.PhoneDAO;

@SuppressWarnings("serial")
public class Controller extends HttpServlet {
	private PhoneDAO dao;
	
	public void init() {
		final String url = getServletContext().getInitParameter("JDBC-URL");
		final String username = getServletContext().getInitParameter("JDBC-USERNAME");
		final String password = getServletContext().getInitParameter("JDBC-PASSWORD");
		
		
		dao = new PhoneDAO(url, username, password);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		final String action = request.getServletPath();
		

		try {
			switch (action) {
				case "/add": //intentionally fall through
				case "/edit": showEditForm(request, response); break;
				case "/insert": insertPhone(request, response); break;
				case "/update": updatePhone(request, response); break;
				case "/search": showSearchForm(request, response); break;
				default: viewPhones(request, response); break;
			}
		} catch (SQLException e) {
			throw new ServletException(e);
		}
		
	}
	
	
	private void viewPhones(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		List<Phone> phones = dao.getPhones();
		request.setAttribute("phones", phones);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("inventory.jsp");
		dispatcher.forward(request, response);
		
	}
	
	private void updatePhone(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		final String action = request.getParameter("action") != null
				? request.getParameter("action")
				: request.getParameter("submit").toLowerCase();
		final int id = Integer.parseInt(request.getParameter("id"));
		
		Phone phone = dao.getPhone(id);
		switch (action) {
			case "purchase": phone.purchaseMe(); break;
			case "return": phone.returnMe(); break;
			case "save":
				String name = request.getParameter("name");
				String manufacturer = request.getParameter("manufacturer");
				int units = Integer.parseInt(request.getParameter("units"));
				int supply = Integer.parseInt(request.getParameter("supply"));
				int cost = Integer.parseInt(request.getParameter("cost"));
				String description = request.getParameter("description");
				
				phone.setName(name);
				phone.setManufacturer(manufacturer);
				phone.setUnits(units);
				phone.setSupply(supply);
				phone.setCost(cost);
				phone.setDescription(description);
				break;
			case "delete": deletePhone(id, request, response); return;
		}
		dao.updatePhone(phone);
		
		response.sendRedirect(request.getContextPath() + "/");
	}
	
	private void insertPhone (HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		String name = request.getParameter("name");
		String manufacturer = request.getParameter("manufacturer");
		int units = Integer.parseInt(request.getParameter("units"));
		int cost = Integer.parseInt(request.getParameter("cost"));
		String description = request.getParameter("description");
		
		dao.insertPhone(name, manufacturer, units, units, cost, description);
		response.sendRedirect(request.getContextPath() + "/");
	}
	
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		try {
			final int id = Integer.parseInt(request.getParameter("id"));
			
			Phone phone = dao.getPhone(id);
			request.setAttribute("phone", phone);
		} catch (NumberFormatException e) {
			
		} finally {
			RequestDispatcher dispatcher = request.getRequestDispatcher("phoneform.jsp");
			dispatcher.forward(request, response);
		}
	}
	
	private void showSearchForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		List<Phone> phones = new ArrayList<>();
		if(request.getParameter("searchBy").equals("id")) {
			try {
				final int idSearch = Integer.parseInt(request.getParameter("searchText"));
				Phone phone = dao.getPhone(idSearch);
				phones.add(phone);
			} catch (NumberFormatException e) {
			
			}
		} else if(request.getParameter("searchBy").equals("name")) {
			
			final String textSearch = request.getParameter("searchText");
		
			Phone phone = dao.getPhone(textSearch);
			if(phone != null) {
				phones.add(phone);
			
			} else {
				phones = dao.getPhones(textSearch);
			}
		}
		request.setAttribute("phones", phones);
		RequestDispatcher dispatcher = request.getRequestDispatcher("searchform.jsp");
		dispatcher.forward(request, response);
	}
	
	
	private void deletePhone(final int id, HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		dao.deletePhone(dao.getPhone(id));
		response.sendRedirect(request.getContextPath() + "/");
	}
	
	
}
