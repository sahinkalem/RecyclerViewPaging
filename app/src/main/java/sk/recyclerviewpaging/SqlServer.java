package sk.recyclerviewpaging;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class SqlServer {
	Connection con;

	@SuppressLint ("NewApi")
	public Connection Connect() {
		String ip = "192.168.1.99";
		String port = "1433";
		String db = "database";
		String username = "sa";
		String password = "*********";

		StrictMode.ThreadPolicy builder = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(builder);

		String connectURL;

		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			connectURL = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";databasename=" + db + ";user=" + username + ";" + "password=" + password + ";";
			con = DriverManager.getConnection(connectURL);
		} catch (Exception e) {
			Log.e("Error :", e.getMessage());
		}
		return con;
	}
}
