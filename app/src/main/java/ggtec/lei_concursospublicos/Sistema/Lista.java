package ggtec.lei_concursospublicos.Sistema;

import android.app.Activity;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import ggtec.lei_concursospublicos.R;

/**
 * Created by Vagner on 11/02/2016.
 */
public class Lista {

    private ViewGroup loadView = null;
    private ViewGroup erroView = null;
    private TextView textoErro = null;
    private boolean onStop = false;

    //versao 2
    private View activity = null;
    private FrameLayout load = null;
    private FrameLayout erro = null;

    protected void addViewRoot(View activity){
        this.activity = activity;
    }

    protected void iniciarLoad(){
        fecharLoad();
        if(activity != null){
            if(load == null){
                load = (FrameLayout) activity.findViewById(R.id.load_conteiner);
            }
            if(load != null){
                load.setVisibility(View.VISIBLE);
            }
        }
    }

    protected void fecharLoad(){
        if(activity != null){
            if(load == null){
                load = (FrameLayout) activity.findViewById(R.id.load_conteiner);
            }
            if(load != null){
                load.setVisibility(View.GONE);
            }
        }
    }

    protected void enviarErro(String mensagem){
        fecharLoad();
        if(activity != null){
            if(erro == null){
                erro = (FrameLayout) activity.findViewById(R.id.erro_conteiner);
            }
            if(erro != null){
                erro.setVisibility(View.VISIBLE);
                TextView textView = (TextView) activity.findViewById(R.id.erro_mensagem);
                textView.setText(mensagem);
            }
        }
    }

    protected void fecharErro(){
        if(activity != null){
            if(erro == null){
                erro = (FrameLayout) activity.findViewById(R.id.erro_conteiner);
            }
            if(load != null){
                erro.setVisibility(View.GONE);
            }
        }
    }

    protected void envarResposta(){
        fecharErro();
        fecharLoad();
    }

    protected ItemInfoLei parseJsonInfolei(JSONObject jsonInfoLei) {
        ItemInfoLei infoLei = new ItemInfoLei();
        try {
            if (jsonInfoLei.has("ID")) {
                infoLei.setID(jsonInfoLei.getInt("ID"));
            }
            if (jsonInfoLei.has("LinkCompilado")) {
                if(jsonInfoLei.getString("LinkCompilado").equalsIgnoreCase("")||jsonInfoLei.getString("LinkCompilado") == null){
                    infoLei.setLink(jsonInfoLei.getString("Link"));
                }else {
                    infoLei.setLink(jsonInfoLei.getString("LinkCompilado"));
                }

            }
            if (jsonInfoLei.has("Nome")) {
                infoLei.setNome(jsonInfoLei.getString("Nome"));
            }
            if (jsonInfoLei.has("Ano")) {
                infoLei.setAno(jsonInfoLei.getInt("Ano"));
            }
            if (jsonInfoLei.has("Descricao")) {
                infoLei.setDescricao(jsonInfoLei.getString("Descricao"));
            }
            if (jsonInfoLei.has("Tabela")) {
                infoLei.setTabela(jsonInfoLei.getString("Tabela"));
            }
            if (jsonInfoLei.has("Numero")) {
                infoLei.setNumero(jsonInfoLei.getInt("Numero"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Debug.d("erro no json");
            enviarErro("Erro inesperado");
        }
        return infoLei;
    }

    protected ItemInfoLei parseCursoItemInfoLei(Cursor con, int position){
        con.moveToFirst();
        con.move(position);
        ItemInfoLei item = new ItemInfoLei();
        item.setNome(con.getString(con.getColumnIndex(Banco.KEY_NOME)));
        item.setNumero(con.getInt(con.getColumnIndex(Banco.KEY_NUMERO)));
        item.setDescricao(con.getString(con.getColumnIndex(Banco.KEY_DESCRICAO)));
        item.setAno(con.getInt(con.getColumnIndex(Banco.KEY_ANO)));
        item.setGrupo(con.getString(con.getColumnIndex(Banco.KEY_TIPO)));
        item.setTabela(con.getString(con.getColumnIndex(Banco.KEY_TABELA)));
        return item;
    }
}