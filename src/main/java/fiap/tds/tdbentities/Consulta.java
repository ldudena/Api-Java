package fiap.tds.tdbentities;

import java.time.LocalDate;
import java.time.LocalTime;

public class Consulta {
    private int idConsulta;
    private String dsProntuario;
    private LocalDate dtConsulta;
    private LocalTime hrConsulta;
    private int idBeneficiario;
    private int idDentista;
    private int idEndereco;
    private String dsRecomendacao;
    private String nmLogradouro;
    private int nrLogradouro;
    private String nmBairro;
    private String nmCidade;

    public Consulta() {
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public String getDsProntuario() {
        return dsProntuario;
    }

    public void setDsProntuario(String dsProntuario) {
        this.dsProntuario = dsProntuario;
    }

    public LocalDate getDtConsulta() {
        return dtConsulta;
    }

    public void setDtConsulta(LocalDate dtConsulta) {
        this.dtConsulta = dtConsulta;
    }

    public LocalTime getHrConsulta() {
        return hrConsulta;
    }

    public void setHrConsulta(LocalTime hrConsulta) {
        this.hrConsulta = hrConsulta;
    }

    public int getIdBeneficiario() {
        return idBeneficiario;
    }

    public void setIdBeneficiario(int idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }

    public int getIdDentista() {
        return idDentista;
    }

    public void setIdDentista(int idDentista) {
        this.idDentista = idDentista;
    }


    public int getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    public String getDsRecomendacao() {
        return dsRecomendacao;
    }

    public void setDsRecomendacao(String dsRecomendacao) {
        this.dsRecomendacao = dsRecomendacao;
    }

    public String getNmLogradouro() {
        return nmLogradouro;
    }

    public void setNmLogradouro(String nmLogradouro) {
        this.nmLogradouro = nmLogradouro;
    }

    public int getNrLogradouro() {
        return nrLogradouro;
    }

    public void setNrLogradouro(int nrLogradouro) {
        this.nrLogradouro = nrLogradouro;
    }

    public String getNmBairro() {
        return nmBairro;
    }

    public void setNmBairro(String nmBairro) {
        this.nmBairro = nmBairro;
    }

    public String getNmCidade() {
        return nmCidade;
    }

    public void setNmCidade(String nmCidade) {
        this.nmCidade = nmCidade;
    }
}