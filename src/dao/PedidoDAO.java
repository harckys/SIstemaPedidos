package dao;

import model.Cliente;
import model.Pedido;
import model.PedidoItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {
    public boolean clienteTemPedidoPendente(int clienteId) {
        String sql = "SELECT id FROM pedido WHERE cliente_id = ? AND status = 'Pendente'";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public boolean salvar(Pedido pedido) {
        String sqlPedido = "INSERT INTO pedido (cliente_id, data, status, total) VALUES (?, ?, ?, ?)";
        String sqlItem = "INSERT INTO pedido_item (pedido_id, item_id, quantidade, subtotal) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtPedido = conn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS)) {
                stmtPedido.setInt(1, pedido.getCliente().getId());
                stmtPedido.setString(2, pedido.getDataHora());
                stmtPedido.setString(3, "Pendente");
                stmtPedido.setDouble(4, pedido.getTotal());
                stmtPedido.executeUpdate();

                ResultSet generatedKeys = stmtPedido.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int pedidoId = generatedKeys.getInt(1);

                    for (PedidoItem pi : pedido.getItens()) {
                        try (PreparedStatement stmtItem = conn.prepareStatement(sqlItem)) {
                            stmtItem.setInt(1, pedidoId);
                            stmtItem.setInt(2, pi.getItem().getId());
                            stmtItem.setInt(3, pi.getQuantidade());
                            stmtItem.setDouble(4, pi.getSubtotal());
                            stmtItem.executeUpdate();
                        }
                    }
                }

                conn.commit();
                return true;

            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Pedido> listarTodos() {
        List<Pedido> lista = new ArrayList<>();
        String sql = """
            SELECT p.id, p.data, p.status, p.total,
                   c.id as cliente_id, c.nome as cliente_nome
            FROM pedido p
            JOIN cliente c ON p.cliente_id = c.id
            ORDER BY p.data DESC
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pedido p = new Pedido();
                Cliente c = new Cliente();

                p.setId(rs.getInt("id"));
                p.setDataHora(rs.getString("data"));
                p.setStatus(rs.getString("status"));
                p.setTotal(rs.getDouble("total"));

                c.setId(rs.getInt("cliente_id"));
                c.setNome(rs.getString("cliente_nome"));

                p.setCliente(c);

                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    public boolean atualizarStatus(int pedidoId, String novoStatus) {
        String sql = "UPDATE pedido SET status = ? WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, novoStatus);
            stmt.setInt(2, pedidoId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<String[]> relatorioPorCliente() {
        List<String[]> lista = new ArrayList<>();

        String sql = """
        SELECT c.nome,
               SUM(CASE WHEN p.status = 'Pendente' THEN 1 ELSE 0 END) AS pendente,
               SUM(CASE WHEN p.status = 'Em Processamento' THEN 1 ELSE 0 END) AS processamento,
               SUM(CASE WHEN p.status = 'Finalizado' THEN 1 ELSE 0 END) AS finalizado,
               SUM(CASE WHEN p.status = 'Cancelado' THEN 1 ELSE 0 END) AS cancelado,
               SUM(CASE WHEN p.status = 'Finalizado' THEN p.total ELSE 0 END) AS total_gasto
        FROM cliente c
        LEFT JOIN pedido p ON p.cliente_id = c.id
        GROUP BY c.id, c.nome
        ORDER BY c.nome
    """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String[] linha = new String[6];
                linha[0] = rs.getString("nome");
                linha[1] = rs.getString("pendente");
                linha[2] = rs.getString("processamento");
                linha[3] = rs.getString("finalizado");
                linha[4] = rs.getString("cancelado");
                linha[5] = String.format("R$ %.2f", rs.getDouble("total_gasto"));
                lista.add(linha);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }


}
