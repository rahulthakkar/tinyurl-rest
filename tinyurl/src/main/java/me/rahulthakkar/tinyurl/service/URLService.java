package me.rahulthakkar.tinyurl.service;

import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.NotFoundException;

import me.rahulthakkar.tinyurl.dao.URLDao;
import me.rahulthakkar.tinyurl.model.URLModel;
import me.rahulthakkar.tinyurl.utils.Constants;

public class URLService {

	private static final Logger LOGGER = Logger.getLogger(URLService.class.getName());
	private Random random;

	public URLService() {
		random = new Random();
	}

	public URLModel getLongURL(String hashStr) throws ClassNotFoundException, SQLException {
		URLModel model = new URLDao().getURL(hashStr);
		if (model == null) {
			LOGGER.log(Level.FINE, "URL with hash {0} not found on server", hashStr);
			throw new NotFoundException("URL with hash " + hashStr + " not found on server");
		}
		return model;
	}

	public URLModel addLongURL(URLModel urlModel) throws ClassNotFoundException, SQLException {
		String hash = getRandomSring(Constants.HASH_LENGTH);
		urlModel.setShortURLHash(hash);
		URLDao urlDao = new URLDao();
		while (!urlDao.addURL(urlModel)) {
			LOGGER.log(Level.FINEST, "{0}: hash already present in the DB", hash);
			hash = getRandomSring(Constants.HASH_LENGTH);
			urlModel.setShortURLHash(hash);
		}
		return urlModel;
	}

	private String getRandomSring(final int hashLength) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < hashLength; i++) {
			builder.append(getSymbol(random.nextInt(Constants.TOTAL_SYMBOLS)));
		}
		return builder.toString();
	}

	private char getSymbol(final int nextInt) {

		if (nextInt >= 0 && nextInt < Constants.TOTAL_DIGITS) {
			return (char) ('0' + nextInt);
		} else if (nextInt >= Constants.TOTAL_DIGITS && nextInt < Constants.TOTAL_DIGITS + Constants.TOTAL_ALPHAS) {
			return (char) ('a' + (nextInt - Constants.TOTAL_DIGITS));
		} else {
			return (char) ('A' + (nextInt - Constants.TOTAL_DIGITS - Constants.TOTAL_ALPHAS));
		}
	}

	public void blackListURL(String hashStr) throws ClassNotFoundException, InternalError, SQLException {
		if (!new URLDao().blackListURL(hashStr)) {
			LOGGER.log(Level.SEVERE, "Error in blocklisting URL hash {0}", hashStr);
			throw new InternalError("Error in blocklisting URL hash " + hashStr);
		}
	}

}
