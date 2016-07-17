package ggtec.lei_concursospublicos.Sistema;

import ggtec.lei_concursospublicos.R;

/**
 * Created by Vagner on 29/11/2015.
 */
public class ItemInfoLei {
    private int ID = -1;
    private int numero = -1;
    private int ano = -1;
    private String nome = "";
    private String descricao = "";
    private String grupo = "";
    private String tabela = "";
    private String link = "";

    public ItemInfoLei(){

    }

    public void setID(int id){
        this.ID = id;
    }

    public int getID(){
        return this.ID;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setNumero(int numero){
        this.numero = numero;
    }

    public int getNumero(){
        return this.numero;
    }

    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return this.descricao.replaceAll("\n|\t|\r"," ").trim();
    }

    public void setAno(int ano){
        this.ano = ano;
    }

    public int getAno(){
        return this.ano;
    }

    public void setLink(String link){
        this.link = link;
    }

    public String getLink(){
        return this.link;
    }

    public void setTabela(String tabela){
        this.tabela = tabela;
    }

    public String getTabela(){
        return this.tabela;
    }

    public void setGrupo(String grupo){
        this.grupo = grupo;
    }

    public String getGrupo(){
        return this.grupo;
    }



    public int getColorGrupo() {
        String tipo = this.grupo;
        int resp = R.color.colorAccent;
        if(tipo != null) {
            if (tipo.equalsIgnoreCase(ListaInfoLei.TIPO_ESTATUTO)) {
                resp = R.color.colorEstatuto;
            } else if (tipo.equalsIgnoreCase(ListaInfoLei.TIPO_CODIGO)) {
                resp = R.color.colorCodigo;
            } else if (tipo.equalsIgnoreCase(ListaInfoLei.TIPO_ADM)) {
                resp = R.color.colorAdm;
            } else if (tipo.equalsIgnoreCase(ListaInfoLei.TIPO_PREV)) {
                resp = R.color.colorPrev;
            } else if (tipo.equalsIgnoreCase(ListaInfoLei.TIPO_PENAL)) {
                resp = R.color.colorPenal;
            }
        }
        return resp;
    }

    public String getTab_num(){
        return this.tabela+"_"+this.numero;
    }

    public String getTipoLeiHumano() {
        String tipo = this.tabela;
        String resp = "";
        if(tipo.equalsIgnoreCase("lo")){
            resp = "Lei ";
        }else if (tipo.equalsIgnoreCase("lc")){
            resp = "Lei complementar ";
        }else if(tipo.equalsIgnoreCase("d")){
            resp = "Decreto ";
        }else if(tipo.equalsIgnoreCase("dl")){
            resp = "Decreto-Lei ";
        }else if(tipo.equalsIgnoreCase("cf")){
            resp = "Contituição Federal";
        }
        return resp;
    }

    public String getNome() {
        if(nome.equalsIgnoreCase("null")|| nome == null || nome.equalsIgnoreCase("")){
            nome = descricao;
            if(nome.equalsIgnoreCase("null") || descricao == null || nome.equalsIgnoreCase("")){
                nome = numero+"";
            }
        }
        return nome.replaceAll("\n|\t|\r"," ").trim();
    }


    public String getGrupoNomeHumano() {
        String tipo = this.grupo;
        String resp = "";
        switch (tipo) {
            case "estatuto":
                resp = "Estatuto";
                break;
            case "codigo":
                resp = "Código";
                break;
            case "adm":
                resp = "Dir. Adm.";
                break;
            case "prev":
                resp = "Dir. Prev.";
                break;
            case "penal":
                resp = "Dir.Penal";
                break;
            default:
                resp = "";
                break;
        }
        return resp;
    }

    public String getNumAnoFormat(){
        if(this.tabela.equalsIgnoreCase("cf")){
            return "CF/88";
        }
        String a = this.ano+"";
        if(a.length() == 4){
            a = a.substring(2,4);
        }
        String num = numero+"";
        if(num.length() > 3){
            int tm = num.length();
            int quebra  = tm - 3;
            String ini = num.substring(0,quebra);
            String fim = num.substring(quebra);
            num = ""+ini+"."+fim;
        }
        return num+"/"+a;
    }
}
