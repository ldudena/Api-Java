package fiap.tds.tdbentities;

public class Solicitante {

    private int idSolicitante;
    private String nmSolicitante;
    private String nmResponsavel; // Pode ser null no banco
    private String stLibras; // 'S' ou 'N'
    private String stSolicitante;
    private String nrTelefone;
    private String email;

    public Solicitante() {
    }

    public int getIdSolicitante() { return idSolicitante; }
    public void setIdSolicitante(int idSolicitante) { this.idSolicitante = idSolicitante; }

    public String getNmSolicitante() { return nmSolicitante; }
    public void setNmSolicitante(String nmSolicitante) { this.nmSolicitante = nmSolicitante; }

    public String getNmResponsavel() { return nmResponsavel; }
    public void setNmResponsavel(String nmResponsavel) { this.nmResponsavel = nmResponsavel; }

    public String getStLibras() { return stLibras; }
    public void setStLibras(String stLibras) { this.stLibras = stLibras; }

    public String getNrTelefone() { return nrTelefone; }
    public void setNrTelefone(String nrTelefone) { this.nrTelefone = nrTelefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getStSolicitante() { return stSolicitante; }
    public void setStSolicitante(String stSolicitante) { this.stSolicitante = stSolicitante; }
}