import view.*;
import dao.CriarTabela;
import javax.swing.*;
import dao.UsuarioSeeder;



public class App {
    public static void main(String[] args) {
        CriarTabela.inicializar();
        UsuarioSeeder.inserirUsuarioPadrao();

        String[] opcoes = {
                "1 - Login",
                "2 - Cadastro de Cliente",
                "3 - Cadastro de Item",
                "4 - Novo Pedido",
                "5 - Listar Pedidos",
                "6 - Atualizar Status de Pedido",
                "7 - Relatório de Pedidos por Cliente",
                "8 - Sair",
                "9 - Ajuda"
        };

        while (true) {
            String escolha = (String) JOptionPane.showInputDialog(null,
                    "Menu Principal", "Sistema de Pedidos",
                    JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]);

            if (escolha == null || escolha.startsWith("8")) {
                break; // sair
            }

            switch (escolha.charAt(0)) {
                case '1': new LoginView(); break;
                case '2': new ClientesView(); break;
                case '3': new ItemView(); break;
                case '4': new NovoPedidoView(); break;
                case '5': new ListaPedidosView(); break;
                case '6': new AtualizarStatusView(); break;
                case '7': new RelatorioClienteView(); break;
                case '9': new AjudaView(); break;

                default: break;
            }
        }
    }
}
