package view;

import dao.ItemDAO;
import model.Item;

import javax.swing.*;

public class ItemView extends JFrame {
    private JTextField tfNome, tfDescricao, tfPreco;
    private JButton btnSalvar;

    public ItemView() {
        super("Cadastro de Item");
        setLayout(null);

        JLabel lblNome = new JLabel("Nome:");
        JLabel lblDescricao = new JLabel("Descrição:");
        JLabel lblPreco = new JLabel("Preço Unitário:");

        tfNome = new JTextField(20);
        tfDescricao = new JTextField(20);
        tfPreco = new JTextField(10);
        btnSalvar = new JButton("Salvar");

        lblNome.setBounds(30, 20, 120, 25);
        tfNome.setBounds(150, 20, 200, 25);
        lblDescricao.setBounds(30, 60, 120, 25);
        tfDescricao.setBounds(150, 60, 200, 25);
        lblPreco.setBounds(30, 100, 120, 25);
        tfPreco.setBounds(150, 100, 100, 25);
        btnSalvar.setBounds(150, 140, 100, 30);

        add(lblNome); add(tfNome);
        add(lblDescricao); add(tfDescricao);
        add(lblPreco); add(tfPreco);
        add(btnSalvar);

        btnSalvar.addActionListener(e -> salvarItem());

        setSize(400, 230);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void salvarItem() {
        String nome = tfNome.getText().trim();
        String descricao = tfDescricao.getText().trim();
        String precoStr = tfPreco.getText().trim();

        if (nome.isEmpty() || precoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e preço são obrigatórios.");
            return;
        }

        double preco;
        try {
            preco = Double.parseDouble(precoStr);
            if (preco <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Preço inválido. Digite um número maior que zero.");
            return;
        }

        Item item = new Item();
        item.setNome(nome);
        item.setDescricao(descricao);
        item.setPreco(preco);

        ItemDAO dao = new ItemDAO();
        if (dao.salvar(item)) {
            JOptionPane.showMessageDialog(this, "Item salvo com sucesso!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao salvar item.");
        }
    }
}
