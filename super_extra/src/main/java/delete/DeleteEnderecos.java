package delete;

import br.com.rpinfo.analuisa.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeleteEnderecos {

    public void deletarEndereco(int id) {
        String limparClientes = "UPDATE clientes SET ende_id = NULL WHERE ende_id = ?";
        String deletarEndereco = "DELETE FROM enderecos WHERE id = ?";

        try (Connection connect = Conexao.conectar()) {
            connect.setAutoCommit(false);

            try (PreparedStatement stmtLimpar = connect.prepareStatement(limparClientes);
                 PreparedStatement stmtDelete = connect.prepareStatement(deletarEndereco)) {

                stmtLimpar.setInt(1, id);
                int clientesAtualizados = stmtLimpar.executeUpdate();

                stmtDelete.setInt(1, id);
                int linhasAfetadas = stmtDelete.executeUpdate();

                if (linhasAfetadas > 0) {
                    connect.commit();
                    System.out.println("\nEndereço deletado com sucesso!");
                    System.out.println("Clientes desvinculados desse endereço: " + clientesAtualizados);
                } else {
                    connect.rollback();
                    System.out.println("\nNenhum endereço encontrado com esse ID.");
                }
            } catch (Exception e) {
                connect.rollback();
                throw e;
            }

        } catch (Exception e) {
            System.out.println("\nErro ao deletar endereço: " + e.getMessage());
        }
    }
}