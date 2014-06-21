package com.b14h.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.b14h.libs.BlubConverter;
import com.b14h.model.Child;
import com.b14h.model.Store;
import com.b14h.services.BalanceService;
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

		String productName = req.getParameter("taskId");
		int blubs = Integer.parseInt(req.getParameter("unit_price"));
		int unitCount = Integer.parseInt(req.getParameter("unit_count"));
		String recipient = req.getParameter("recipient");
		
		double totalEur = BlubConverter.toEur(blubs * unitCount);

		try {
			BalanceService.payStore(Store.getInstance(), Child.getInstance(), blubs);
			PayService.pay(recipient, totalEur, productName);
			resp.getWriter().write("payment executed!");
		} catch (Exception e) {
			resp.getWriter().write("payment not executed! because: " + e);
		}
	}
}
