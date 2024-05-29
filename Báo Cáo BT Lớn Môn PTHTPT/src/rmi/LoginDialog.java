package rmi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginDialog extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private UserManager userManager;
    private InterfacePro productManager;

    public LoginDialog(JFrame parent, UserManager userManager, InterfacePro productManager) {
        super(parent, "Đăng nhập", true); // true để thiết lập dialog modal
        this.userManager = userManager;
        this.productManager = productManager;
        initComponents();
    }

    private void initComponents() {
        setTitle("Đăng nhập");
        setSize(300, 150);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Tài khoản:");
        panel.add(usernameLabel);

        usernameField = new JTextField();
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Mật khẩu:");
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("Đăng nhập");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (userManager.login(username, password)) {
                    // Hiển thị giao diện chính của ứng dụng nếu đăng nhập thành công
                    dispose();
                    Client client = (Client)getParent();
                    client.showMainMenu();
                } else {
                    JOptionPane.showMessageDialog(LoginDialog.this, "Sai tên đăng nhập hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(loginButton);

        getContentPane().add(panel, BorderLayout.CENTER);
    }
}

