package delete;

import br.com.rpinfo.analuisa.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeleteCidades {

    public void deletarCidade(int id) {
        String limparEnderecos = "UPDATE enderecos SET cida_id = NULL WHERE cida_id = ?";
        String deletarCidade = "DELETE FROM cidades WHERE id = ?";

        try (Connection connect = Conexao.conectar()) {
            connect.setAutoCommit(false);

            try (PreparedStatement stmtLimpar = connect.prepareStatement(limparEnderecos);
                 PreparedStatement stmtDelete = connect.prepareStatement(deletarCidade)) {

                stmtLimpar.setInt(1, id);
                int enderecosAtualizados = stmtLimpar.executeUpdate();

                stmtDelete.setInt(1, id);
                int linhasAfetadas = stmtDelete.executeUpdate();

                if (linhasAfetadas > 0) {
                    connect.commit();
                    System.out.println("\nCidade deletada com sucesso!");
                    System.out.println("Endereços desvinculados dessa cidade: " + enderecosAtualizados);
                } else {
                    connect.rollback();
                    System.out.println("\nNenhuma cidade encontrada com esse ID.");
                }
            } catch (Exception e) {
                connect.rollback();
                throw e;
            }

        } catch (Exception e) {
            System.out.println("\nErro ao deletar cidade: " + e.getMessage());
        }
    }
}