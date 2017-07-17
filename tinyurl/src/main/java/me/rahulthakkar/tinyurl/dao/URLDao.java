package me.rahulthakkar.tinyurl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.rahulthakkar.tinyurl.model.URLModel;
import me.rahulthakkar.tinyurl.utils.Constants;

public class URLDao {
	
	private static final Logger LOGGER = Logger.getLogger(URLDao.class.getName());
	
	public boolean addURL(URLModel urlModel) throws SQLException, ClassNotFoundException {
		Connection connection = DBConnection.getConnection();
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(Constants.ADD_URL_SQL);
			statement.setString(1, urlModel.getShortURLHash());
			statement.setString(2, urlModel.getLongURL());
			
			int count = statement.executeUpdate();
			if(count == 1){
				return true;
			}
			statement.close();
		} catch (SQLException ex) {
			LOGGER.log(Level.SEVERE, ex.toString(), ex);
			throw ex;
		}
		return false;
	}

	public URLModel getURL(String hashStr) throws SQLException, ClassNotFoundException {
		
		Connection connection = DBConnection.getConnection();
		PreparedStatement statement = null;
		URLModel urlModel = null;
		try {
			statement = connection.prepareStatement(Constants.GET_URL_SQL);
			statement.setString(1, hashStr);
			ResultSet resultSet = statement.executeQuery();
			
			if(resultSet.next()) {
				urlModel = new URLModel(resultSet.getString("hash"), resultSet.getString("long_url"));
				if(Constants.BLACKLIST.equals(urlModel.getLongURL())) {
					LOGGER.log(Level.INFO,"Block listed hash {0} access request", hashStr);
					//TODO
					return null;
				}
			}
			statement.close();
		} catch (SQLException ex) {
			LOGGER.log(Level.SEVERE, ex.toString(), ex);
			throw ex;
		}
		
		return urlModel;
	}

	public boolean blackListURL(String hashStr) throws SQLException, ClassNotFoundException {
		Connection connection = DBConnection.getConnection();
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(Constants.BLACKLIST_SQL);
			statement.setString(1, hashStr);
			statement.setString(2, Constants.BLACKLIST);
			
			int count = statement.executeUpdate();
			statement.close();
			if(count == 1){
				return true;
			}
		} catch (SQLException ex) {
			LOGGER.log(Level.SEVERE, ex.toString(), ex);
			throw ex;
		}
		return false;
	}

}
