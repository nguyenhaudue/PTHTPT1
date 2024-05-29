package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.HashMap;
import java.sql.DriverManager;

public class UserManager extends UnicastRemoteObject {
    private static final long serialVersionUID = 1L;
    private Map<String, String> users; // Lưu trữ thông tin đăng nhập của người dùng
    private Connection connection; // Đối tượng kết nối cơ sở dữ liệu

    // Hàm tạo mặc định
    public UserManager() throws RemoteException {
        super();
        users = new HashMap<>();
        // Khởi tạo kết nối cơ sở dữ liệu
        initializeDatabaseConnection();
        // Thêm các tài khoản mẫu vào đây, thực tế sẽ được lấy từ cơ sở dữ liệu
        users.put("admin", "admin");
    }

    // Phương thức để khởi tạo kết nối cơ sở dữ liệu
    private void initializeDatabaseConnection() {
        try {
            // Load trình điều khiển JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Thiết lập kết nối đến cơ sở dữ liệu của bạn
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/product_management", "root", "1234");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Xử lý lỗi kết nối một cách thích hợp
        }
    }
    public boolean login(String username, String password) {
        
        return true; 
    }

    public boolean register(String username, String password) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO User(username, password) VALUES (?, ?)");
            statement.setString(1, username);
            statement.setString(2, password);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
