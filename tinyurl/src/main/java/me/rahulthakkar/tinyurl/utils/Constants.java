package me.rahulthakkar.tinyurl.utils;

public class Constants {
	public final static int URL_MAX_LENGTH = 2048;
	public final static String HTTP_PROTOCOL = "http://";
	public static final String DB_DRIVER = "org.sqlite.JDBC";
	// public static final String CONNECTION_STR =
	// "jdbc:sqlite:${catalina.base}/webapps/tinyurl/WEB-INF/classes/tinyurl.sqlite";
	public static final String CONNECTION_STR = "jdbc:sqlite:src/main/resources/tinyurl.sqlite";
	
	public static final int HASH_LENGTH = 7;
	public static final int TOTAL_DIGITS = 10;
	public static final int TOTAL_ALPHAS = 26;
	public static final int TOTAL_SYMBOLS = TOTAL_DIGITS+ 2*TOTAL_ALPHAS;
	
	public static final String ADD_URL_SQL = "INSERT INTO url_mapping (hash, long_url) VALUES (?, ?)";
	public static final String GET_URL_SQL = "SELECT * FROM url_mapping WHERE hash=?";
	public static final String BLACKLIST_SQL = "INSERT OR REPLACE INTO url_mapping (hash, long_url) VALUES (?, ?) ";
	public static final String BLACKLIST = "BLACKLIST";
}
