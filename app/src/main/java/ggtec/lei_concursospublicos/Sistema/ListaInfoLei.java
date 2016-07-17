package ggtec.lei_concursospublicos.Sistema;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ggtec.lei_concursospublicos.R;

/**
 * Created by Vagner on 25/11/2015.
 */
public class ListaInfoLei extends Lista {

    //private CRUD crud;
    private Cursor cursor;
    private Context context;
    private View activity;
    private String[] allColunas = {
            Banco.KEY_NOME,
            Banco.KEY_NUMERO,
            Banco.KEY_ANO,
            Banco.KEY_DESCRICAO,
            Banco.KEY_TABELA,
            Banco.KEY_FAVORITO,
            Banco.KEY_TIPO,
            Banco.KEY_OFF_LINE,
            Banco.KEY_DATE,
            Banco.KEY_LEI_ID,
            Banco.KEY_LIMIT};

    public static final String TIPO_ESTATUTO = "estatuto";
    public static final String TIPO_CODIGO = "codigo";
    public static final String TIPO_ADM = "adm";
    public static final String TIPO_PREV = "prev";
    public static final String TIPO_PENAL = "penal";

    private OnItemInfoLei observerAsync = null;

    public ListaInfoLei(Context con) {
        context = con;
    }

    public ListaInfoLei(Context con,View activity) {
        context = con;
        this.activity = activity;
    }

