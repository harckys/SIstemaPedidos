package view;

import dao.PedidoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class RelatorioClienteView extends JFrame {
    private JTable tabela;
    private DefaultTableModel modelo;

    public RelatorioClienteView() {
        super("Relatório de Pedidos por Cliente");

        modelo = new DefaultTableModel(
                new Object[]{"Cliente", "Pendente", "Em Processamento", "Finalizado", "Cancelado", "Total Gasto"}, 0);
        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);

        setLayout(null);
        scroll.setBounds(20, 20, 720, 300);
        add(scroll);

        carregarRelatorio();

        setSize(780, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void carregarRelatorio() {
        modelo.setRowCount(0);
        PedidoDAO dao = new PedidoDAO();
        List<String[]> relatorio = dao.relatorioPorCliente();

        for (String[] linha : relatorio) {
            modelo.addRow(linha);
        }
    }
}
