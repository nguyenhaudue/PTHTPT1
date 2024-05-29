package rmi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class Client extends JFrame {
	private boolean isLoggedIn = false;
    private InterfacePro productManager;
    private UserManager userManager;
    private LoginDialog loginDialog;

    public Client(InterfacePro productManager, UserManager userManager) {
        this.productManager = productManager;
        this.userManager = userManager;
        initComponents();
    }

    private void initComponents() {
        setTitle("Product Management Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));

        JLabel titleLabel = new JLabel("MENU");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(titleLabel);

        JButton loginButton = new JButton("Đăng nhập");
        loginButton.addActionListener(e -> showLoginDialog());
        panel.add(loginButton);

        JButton registerButton = new JButton("Đăng ký");
        registerButton.addActionListener(e -> showRegisterDialog());
        panel.add(registerButton);

        getContentPane().add(panel);
    }

    private void showLoginDialog() {
        loginDialog = new LoginDialog(this, userManager, productManager);
        loginDialog.setVisible(true);
    }

    private void showRegisterDialog() {
        RegisterDialog registerDialog = new RegisterDialog(this, userManager);
        registerDialog.setVisible(true);
    }
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            InterfacePro productManager = (InterfacePro) registry.lookup("ProductManager");
            UserManager userManager = new UserManager(); // Thêm đối tượng UserManager

            // Hiển thị cửa sổ đăng nhập
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    Client client = new Client(productManager, userManager);
                    client.setVisible(true);
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Client exception: " + e.toString(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Phương thức để hiển thị giao diện chính sau khi đăng nhập thành công

    
void showMainMenu() {
    	
        String[] options = {"Hiển thị danh sách sản phẩm", "Tìm kiếm sản phẩm theo ID", "Thêm sản phẩm mới",
                            "Cập nhật thông tin sản phẩm", "Xóa sản phẩm", "Thoát"};
        int response;
        do {
            response = JOptionPane.showOptionDialog(this,
                    "Chọn chức năng", "MENU", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            switch (response) {
                case 0:
                    displayAllProducts();
                    break;
                case 1:
                    searchProductById();
                    break;
                case 2:
                    addNewProduct();
                    break;
                case 3:
                    updateProduct();
                    break;
                case 4:
                    deleteProduct();
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    break;
            }
        } while (response != 5);
    }
    	

    private void displayAllProducts() {
        try {
            List<Product> allProducts = productManager.getAllProducts();
            StringBuilder productList = new StringBuilder("Danh sách sản phẩm:\n");
            for (Product product : allProducts) {
                productList.append(product.toString()).append("\n");
            }
            JOptionPane.showMessageDialog(this, productList.toString(), "Danh sách sản phẩm", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
JOptionPane.showMessageDialog(this, "Lỗi khi hiển thị danh sách sản phẩm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void searchProductById() {
        try {
            int productId = Integer.parseInt(JOptionPane.showInputDialog(this, "Nhập ID sản phẩm cần tìm:", "Tìm kiếm sản phẩm theo ID", JOptionPane.QUESTION_MESSAGE));
            Product product = productManager.getProductById(productId);
            if (product != null) {
                JOptionPane.showMessageDialog(this, "Sản phẩm có ID " + productId + ":\n" + product.toString(), "Kết quả tìm kiếm", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm có ID " + productId + ".", "Kết quả tìm kiếm", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số nguyên cho ID sản phẩm.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm sản phẩm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void addNewProduct() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Nhập ID sản phẩm mới:", "Thêm sản phẩm mới", JOptionPane.QUESTION_MESSAGE));
            String name = JOptionPane.showInputDialog(this, "Nhập tên sản phẩm mới:", "Thêm sản phẩm mới", JOptionPane.QUESTION_MESSAGE);
            String price = JOptionPane.showInputDialog(this, "Nhập giá sản phẩm mới:", "Thêm sản phẩm mới", JOptionPane.QUESTION_MESSAGE);
            String quantity = JOptionPane.showInputDialog(this, "Nhập số lượng sản phẩm mới:", "Thêm sản phẩm mới", JOptionPane.QUESTION_MESSAGE);
            String ngaysx = JOptionPane.showInputDialog(this, "Nhập ngày sản xuất mới (yyyy-MM-dd):", "Thêm sản phẩm mới", JOptionPane.QUESTION_MESSAGE);
            String ngayhethan = JOptionPane.showInputDialog(this, "Nhập ngày hết hạn mới (yyyy-MM-dd):", "Thêm sản phẩm mới", JOptionPane.QUESTION_MESSAGE);

            Product newProduct = new Product(id, name, price, quantity, ngaysx, ngayhethan);
            productManager.addProduct(newProduct);
            JOptionPane.showMessageDialog(this, "Sản phẩm mới đã được thêm vào.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số nguyên cho ID sản phẩm.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm sản phẩm mới: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
e.printStackTrace();
        }
    }

    private void updateProduct() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Nhập ID sản phẩm cần cập nhật:", "Cập nhật thông tin sản phẩm", JOptionPane.QUESTION_MESSAGE));
            Product productToUpdate = productManager.getProductById(id);
            if (productToUpdate != null) {
                String name = JOptionPane.showInputDialog(this, "Nhập tên mới cho sản phẩm:", "Cập nhật thông tin sản phẩm", JOptionPane.QUESTION_MESSAGE);
                String price = JOptionPane.showInputDialog(this, "Nhập giá mới cho sản phẩm:", "Cập nhật thông tin sản phẩm", JOptionPane.QUESTION_MESSAGE);
                String quantity = JOptionPane.showInputDialog(this, "Nhập số lượng mới cho sản phẩm:", "Cập nhật thông tin sản phẩm", JOptionPane.QUESTION_MESSAGE);
                String ngaysx = JOptionPane.showInputDialog(this, "Nhập ngày sản xuất mới (yyyy-MM-dd):", "Cập nhật thông tin sản phẩm", JOptionPane.QUESTION_MESSAGE);
                String ngayhethan = JOptionPane.showInputDialog(this, "Nhập ngày hết hạn mới (yyyy-MM-dd):", "Cập nhật thông tin sản phẩm", JOptionPane.QUESTION_MESSAGE);

                productToUpdate.setName(name);
                productToUpdate.setPrice(price);
                productToUpdate.setQuantity(quantity);
                productToUpdate.setNgaysx(ngaysx);
                productToUpdate.setNgayhethan(ngayhethan);

                productManager.updateProduct(productToUpdate);
                JOptionPane.showMessageDialog(this, "Thông tin sản phẩm đã được cập nhật.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm có ID " + id + ".", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số nguyên cho ID sản phẩm.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật sản phẩm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void deleteProduct() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Nhập ID sản phẩm cần xóa:", "Xóa sản phẩm", JOptionPane.QUESTION_MESSAGE));
            Product productToDelete = productManager.getProductById(id);
            if (productToDelete != null) {
                productManager.deleteProduct(id);
                JOptionPane.showMessageDialog(this, "Sản phẩm có ID " + id + " đã được xóa.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm có ID " + id + ".", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số nguyên cho ID sản phẩm.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa sản phẩm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    
    
}