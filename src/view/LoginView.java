package view;

import dao.UsuarioDAO;
import model.Usuario;

import javax.swing.*;
import java.awt.event.*;

public class LoginView extends JFrame {
    private JTextField tfLogin;
    private JPasswordField pfSenha;
    private JButton btnEntrar;

    public LoginView() {
        super("Login");
        setLayout(null);

        JLabel lblLogin = new JLabel("Usuário:");
        JLabel lblSenha = new JLabel("Senha:");
        tfLogin = new JTextField(20);
        pfSenha = new JPasswordField(20);
        btnEntrar = new JButton("Entrar");

        lblLogin.setBounds(30, 30, 80, 25);
        tfLogin.setBounds(100, 30, 150, 25);
        lblSenha.setBounds(30, 70, 80, 25);
        pfSenha.setBounds(100, 70, 150, 25);
        btnEntrar.setBounds(100, 110, 100, 30);

        add(lblLogin); add(tfLogin);
        add(lblSenha); add(pfSenha);
        add(btnEntrar);

        btnEntrar.addActionListener(e -> autenticar());

        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void autenticar() {
        String login = tfLogin.getText();
        String senha = new String(pfSenha.getPassword());

        UsuarioDAO dao = new UsuarioDAO();
        Usuario u = dao.autenticar(login, senha);

        if (u != null) {
            JOptionPane.showMessageDialog(this, "Bem-vindo, " + u.getNome() + "!");
            dispose(); // fecha a tela de login
        } else {
            JOptionPane.showMessageDialog(this, "Credenciais inválidas.");
        }
    }
}
