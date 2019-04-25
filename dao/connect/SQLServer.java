package by.park.dao.connect;

import by.park.dao.DAOFactory;
import by.park.dao.ForesterDAO;
import by.park.dao.OwnerDAO;
import by.park.dao.PlantsInfoDAO;
import by.park.dao.service.SQLServerForester;
import by.park.dao.service.SQLServerOwner;
import by.park.dao.service.SQLServerPlantsInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLServer extends DAOFactory {
    private static final String DB_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String DB_URL = "jdbc:sqlserver://localhost:61907;databaseName=Test";
    private static final String DB_USERNAME = "Lev";
    private static final String DB_PASSWORD = "1234";

    public static Connection createConnection() {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        }catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connection Error");
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public OwnerDAO getOwnerDAO() {
        return new SQLServerOwner();
    }

    @Override
    public ForesterDAO getForesterDAO() {
        return new SQLServerForester();
    }

    @Override
    public PlantsInfoDAO getPlantInfoDAO() {
        return new SQLServerPlantsInfo();
    }
}
