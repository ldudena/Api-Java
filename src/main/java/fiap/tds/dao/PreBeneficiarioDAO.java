package fiap.tds.dao;

import fiap.tds.tdbentities.PreBeneficiario;
import fiap.tds.infra.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PreBeneficiarioDAO {

    public void cadastrar(PreBeneficiario pb) throws SQLException {
        String sqlPre = "INSERT INTO T_BC_PRE_BENEFICIARIO (nm_pre_beneficiario, dt_nascimento, cpf_pre_beneficiario, sx_pre_beneficiario, ds_problema_dentario, st_situacao, id_programa_social, id_endereco, id_solicitante) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlUpdateSolicitante = "UPDATE T_BC_SOLICITANTE SET st_solicitante = 'I' WHERE id_solicitante = ?";

        String sqlRelacaoTriagem = "INSERT INTO T_BC_REL_TRIAGEM_PRE_BENEF (id_triagem, id_pre_beneficiario) VALUES (?, ?)";

        Connection conn = null;
        try {
            conn = DatabaseConfig.getConnection();
            conn.setAutoCommit(false);

            EnderecoDAO enderecoDAO = new EnderecoDAO();
            int idEnderecoGerado = enderecoDAO.cadastrar(pb.getEndereco(), conn);

            int idPreBeneficiarioGerado = 0;

            try (PreparedStatement ps = conn.prepareStatement(sqlPre, new String[]{"ID_PRE_BENEFICIARIO"})) {
                ps.setString(1, pb.getNmPreBeneficiario());
                ps.setDate(2, java.sql.Date.valueOf(pb.getDtNascimento()));
                ps.setString(3, pb.getCpfPreBeneficiario());
                ps.setString(4, pb.getSxPreBeneficiario());
                ps.setString(5, pb.getDsProblemaDentario());
                ps.setString(6, pb.getStSituacao());
                ps.setInt(7, pb.getIdProgramaSocial());
                ps.setInt(8, idEnderecoGerado);
                ps.setInt(9, pb.getIdSolicitante());

                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        idPreBeneficiarioGerado = rs.getInt(1);
                    }
                }
            }

            if (idPreBeneficiarioGerado == 0) {
                throw new SQLException("Falha ao cadastrar Pré-Beneficiário, ID não obtido.");
            }

            try (PreparedStatement psRel = conn.prepareStatement(sqlRelacaoTriagem)) {
                psRel.setInt(1, pb.getIdTriagem());
                psRel.setInt(2, idPreBeneficiarioGerado);
                psRel.executeUpdate();
            }

            try (PreparedStatement psUpd = conn.prepareStatement(sqlUpdateSolicitante)) {
                psUpd.setInt(1, pb.getIdSolicitante());
                psUpd.executeUpdate();
            }

            conn.commit();

        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    public List<PreBeneficiario> listar() throws SQLException {
        List<PreBeneficiario> preBeneficiarios = new ArrayList<>();
        String sql = "SELECT * FROM T_BC_PRE_BENEFICIARIO";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                PreBeneficiario pb = new PreBeneficiario(
                        rs.getInt("id_pre_beneficiario"),
                        rs.getString("nm_pre_beneficiario"),
                        rs.getString("cpf_pre_beneficiario"),
                        rs.getString("sx_pre_beneficiario"),
                        rs.getString("ds_problema_dentario"),
                        rs.getString("st_situacao"),
                        rs.getInt("id_programa_social"),
                        rs.getInt("id_solicitante"),
                        rs.getInt("id_endereco")
                );
                preBeneficiarios.add(pb);
            }
        }
        return preBeneficiarios;
    }

    public void atualizar(PreBeneficiario pb) throws SQLException {
        String sql = "UPDATE T_BC_PRE_BENEFICIARIO SET nm_pre_beneficiario = ?, cpf_pre_beneficiario = ?, sx_pre_beneficiario = ?, ds_problema_dentario = ?, st_situacao = ?, id_programa_social = ?, id_solicitante = ?, id_endereco = ? WHERE id_pre_beneficiario = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pb.getNmPreBeneficiario());
            ps.setString(2, pb.getCpfPreBeneficiario());
            ps.setString(3, pb.getSxPreBeneficiario());
            ps.setString(4, pb.getDsProblemaDentario());
            ps.setString(5, pb.getStSituacao());
            ps.setInt(6, pb.getIdProgramaSocial());
            ps.setInt(7, pb.getIdSolicitante());
            ps.setInt(8, pb.getIdEndereco());
            ps.setInt(9, pb.getIdPreBeneficiario());

            ps.executeUpdate();
        }
    }

    public void atualizarDadosCompletos(PreBeneficiario pb) throws SQLException {
        String sql = "UPDATE T_BC_PRE_BENEFICIARIO SET nm_pre_beneficiario = ?, cpf_pre_beneficiario = ?, ds_problema_dentario = ? WHERE id_pre_beneficiario = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pb.getNmPreBeneficiario());
            ps.setString(2, pb.getCpfPreBeneficiario());
            ps.setString(3, pb.getDsProblemaDentario());
            ps.setInt(4, pb.getIdPreBeneficiario());
            ps.executeUpdate();
        }
    }

    public void atualizarStatus(int id, String novoStatus) throws SQLException {
        String sql = "UPDATE T_BC_PRE_BENEFICIARIO SET st_situacao = ? WHERE id_pre_beneficiario = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, novoStatus);
            ps.setInt(2, id);

            ps.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {

        String sqlDeleteRelTriagem = "DELETE FROM T_BC_REL_TRIAGEM_PRE_BENEF WHERE id_pre_beneficiario = ?";

        String sqlDeleteBeneficiario = "DELETE FROM T_BC_BENEFICIARIO WHERE id_pre_beneficiario = ?";

        String sqlDeletePreBeneficiario = "DELETE FROM T_BC_PRE_BENEFICIARIO WHERE id_pre_beneficiario = ?";

        Connection conn = null;
        try {
            conn = DatabaseConfig.getConnection();

            conn.setAutoCommit(false);

            try (PreparedStatement psRel = conn.prepareStatement(sqlDeleteRelTriagem)) {
                psRel.setInt(1, id);
                psRel.executeUpdate();
            }

            try (PreparedStatement psBen = conn.prepareStatement(sqlDeleteBeneficiario)) {
                psBen.setInt(1, id);
                psBen.executeUpdate();
            }

            try (PreparedStatement psPre = conn.prepareStatement(sqlDeletePreBeneficiario)) {
                psPre.setInt(1, id);
                psPre.executeUpdate();
            }

            conn.commit();

        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
}