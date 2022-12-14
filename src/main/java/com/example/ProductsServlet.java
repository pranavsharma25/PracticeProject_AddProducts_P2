package com.example;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.Products;

@WebServlet("/ProductShop")
public class ProductsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TODO Auto-generated method stub
        try {
               SessionFactory factory = HibernateUtil.getSessionFactory();
               
               Session session = factory.openSession();
               // using HQL
               List<Products> list = session.createQuery("from products", Products.class).list();
               
               session.close();
               
                PrintWriter out = response.getWriter();
                out.println("<html><body>");
                out.println("<b>Product Listing</b><br>");
                for(Products p: list) {
                        out.println("ID: " + String.valueOf(p.getId()) + ", Name: " + p.getName() +
                                        ", Price: " + String.valueOf(p.getPrice()) + ", Color: " + p.getColor().toString() + "<br>");
                }
                
            out.println("</body></html>");
            
            
        } catch (Exception ex) {
                throw ex;
        }

		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<b>Adding Product</b> " + request.getParameter("name") + "<br>");
        out.println("<a href='index.jsp'>Return to Main</a><br>");
        out.println("</body></html>");
        
        int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String color = request.getParameter("color");
		double price = Double.parseDouble(request.getParameter("price"));
		String productPrice = String.format("%.0f", price);
		HttpSession session = request.getSession(true);
		try {		
			ProductShopDAO ProductShopDAO = new ProductShopDAO();
			ProductShopDAO.addProduct(name, color, productPrice);
			response.sendRedirect("Success");
		} catch(Exception e) {
			System.err.println("doPost");
			e.printStackTrace();
		}
        
        //TODO: Take all parameters from post, and use hibernate to insert new product.
        // Then you need to print out some confirmation as to the the success/failure.
        // You also need to validate your input and not allow missing / NULL data.

		//doGet(request, response);
	}
}