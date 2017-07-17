package me.rahulthakkar.tinyurl.client;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.validator.routines.UrlValidator;

import me.rahulthakkar.tinyurl.model.ErrorMessageModel;
import me.rahulthakkar.tinyurl.model.URLModel;
import me.rahulthakkar.tinyurl.utils.Constants;

/**
 * Servlet implementation class TinyURLClientServlet
 */
public class TinyURLClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Client client;
	private WebTarget target;
	private WebTarget urlTarget;
	private static final String BASE_TARGET = "http://localhost:8080/tinyurl/api/url/";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TinyURLClientServlet() {
		super();
		client = ClientBuilder.newClient();
		target = client.target(BASE_TARGET);
		urlTarget = target.path("{urlHash}");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String hash = request.getParameter("shortGetURLHash");
		PrintWriter writer = response.getWriter();

		if (hash == null || hash.trim().isEmpty()) {
			writer.append("Hash is empty: ");
		} else {

			hash = hash.trim();
			Builder builder = urlTarget.resolveTemplate("urlHash", hash.trim()).request();

			javax.ws.rs.core.Response serverResponse = builder.get();

			if (Response.Status.Family.SUCCESSFUL == Response.Status.Family.familyOf(serverResponse.getStatus())) {
				URLModel urlModel = serverResponse.readEntity(URLModel.class);

				String anchor = "<a href='" + urlModel.getLongURL() + "'>" + urlModel.getLongURL() + "</a>";
				writer.append("Long URL for http://tinyurl.com/" + urlModel.getShortURLHash()).append(" is: " + anchor);
			} else {
				// System.out.println(serverResponse.readEntity(String.class));

				ErrorMessageModel errorMessage = serverResponse.readEntity(ErrorMessageModel.class);
				writer.append("Error in retrieving url for http://tinyurl.com/" + hash)
						.append(": " + errorMessage.getErrorMessage());
			}
		}
		writer.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if ("delete".equals(request.getParameter("action"))) {
			doDelete(request, response);
			return;
		}

		UrlValidator urlValidator = new UrlValidator(UrlValidator.ALLOW_ALL_SCHEMES);

		PrintWriter writer = response.getWriter();

		String longURL = request.getParameter("longURL");
		if (longURL == null || longURL.trim().isEmpty() || longURL.trim().length() > Constants.URL_MAX_LENGTH) {
			writer.append("LongURL length should be greater than zero and less than " + Constants.URL_MAX_LENGTH);
		} else if (!urlValidator.isValid(longURL) && !urlValidator.isValid(Constants.HTTP_PROTOCOL + longURL)) {
			writer.append(longURL.trim() + " is not a valid URL.");
		} else {
			longURL = longURL.trim();
			URLModel model = new URLModel("", longURL);
			Builder builder = target.request(MediaType.APPLICATION_JSON);

			javax.ws.rs.core.Response serverResponse = builder.post(Entity.json(model));

			if (Response.Status.Family.SUCCESSFUL == Response.Status.Family.familyOf(serverResponse.getStatus())) {
				URLModel urlModel = serverResponse.readEntity(URLModel.class);

				String anchor = "<a href='#'>"
						+ "http://tinyurl.com/" + urlModel.getShortURLHash() + "</a>";
				writer.append("tinyURL for " + urlModel.getLongURL()).append(" is: " + anchor);
			} else {
				ErrorMessageModel errorMessage = serverResponse.readEntity(ErrorMessageModel.class);
				writer.append("Error in generating a tiny URL:").append(" " + errorMessage.getErrorMessage());
			}
		}
		writer.close();
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String hash = request.getParameter("shortDeleteURLHash");

		PrintWriter writer = response.getWriter();

		if (hash == null || hash.trim().isEmpty()) {
			writer.append("Hash is empty: ");
		} else {

			Builder builder = urlTarget.resolveTemplate("urlHash", hash).request();

			javax.ws.rs.core.Response serverResponse = builder.delete();

			if (Response.Status.Family.SUCCESSFUL == Response.Status.Family.familyOf(serverResponse.getStatus())) {
				writer.append("Successfully blocklisted the url http://tinyurl.com/" + hash);
			} else {
				// System.out.println(serverResponse.readEntity(String.class));
				ErrorMessageModel errorMessage = serverResponse.readEntity(ErrorMessageModel.class);
				writer.append("Error in blocklisting the url http://tinyurl.com/" + hash)
						.append(": " + errorMessage.getErrorMessage());
			}
		}
		writer.close();
	}
}
