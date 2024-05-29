
package rmi;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Implement extends UnicastRemoteObject implements InterfacePro {
    private static final long serialVersionUID = 1L;
    private Connection connection;

    public Implement() throws RemoteException {
        super();
        // Khởi tạo kết nối JDBC ở đây
        try {
            // Thiết lập kết nối JDBC
            String url = "jdbc:mysql://127.0.0.1:3306/product_management";
            String username = "root";
            String password = "1234";
            connection = DriverManager.getConnection(url, username, password);

            // Tự động tạo bảng User nếu chưa tồn tại
            Statement createTableStatement = connection.createStatement();
            String createTableQuery = "CREATE TABLE IF NOT EXISTS User (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "username VARCHAR(255) NOT NULL UNIQUE," +
                    "password VARCHAR(255) NOT NULL)";
            createTableStatement.executeUpdate(createTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean registerUser(String username, String password) throws RemoteException {
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

    public boolean loginUser(String username, String password) throws RemoteException {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM User WHERE username = ? AND password = ?");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // Trả về true nếu tồn tại người dùng với username và password đã cung cấp
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Product> getAllProducts() throws RemoteException {
        List<Product> products = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM product");
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getString("price"));
                product.setQuantity(resultSet.getString("quantity"));
                product.setNgaysx(resultSet.getString("ngaysx")); // Thêm ngaysx vào đây
                product.setNgayhethan(resultSet.getString("ngayhethan")); // Thêm ngayhethan vào đây
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public Product getProductById(int id) throws RemoteException {
        Product product = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM product WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getString("price"));
                product.setQuantity(resultSet.getString("quantity"));
                product.setNgaysx(resultSet.getString("ngaysx")); // Thêm ngaysx vào đây
                product.setNgayhethan(resultSet.getString("ngayhethan")); // Thêm ngayhethan vào đây
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    public void addProduct(Product product) throws RemoteException {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO product(id, name, price, quantity, ngaysx, ngayhethan) VALUES (?, ?, ?, ?, ?, ?)");
            statement.setInt(1, product.getId());
            statement.setString(2, product.getName());
            statement.setString(3, product.getPrice());
            statement.setString(4, product.getQuantity());
            statement.setString(5, product.getNgaysx()); // Thêm ngaysx vào đây
            statement.setString(6, product.getNgayhethan()); // Thêm ngayhethan vào đây
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(Product product) throws RemoteException {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE product SET name = ?, price = ?, quantity = ?, ngaysx = ?, ngayhethan = ? WHERE id = ?");
            statement.setString(1, product.getName());
            statement.setString(2, product.getPrice());
            statement.setString(3, product.getQuantity());
            statement.setString(4, product.getNgaysx()); // Thêm ngaysx vào đây
            statement.setString(5, product.getNgayhethan()); // Thêm ngayhethan vào đây
            statement.setInt(6, product.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int id) throws RemoteException {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM product WHERE id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }