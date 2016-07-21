package ggtec.lei_concursospublicos.api_antiga;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ggtec.lei_concursospublicos.api_antiga.net.Link;

/**
 * Created by Vagner on 02/12/2015.
 */
public class Trecho {

    private int id = -1;
    private String texto = null;
    private String lei_id = null;
    private int tipo = -1;
    private String classe;

    private String marcacao = null;
    private Boolean selecionado = false;
    private Comentario myComentario = null;
    private List<Comentario> comentariosPublico = new ArrayList<>();

    protected Trecho(int id,String lei_id,int tipo,String classe,String texto){
        this.id = id;
        this.lei_id = lei_id;
        this.tipo = tipo;
        this.classe = classe;
        this.texto = texto;
    }

    public int getID(){
        return this.id;
    }

    public String getLeiID(){
        return this.lei_id;
    }

    public int getTipo(){
        return this.tipo;
    }


    public String getClasse() {
        return classe;
    }

    public String getTexto() {
        return  texto.replaceAll("\n|\t"," ");
    }

    protected void setComentario(Comentario comentario){
        this.myComentario = comentario;
    }

    public Comentario getComentario(){
        return this.myComentario;
    }

    protected void addComentarioPublico(Comentario comentario){
        comentariosPublico.add(comentario);
    }

    public List<Comentario> getComentariosPublico(){

        return Collections.unmodifiableList(comentariosPublico);
    }


    public String getMarcacao() {
        return marcacao;
    }

    public Boolean getSelecionado(){
        return selecionado;
    }

    public void setComentario(final String comentario, final Boolean publico,final String tab_num,final Context con) {
        if(myComentario == null){
            myComentario = new Comentario();
        }
        this.myComentario.setTexto(comentario);
        this.myComentario.setPublico(publico);

        if(tab_num != null){
            new Link(con).setComentario(tab_num, this.lei_id, Usuario.getInstance(con).getId(), comentario, publico)
                    .send(new Link.OnJSONObject() {
                        @Override
                        public void resp(JSONObject jsonObject) {
                            Toast.makeText(con, "Comentario salvo!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void erro(String tipo, String mensagem) {
                            Debug.d("Não salvo na webService: Público" + publico + ": " + comentario);
                        }
                    });
        }
    }

    protected void setMarcacao(String marcacao){
        this.marcacao = marcacao;
    }

    public void setMarcacao(String marcacao,String tab_num, final Context con) {
        this.marcacao = marcacao;
        if(tab_num != null){
            new Link(con).setMarcacao(tab_num, this.lei_id, Usuario.getInstance(con).getId(), marcacao)
                    .send(new Link.OnJSONObject() {
                        @Override
                        public void resp(JSONObject jsonObject) {
                            Toast.makeText(con, "Marcacao salva!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void erro(String tipo, String mensagem) {
                            Debug.d("Não salvo na webService");
                        }
                    });
        }
    }

    public void setSelecionado(Boolean selecionado){
        this.selecionado = selecionado;
    }
}
