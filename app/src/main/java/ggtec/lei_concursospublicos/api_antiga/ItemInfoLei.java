package ggtec.lei_concursospublicos.api_antiga;

import ggtec.lei_concursospublicos.R;

/**
 * Created by Vagner on 29/11/2015.
 */
public class ItemInfoLei {
    private int numero = -1;
    private int ano = -1;
    private String nome = "";
    private String descricao = "";
    private String tag = "";
    private String tipo = "";



    public ItemInfoLei(String tipo, int numero,int ano,String nome,String descricao, String tag){
        this.numero = numero;
        this.ano = ano;
        this.nome = nome;
        this.descricao = descricao;
        this.tag = tag;
        this.tipo = tipo;
    }

    public String getNome() {
        if(nome.equalsIgnoreCase("null")|| nome == null || nome.equalsIgnoreCase("")){
            nome = getDescricao();
            if(nome.equalsIgnoreCase("null") || getDescricao() == null || nome.equalsIgnoreCase("")){
                nome = getNumero() +"";
            }
        }
        return nome.replaceAll("\n|\t|\r"," ").trim();
    }

    public String getNumAnoFormat(){
        if(this.getTipo().equalsIgnoreCase("cf")){
            return "CF/88";
        }
        String a = this.getAno() +"";
        if(a.length() == 4){
            a = a.substring(2,4);
        }
        String num = getNumero() +"";
        if(num.length() > 3){
            int tm = num.length();
            int quebra  = tm - 3;
            String ini = num.substring(0,quebra);
            String fim = num.substring(quebra);
            num = ""+ini+"."+fim;
        }
        return num+"/"+a;
    }




    public int getColorGrupo() {
     //   String tipo = this.grupo;
        int resp = R.color.colorAccent;
        if(getTipo() != null) {
            if (getTipo().equalsIgnoreCase(ListaInfoLei.TIPO_ESTATUTO)) {
                resp = R.color.colorEstatuto;
            } else if (getTipo().equalsIgnoreCase(ListaInfoLei.TIPO_CODIGO)) {
                resp = R.color.colorCodigo;
            } else if (getTipo().equalsIgnoreCase(ListaInfoLei.TIPO_ADM)) {
                resp = R.color.colorAdm;
            } else if (getTipo().equalsIgnoreCase(ListaInfoLei.TIPO_PREV)) {
                resp = R.color.colorPrev;
            } else if (getTipo().equalsIgnoreCase(ListaInfoLei.TIPO_PENAL)) {
                resp = R.color.colorPenal;
            }
        }
        return resp;
    }


    public String getTipoLeiHumano() {
       // String tipo = this.tabela;
        String resp = "";
        if(getTipo().equalsIgnoreCase("lo")){
            resp = "Lei ";
        }else if (getTipo().equalsIgnoreCase("lc")){
            resp = "Lei complementar ";
        }else if(getTipo().equalsIgnoreCase("d")){
            resp = "Decreto ";
        }else if(getTipo().equalsIgnoreCase("dl")){
            resp = "Decreto-Lei ";
        }else if(getTipo().equalsIgnoreCase("cf")){
            resp = "Contituição Federal";
        }
        return resp;
    }




    public String getGrupoNomeHumano() {
        //String tipo = this.grupo;
        String resp = "";
        switch (getTipo()) {
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


    public int getNumero() {
        return numero;
    }

    public int getAno() {
        return ano;
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
