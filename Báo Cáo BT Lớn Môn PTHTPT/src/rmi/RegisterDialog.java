package rmi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterDialog extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private UserManager userManager;

    public RegisterDialog(JFrame parent, UserManager userManager) {
        super(parent, "Đăng ký", true); // true để thiết lập dialog modal
        this.userManager = userManager;
        initComponents();
    }

    private void initComponents() {
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

        JButton registerButton = new JButton("Đăng ký");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (userManager.register(username, password)) {
                    JOptionPane.showMessageDialog(RegisterDialog.this, "Đăng ký thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(RegisterDialog.this, "Đăng ký thất bại! Tài khoản đã tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(registerButton);

        getContentPane().add(panel, BorderLayout.CENTER);
    }
}
