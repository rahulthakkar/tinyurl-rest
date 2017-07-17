package me.rahulthakkar.tinyurl.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.rahulthakkar.tinyurl.utils.Constants;

public class DBConnection {

	private static final Logger LOGGER = Logger.getLogger(DBConnection.class.getName());

	private static Connection connection = null;
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		if (connection == null) {
			synchronized (DBConnection.class) {
				try {
					Class.forName(Constants.DB_DRIVER);
					connection = DriverManager.getConnection(Constants.CONNECTION_STR);
				} catch (ClassNotFoundException ex) {
					LOGGER.log(Level.SEVERE, ex.toString(), ex);
					throw ex;
				} catch (SQLException ex) {
					LOGGER.log(Level.SEVERE, ex.toString(), ex);
					throw ex;
				}
				
			}
		}
		return connection;
	}

}
