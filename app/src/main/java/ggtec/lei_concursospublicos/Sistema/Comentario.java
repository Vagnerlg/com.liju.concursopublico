package ggtec.lei_concursospublicos.Sistema;

/**
 * Created by Vagner on 11/02/2016.
 */
public class Comentario {
    private int  ID = -1;
    private int usuarioID = -1;
    private String leiID = null;
    private String data = null;
    private String texto = null;
    private String alteracao = null;
    private int positivo = -1;
    private int negativo = -1;
    private Boolean publico = true;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(int usuarioID) {
        this.usuarioID = usuarioID;
    }

    public String getLeiID() {
        return leiID;
    }

    public void setLeiID(String leiID) {
        this.leiID = leiID;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getAlteracao() {
        return alteracao;
    }

    public void setAlteracao(String alteracao) {
        this.alteracao = alteracao;
    }

    public int getPositivo() {
        return positivo;
    }

    public void setPositivo(int positivo) {
        this.positivo = positivo;
    }

    public int getNegativo() {
        return negativo;
    }

    public void setNegativo(int negativo) {
        this.negativo = negativo;
    }

    public Boolean getPublico() {
        return publico;
    }

    public void setPublico(Boolean publico) {
        this.publico = publico;
    }
}
