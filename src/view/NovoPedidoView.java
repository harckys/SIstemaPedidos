package view;

import dao.ClienteDAO;
import dao.ItemDAO;
import dao.PedidoDAO;
import model.*;

import javax.swing.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class NovoPedidoView extends JFrame {
    private JComboBox<Cliente> cbClientes;
    private JComboBox<Item> cbItens;
    private JTextField tfQuantidade;
    private JTextArea taResumo;
    private JButton btnAdicionarItem, btnSalvarPedido;

    private List<PedidoItem> listaItens = new ArrayList<>();

    public NovoPedidoView() {
        super("Novo Pedido");
        setLayout(null);

        JLabel lblCliente = new JLabel("Cliente:");
        cbClientes = new JComboBox<>();
        JLabel lblItem = new JLabel("Item:");
        cbItens = new JComboBox<>();
        JLabel lblQtd = new JLabel("Qtd:");
        tfQuantidade = new JTextField(5);
        btnAdicionarItem = new JButton("Adicionar Item");
        taResumo = new JTextArea();
        taResumo.setEditable(false);
        btnSalvarPedido = new JButton("Salvar Pedido");

        JScrollPane scroll = new JScrollPane(taResumo);

        lblCliente.setBounds(20, 20, 80, 25);
        cbClientes.setBounds(100, 20, 200, 25);
        lblItem.setBounds(20, 60, 80, 25);
        cbItens.setBounds(100, 60, 200, 25);
        lblQtd.setBounds(20, 100, 80, 25);
        tfQuantidade.setBounds(100, 100, 50, 25);
        btnAdicionarItem.setBounds(160, 100, 140, 25);
        scroll.setBounds(20, 140, 400, 150);
        btnSalvarPedido.setBounds(150, 310, 140, 30);

        add(lblCliente); add(cbClientes);
        add(lblItem); add(cbItens);
        add(lblQtd); add(tfQuantidade);
        add(btnAdicionarItem); add(scroll); add(btnSalvarPedido);

        carregarClientes();
        carregarItens();

        btnAdicionarItem.addActionListener(e -> adicionarItem());
        btnSalvarPedido.addActionListener(e -> salvarPedido());

        setSize(460, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void carregarClientes() {
        cbClientes.removeAllItems();
        for (Cliente c : new ClienteDAO().listar()) {
            cbClientes.addItem(c);
        }
    }

    private void carregarItens() {
        cbItens.removeAllItems();
        for (Item i : new ItemDAO().listar()) {
            cbItens.addItem(i);
        }
    }

    private void adicionarItem() {
        Item item = (Item) cbItens.getSelectedItem();
        int qtd;

        try {
            qtd = Integer.parseInt(tfQuantidade.getText());
            if (qtd < 1) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida.");
            return;
        }

        PedidoItem pi = new PedidoItem();
        pi.setItem(item);
        pi.setQuantidade(qtd);
        listaItens.add(pi);

        atualizarResumo();
    }

    private void atualizarResumo() {
        StringBuilder sb = new StringBuilder();
        double total = 0;
        for (PedidoItem pi : listaItens) {
            sb.append(pi.getQuantidade())
                    .append(" x ")
                    .append(pi.getItem().getNome())
                    .append(" = R$ ")
                    .append(String.format("%.2f", pi.getSubtotal()))
                    .append("\n");
            total += pi.getSubtotal();
        }
        sb.append("\nTotal: R$ ").append(String.format("%.2f", total));
        taResumo.setText(sb.toString());
    }

    private void salvarPedido() {
        Cliente cliente = (Cliente) cbClientes.getSelectedItem();
        if (cliente == null || listaItens.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente e adicione ao menos um item.");
            return;
        }

        PedidoDAO dao = new PedidoDAO();
        if (dao.clienteTemPedidoPendente(cliente.getId())) {
            JOptionPane.showMessageDialog(this, "Este cliente já tem um pedido pendente.");
            return;
        }

        double total = listaItens.stream().mapToDouble(PedidoItem::getSubtotal).sum();
        String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDataHora(dataHora);
        pedido.setStatus("Pendente");
        pedido.setTotal(total);
        pedido.setItens(listaItens);

        if (dao.salvar(pedido)) {
            JOptionPane.showMessageDialog(this, "Pedido salvo com sucesso!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao salvar pedido.");
        }
    }
}
