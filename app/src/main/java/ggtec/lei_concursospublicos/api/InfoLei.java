package ggtec.lei_concursospublicos.api;

/**
 * Created by Vagner on 20/07/2016.
 */
class InfoLei {
    private int numero = -1;
    private int ano = -1;
    private String nome = "";
    private String descricao = "";
    private String tag = "";
    private String tipo = "";

    InfoLei(String tipo, int numero,int ano,String nome,String descricao, String tag){
        this.numero = numero;
        this.ano = ano;
        this.nome = nome;
        this.descricao = descricao;
        this.tag = tag;
        this.tipo = tipo;
    }

    public int getNumero() {
        return numero;
    }

    public int getAno() {
        return ano;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getTag() {
        return tag;
    }

    public String getTipo() {
        return tipo;
    }
}