    public void getMaisAcessadas(final OnItemInfoLei observer) {
        addViewRoot(activity);
        iniciarLoad();
        new Link(context)
                .maisAcessadas()
                .send(new Link.OnJSONObject() {
                    @Override
                    public void resp(JSONObject jsonObject) {
                        try {
                            JSONArray jsonArray = jsonObject.getJSONArray("mais_acessadas");
                            ArrayList<ItemInfoLei> itemInfoLeis = new ArrayList<ItemInfoLei>();
                            for (int i = 0; jsonArray.length() > i; i++) {
                                itemInfoLeis.add(parseJsonInfolei(jsonArray.getJSONObject(i)));
                            }
                            observer.resp(itemInfoLeis);
                            itemInfoLeis = null;
                            jsonArray = null;
                            jsonObject = null;
                            envarResposta();
                        } catch (JSONException e) {
                            Debug.d("erro de json");
                            jsonObject = null;
                            enviarErro(context.getResources().getString(R.string.info_erro_geral));
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void erro(String tipo, String mensagem) {
                        enviarErro(mensagem);
                        mensagem = null;
                    }
                });
    }

    public void getGrupo(String grupo, OnItemInfoLei observer) {
        addViewRoot(activity);
        iniciarLoad();
        observerAsync = observer;
        new AsyncBanco().execute("grupo", grupo);
    }

    public void getHistorico(OnItemInfoLei observer) {
        addViewRoot(activity);
        iniciarLoad();
        observerAsync = observer;
        new AsyncBanco().execute("historico");
    }


    public interface OnItemInfoLei {
        void resp(ArrayList<ItemInfoLei> itemInfoLeis);
    }

    private class AsyncBanco extends AsyncTask<String, Void, ArrayList<ItemInfoLei>> {
        protected ArrayList<ItemInfoLei> doInBackground(String... urls) {
            //busca no banco
            ArrayList<ItemInfoLei> lista = new ArrayList<>();
            Cursor cursor = null;
            if (urls[0].equalsIgnoreCase("grupo")) {
                cursor = Banco.getIntance(context).getReadableDatabase().query(Banco.TABLE_LEIS, allColunas, Banco.KEY_TIPO + "='" + urls[1] + "'", null, null, null, Banco.KEY_ANO + " DESC");
            }
            if (urls[0].equalsIgnoreCase("historico")) {
                cursor = Banco.getIntance(context).getReadableDatabase().query(Banco.TABLE_LEIS, allColunas, Banco.KEY_FAVORITO + "='true'", null, null, null, Banco.KEY_DATE + " DESC");
            }
            int cont = cursor.getCount();
            if (cont > 0) {
                for (int i = 0; cont > i; i++) {
                    lista.add(parseCursoItemInfoLei(cursor, i));
                }
            }
            return lista;
        }

        protected void onPostExecute(ArrayList<ItemInfoLei> result) {
            if (observerAsync != null) {
                observerAsync.resp(result);
            }
            envarResposta();
        }
    }


    public int getLimitHistorico(String tab_num) {
        int resp = 1;
        String[] tn = tab_num.split("_");
        if (tn[0] != null && tn[1] != null) {
            Cursor cursor = Banco.getIntance(context).getReadableDatabase().query(
                    Banco.TABLE_LEIS, allColunas, Banco.KEY_TABELA + "='" + tn[0] + "' AND " + Banco.KEY_NUMERO + "='" + tn[1] + "'", null, null, null, null, "0,1");
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                resp = cursor.getInt(cursor.getColumnIndex(Banco.KEY_LIMIT));
            }
        }
        return resp;
    }

    public Boolean hasHistorico() {
        Boolean resp = false;
        cursor = Banco.getIntance(context).getReadableDatabase().query(Banco.TABLE_LEIS, allColunas, Banco.KEY_FAVORITO + "='true'", null, null, null, Banco.KEY_DATE + " DESC");
        if (cursor.getCount() > 0) {
            resp = true;
        }
        return resp;
    }

    public void addHistorico(ItemInfoLei itemMenu, ItemLei itemLei) {
        //buscar se a lei existe localmente
        Cursor cursor = Banco.getIntance(context).getReadableDatabase().query(Banco.TABLE_LEIS, allColunas, Banco.KEY_TABELA + "='" + itemMenu.getTabela() + "' AND " + Banco.KEY_NUMERO + "='" + itemMenu.getNumero() + "'", null, null, null, null);
        int time = (int) (System.currentTimeMillis());
        if (cursor.getCount() > 0) {
            //if atualizar
            ContentValues values = new ContentValues();
            values.put(Banco.KEY_DATE, time);
            values.put(Banco.KEY_FAVORITO, "true");
            values.put(Banco.KEY_LEI_ID, itemLei.getLeiID());
            values.put(Banco.KEY_LIMIT, itemLei.getID() + "");
            int result = Banco.getIntance(context).getReadableDatabase().update(Banco.TABLE_LEIS, values, Banco.KEY_TABELA + "='" + itemMenu.getTabela() + "' AND " + Banco.KEY_NUMERO + "='" + itemMenu.getNumero() + "'", null);
            if (result == 1) {
                Debug.d("Atualizado");
            }
        } else {
            //else criar novo regitro
            ContentValues values = new ContentValues();
            values.put(Banco.KEY_NOME, itemMenu.getNome());
            values.put(Banco.KEY_NUMERO, itemMenu.getNumero());
            values.put(Banco.KEY_ANO, itemMenu.getAno());
            values.put(Banco.KEY_DESCRICAO, itemMenu.getDescricao());
            values.put(Banco.KEY_TABELA, itemMenu.getTabela());
            values.put(Banco.KEY_DATE, time);
            values.put(Banco.KEY_FAVORITO, "true");
            values.put(Banco.KEY_LEI_ID, itemLei.getLeiID());
            values.put(Banco.KEY_LIMIT, itemLei.getID() + "");
            values.put(Banco.KEY_TIPO, itemMenu.getGrupo());
            long result = Banco.getIntance(context).getWritableDatabase().insert(Banco.TABLE_LEIS, null, values);
            if (result != -1) {
                Debug.d("Criado " + itemMenu.getNumero() + "/" + itemMenu.getNumero());
            }
        }
    }

    public void pesquisa(String numero,String ano,String descricao, final OnItemInfoLei observer){
        addViewRoot(activity);
        iniciarLoad();
        new Link(context).pesquisa(numero,ano,descricao)
                .send(new Link.OnJSONObject() {
                    @Override
                    public void resp(JSONObject jsonObject) {
                        try {
                            JSONArray jsonArray = jsonObject.getJSONArray("pesquisa");
                            ArrayList<ItemInfoLei> itemInfoLeis = new ArrayList<ItemInfoLei>();
                            for(int i=0;jsonArray.length()>i;i++){
                                itemInfoLeis.add(parseJsonInfolei(jsonArray.getJSONObject(i)));
                            }
                            observer.resp(itemInfoLeis);
                            envarResposta();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            enviarErro("erro inesperado");
                        }
                    }

                    @Override
                    public void erro(String tipo, String mensagem) {
                        enviarErro(mensagem);
                    }
                });
    }
}