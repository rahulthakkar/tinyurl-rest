package me.rahulthakkar.tinyurl.resource;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.validator.routines.UrlValidator;

import me.rahulthakkar.tinyurl.model.URLModel;
import me.rahulthakkar.tinyurl.service.URLService;
import me.rahulthakkar.tinyurl.utils.Constants;

/**
 * Root resource (exposed at "url" path)
 */
@Path("url")
@Produces(MediaType.APPLICATION_JSON)
public class URLResource {

	private static final Logger LOGGER = Logger.getLogger(URLResource.class.getName());
	private URLService service;
	
	public URLResource() {
		service = new URLService();
	}

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to
	 * the client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws UnsupportedEncodingException
	 */
	@GET
	@Path("{hashStr}")
	public URLModel getLongURL(@PathParam("hashStr") String hashStr) throws ClassNotFoundException, SQLException {
		if (hashStr == null || hashStr.trim().isEmpty()) {
			LOGGER.log(Level.FINE, "Short URL hash is empty");
			throw new IllegalArgumentException("Short URL hash is empty");
		}
		return service.getLongURL(hashStr);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addLongURL(URLModel urlModel) throws ClassNotFoundException, SQLException {
		String longURL = urlModel.getLongURL();
		UrlValidator urlValidator = new UrlValidator(UrlValidator.ALLOW_ALL_SCHEMES);

		if (longURL == null || longURL.isEmpty() || longURL.length() > Constants.URL_MAX_LENGTH) {
			throw new IllegalArgumentException(
					"LongURL length should be greater than zero and less than " + Constants.URL_MAX_LENGTH);
		}

		if (!urlValidator.isValid(longURL)) {
			if (urlValidator.isValid(Constants.HTTP_PROTOCOL + longURL)) {
				urlModel.setLongURL(Constants.HTTP_PROTOCOL + longURL);
			} else {
				LOGGER.log(Level.FINE, "{0} is not a valid URL.", longURL);
				throw new IllegalArgumentException(longURL + " is not a valid URL ");
			}
		}

		URLModel model = service.addLongURL(urlModel);

		return Response.status(Status.CREATED).type(MediaType.APPLICATION_JSON).entity(model).build();
	}

	@DELETE
	@Path("{hashStr}")
	public void blackListURL(@PathParam("hashStr") String hashStr) throws ClassNotFoundException, InternalError, SQLException {
		if (hashStr == null || hashStr.trim().isEmpty()) {
			LOGGER.log(Level.FINE, "Short URL hash is empty");
			throw new IllegalArgumentException("Short URL hash is empty");
		}
			
		service.blackListURL(hashStr);
	}
}
