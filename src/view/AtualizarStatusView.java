package view;

import dao.PedidoDAO;
import model.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class AtualizarStatusView extends JFrame {
    private JTable tabela;
    private DefaultTableModel modelo;
    private JButton btnAtualizar;

    public AtualizarStatusView() {
        super("Atualizar Status do Pedido");

        modelo = new DefaultTableModel(new Object[]{"ID", "Cliente", "Data", "Status", "Total"}, 0);
        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);

        btnAtualizar = new JButton("Atualizar Status");

        setLayout(null);
        scroll.setBounds(20, 20, 600, 250);
        btnAtualizar.setBounds(230, 280, 180, 30);

        add(scroll);
        add(btnAtualizar);

        carregarPedidos();

        btnAtualizar.addActionListener(e -> atualizarStatusSelecionado());

        setSize(660, 360);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void carregarPedidos() {
        modelo.setRowCount(0);
        List<Pedido> pedidos = new PedidoDAO().listarTodos();
        for (Pedido p : pedidos) {
            modelo.addRow(new Object[]{
                    p.getId(),
                    p.getCliente().getNome(),
                    p.getDataHora(),
                    p.getStatus(),
                    String.format("R$ %.2f", p.getTotal())
            });
        }
    }

    private void atualizarStatusSelecionado() {
        int row = tabela.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um pedido.");
            return;
        }

        int id = (int) modelo.getValueAt(row, 0);
        String statusAtual = (String) modelo.getValueAt(row, 3);

        String[] opcoes;
        switch (statusAtual) {
            case "Pendente":
                opcoes = new String[]{"Em Processamento"};
                break;
            case "Em Processamento":
                opcoes = new String[]{"Finalizado", "Cancelado"};
                break;
            default:
                JOptionPane.showMessageDialog(this, "Este pedido não pode ser alterado.");
                return;
        }

        String novoStatus = (String) JOptionPane.showInputDialog(
                this,
                "Novo status:",
                "Alterar Status",
                JOptionPane.PLAIN_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );

        if (novoStatus != null && !novoStatus.equals(statusAtual)) {
            boolean ok = new PedidoDAO().atualizarStatus(id, novoStatus);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Status atualizado!");
                carregarPedidos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar status.");
            }
        }
    }
}
