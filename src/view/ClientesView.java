package view;

import dao.ClienteDAO;
import model.Cliente;

import javax.swing.*;
import java.awt.event.*;

public class ClientesView extends JFrame {
    private JTextField tfNome, tfCpfCnpj, tfEmail, tfTelefone;
    private JButton btnSalvar;

    public ClientesView() {
        super("Cadastro de Cliente");
        setLayout(null);

        JLabel lblNome = new JLabel("Nome:");
        JLabel lblCpfCnpj = new JLabel("CPF/CNPJ:");
        JLabel lblEmail = new JLabel("Email:");
        JLabel lblTelefone = new JLabel("Telefone:");

        tfNome = new JTextField(20);
        tfCpfCnpj = new JTextField(20);
        tfEmail = new JTextField(20);
        tfTelefone = new JTextField(20);
        btnSalvar = new JButton("Salvar");

        lblNome.setBounds(30, 20, 100, 25);
        tfNome.setBounds(130, 20, 200, 25);
        lblCpfCnpj.setBounds(30, 60, 100, 25);
        tfCpfCnpj.setBounds(130, 60, 200, 25);
        lblEmail.setBounds(30, 100, 100, 25);
        tfEmail.setBounds(130, 100, 200, 25);
        lblTelefone.setBounds(30, 140, 100, 25);
        tfTelefone.setBounds(130, 140, 200, 25);
        btnSalvar.setBounds(130, 190, 100, 30);

        add(lblNome); add(tfNome);
        add(lblCpfCnpj); add(tfCpfCnpj);
        add(lblEmail); add(tfEmail);
        add(lblTelefone); add(tfTelefone);
        add(btnSalvar);

        btnSalvar.addActionListener(e -> salvarCliente());

        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void salvarCliente() {
        String nome = tfNome.getText().trim();
        String cpfCnpj = tfCpfCnpj.getText().trim();
        String email = tfEmail.getText().trim();
        String telefone = tfTelefone.getText().trim();

        if (nome.isEmpty() || cpfCnpj.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e CPF/CNPJ são obrigatórios.");
            return;
        }

        ClienteDAO dao = new ClienteDAO();
        if (dao.existeCpfCnpj(cpfCnpj)) {
            JOptionPane.showMessageDialog(this, "CPF/CNPJ já cadastrado.");
            return;
        }

        Cliente c = new Cliente();
        c.setNome(nome);
        c.setCpfCnpj(cpfCnpj);
        c.setEmail(email);
        c.setTelefone(telefone);

        if (dao.salvar(c)) {
            JOptionPane.showMessageDialog(this, "Cliente salvo com sucesso!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao salvar cliente.");
        }
    }
}
