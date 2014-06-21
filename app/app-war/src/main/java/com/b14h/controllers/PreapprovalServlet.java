package com.b14h.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.b14h.services.PreapprovalService;

public class PreapprovalServlet extends HttpServlet {

	private static final long serialVersionUID = -5608710629269370689L;

	/**
	 * Get Preapproval redirect URL.
	 * 
	 * @param req
	 *            HttpServletRequest should contain the parent Paypal ID in
	 *            future versions.
	 * @param resp
	 *            HttpServletResponse contains redirect URL to paypal with the
	 *            preapproval key.
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String preapprovalKey = PreapprovalService.preapprove();
		resp.sendRedirect("https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_ap-preapproval&preapprovalkey="
				+ preapprovalKey);
		
	}
}
