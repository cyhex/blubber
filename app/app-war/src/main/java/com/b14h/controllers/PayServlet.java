package com.b14h.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.b14h.services.PayService;

public class PayServlet extends HttpServlet {

	private static final long serialVersionUID = 2187633796363073807L;

	/**
	 * TODO: use put requests instead of get requests. triggers a payment from a
	 * static parent paypal account to a static store payment account.
	 * 
	 * @param req
	 *            HttpServletRequest
	 * @param resp
	 *            HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PayService.pay();
		
		resp.getWriter().write("payment executed!");
	}
}
