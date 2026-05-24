package fiap.tds.dao;

import fiap.tds.tdbentities.Consulta;
import fiap.tds.infra.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO {

    public void cadastrar(Consulta consulta) throws SQLException {
        String sql = "INSERT INTO T_BC_CONSULTA (dt_consulta, hr_consulta, id_beneficiario, id_dentista, id_endereco) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(consulta.getDtConsulta()));
            ps.setTime(2, java.sql.Time.valueOf(consulta.getHrConsulta()));
            ps.setInt(3, consulta.getIdBeneficiario());
            ps.setInt(4, consulta.getIdDentista());
            ps.setInt(5, consulta.getIdEndereco());
            ps.executeUpdate();
        }
    }

    public List<Consulta> listar() throws SQLException {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT * FROM T_BC_CONSULTA";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Consulta c = new Consulta();
                c.setIdConsulta(rs.getInt("id_consulta"));
                c.setDsProntuario(rs.getString("ds_prontuario"));
                if (rs.getDate("dt_consulta") != null) c.setDtConsulta(rs.getDate("dt_consulta").toLocalDate());
                if (rs.getTime("hr_consulta") != null) c.setHrConsulta(rs.getTime("hr_consulta").toLocalTime());
                c.setIdBeneficiario(rs.getInt("id_beneficiario"));
                c.setIdDentista(rs.getInt("id_dentista"));
                c.setIdEndereco(rs.getInt("id_endereco"));
                c.setDsRecomendacao(rs.getString("ds_recomendação"));

                consultas.add(c);
            }
        }
        return consultas;
    }

    public List<Consulta> listarProximasBeneficiario(int idBeneficiario) throws SQLException {
        List<Consulta> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_BC_CONSULTA WHERE id_beneficiario = ? AND dt_consulta >= TRUNC(SYSDATE) ORDER BY dt_consulta ASC, hr_consulta ASC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idBeneficiario);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Consulta c = new Consulta();
                    c.setIdConsulta(rs.getInt("id_consulta"));
                    c.setDsProntuario(rs.getString("ds_prontuario"));
                    if (rs.getDate("dt_consulta") != null) c.setDtConsulta(rs.getDate("dt_consulta").toLocalDate());
                    if (rs.getTime("hr_consulta") != null) c.setHrConsulta(rs.getTime("hr_consulta").toLocalTime());
                    c.setIdBeneficiario(rs.getInt("id_beneficiario"));
                    c.setIdDentista(rs.getInt("id_dentista"));
                    c.setIdEndereco(rs.getInt("id_endereco"));
                    c.setDsRecomendacao(rs.getString("ds_recomendação"));

                    lista.add(c);
                }
            }
        }
        return lista;
    }

    public List<Consulta> listarHistoricoBeneficiario(int idBeneficiario) throws SQLException {
        List<Consulta> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_BC_CONSULTA WHERE id_beneficiario = ? AND dt_consulta < TRUNC(SYSDATE) ORDER BY dt_consulta DESC, hr_consulta DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idBeneficiario);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Consulta c = new Consulta();
                    c.setIdConsulta(rs.getInt("id_consulta"));
                    c.setDsProntuario(rs.getString("ds_prontuario"));
                    if (rs.getDate("dt_consulta") != null) c.setDtConsulta(rs.getDate("dt_consulta").toLocalDate());
                    if (rs.getTime("hr_consulta") != null) c.setHrConsulta(rs.getTime("hr_consulta").toLocalTime());
                    c.setIdBeneficiario(rs.getInt("id_beneficiario"));
                    c.setIdDentista(rs.getInt("id_dentista"));
                    c.setIdEndereco(rs.getInt("id_endereco"));
                    c.setDsRecomendacao(rs.getString("ds_recomendação"));

                    lista.add(c);
                }
            }
        }
        return lista;
    }

    public List<Consulta> listarProximasDentista(int idDentista) throws SQLException {
        List<Consulta> lista = new ArrayList<>();
        String sql = "SELECT c.id_consulta, c.ds_prontuario, c.dt_consulta, c.hr_consulta, c.id_beneficiario, c.id_dentista, c.id_endereco, c.ds_recomendação, " +
                "e.nm_logradouro, e.nr_logradouro, e.nm_bairro, e.nm_cidade " +
                "FROM T_BC_CONSULTA c " +
                "LEFT JOIN T_BC_ENDERECO e ON c.id_endereco = e.id_endereco " +
                "WHERE c.id_dentista = ? AND c.dt_consulta >= TRUNC(SYSDATE) ORDER BY c.dt_consulta ASC, c.hr_consulta ASC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idDentista);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Consulta c = new Consulta();
                    c.setIdConsulta(rs.getInt("id_consulta"));
                    c.setDsProntuario(rs.getString("ds_prontuario"));
                    if (rs.getDate("dt_consulta") != null) c.setDtConsulta(rs.getDate("dt_consulta").toLocalDate());
                    if (rs.getTime("hr_consulta") != null) c.setHrConsulta(rs.getTime("hr_consulta").toLocalTime());
                    c.setIdBeneficiario(rs.getInt("id_beneficiario"));
                    c.setIdDentista(rs.getInt("id_dentista"));
                    c.setIdEndereco(rs.getInt("id_endereco"));
                    c.setDsRecomendacao(rs.getString("ds_recomendação"));
                    c.setNmLogradouro(rs.getString("nm_logradouro"));
                    c.setNrLogradouro(rs.getInt("nr_logradouro"));
                    c.setNmBairro(rs.getString("nm_bairro"));
                    c.setNmCidade(rs.getString("nm_cidade"));

                    lista.add(c);
                }
            }
        }
        return lista;
    }

    public List<Consulta> listarPendentesDentista(int idDentista) throws SQLException {
        List<Consulta> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_BC_CONSULTA WHERE id_dentista = ? AND dt_consulta < TRUNC(SYSDATE) AND ds_prontuario IS NULL";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idDentista);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Consulta c = new Consulta();
                    c.setIdConsulta(rs.getInt("id_consulta"));
                    c.setDsProntuario(rs.getString("ds_prontuario"));
                    if (rs.getDate("dt_consulta") != null) c.setDtConsulta(rs.getDate("dt_consulta").toLocalDate());
                    if (rs.getTime("hr_consulta") != null) c.setHrConsulta(rs.getTime("hr_consulta").toLocalTime());
                    c.setIdBeneficiario(rs.getInt("id_beneficiario"));
                    c.setIdDentista(rs.getInt("id_dentista"));
                    c.setIdEndereco(rs.getInt("id_endereco"));
                    c.setDsRecomendacao(rs.getString("ds_recomendação"));

                    lista.add(c);
                }
            }
        }
        return lista;
    }

    public void atualizar(Consulta consulta) throws SQLException {
        String sql = "UPDATE T_BC_CONSULTA SET ds_prontuario = ?, dt_consulta = ?, hr_consulta = ?, id_beneficiario = ?, id_dentista = ? WHERE id_consulta = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, consulta.getDsProntuario());
            ps.setDate(2, Date.valueOf(consulta.getDtConsulta()));
            ps.setTime(3, Time.valueOf(consulta.getHrConsulta()));
            ps.setInt(4, consulta.getIdBeneficiario());
            ps.setInt(5, consulta.getIdDentista());
            ps.setInt(6, consulta.getIdConsulta());

            ps.executeUpdate();
        }
    }

    public void atualizarProntuario(int idConsulta, String dsProntuario) throws SQLException {
        String sql = "UPDATE T_BC_CONSULTA SET ds_prontuario = ? WHERE id_consulta = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dsProntuario);
            ps.setInt(2, idConsulta);
            ps.executeUpdate();
        }
    }

    public void atualizarRecomendacao(int idConsulta, String recomendacao) throws SQLException {
        String sql = "UPDATE T_BC_CONSULTA SET ds_recomendação = ? WHERE id_consulta = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, recomendacao);
            ps.setInt(2, idConsulta);
            ps.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM T_BC_CONSULTA WHERE id_consulta = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}