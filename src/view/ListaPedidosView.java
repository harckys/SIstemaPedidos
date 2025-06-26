package view;

import dao.PedidoDAO;
import model.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ListaPedidosView extends JFrame {
    private JTable tabela;
    private DefaultTableModel modelo;

    public ListaPedidosView() {
        super("Lista de Pedidos");

        modelo = new DefaultTableModel(new Object[]{"ID", "Cliente", "Data", "Status", "Total"}, 0);
        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);

        carregarPedidos();

        add(scroll);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void carregarPedidos() {
        modelo.setRowCount(0); // limpa
        PedidoDAO dao = new PedidoDAO();
        List<Pedido> pedidos = dao.listarTodos();

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
}
