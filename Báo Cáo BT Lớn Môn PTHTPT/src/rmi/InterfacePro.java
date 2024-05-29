package rmi;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface InterfacePro extends Remote {
    List<Product> getAllProducts() throws RemoteException;
    Product getProductById(int id) throws RemoteException;
    void addProduct(Product product) throws RemoteException;
    void updateProduct(Product product) throws RemoteException;
    void deleteProduct(int id) throws RemoteException;

 // Thêm phương thức đăng ký người dùng
    boolean registerUser(String username, String password) throws RemoteException;

    // Thêm phương thức đăng nhập người dùng
    boolean loginUser(String username, String password) throws RemoteException;
}

