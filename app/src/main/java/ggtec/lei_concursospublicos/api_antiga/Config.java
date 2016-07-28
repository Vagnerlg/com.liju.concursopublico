package ggtec.lei_concursospublicos.api_antiga;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import ggtec.lei_concursospublicos.api_antiga.db.Banco;

/**
 * Created by Vagner on 05/01/2016.
 */
public class Config {

    //private CRUD crud;
    private static Config config;
    private String[] cols = {Banco.KEY_FONTE, Banco.KEY_LIMIT_TIME_PLAYER, Banco.KEY_DICA_LEI, Banco.KEY_DICA_EDICAO};
    private int fonte = 12;
    private String timePlayer = "";
    private boolean dicaInicial = false;

    private Context context;

    private Config(Context context){
        this.context = context;
        Cursor cursor = Banco.getIntance(context).getWritableDatabase().query(Banco.TABLE_CONFIGURCAO,cols,null,null,null,null,null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            this.fonte = Integer.parseInt(cursor.getString(0));
            if(cursor.getString(1) != null) {
                this.timePlayer = cursor.getString(1);
            }else {
                this.timePlayer = "";
            }
            if(cursor.getString(2) != null) {
                String bol = cursor.getString(2);
                if(bol.equalsIgnoreCase("true")){
                    this.dicaInicial = true;
                }
            }
        }
    }

    public static Config getInstance(Context context){
        if(config == null){
            config = new Config(context);
        }
        return config;
    }

    public int getFonte(){
        return this.fonte;
    }

    public Boolean setFonte(int valor){
        Boolean resp = false;
        ContentValues values = new ContentValues();
        values.put(Banco.KEY_FONTE,""+valor);
        if(Banco.getIntance(context).getWritableDatabase().update(Banco.TABLE_CONFIGURCAO,values,null,null) > 0){
            this.fonte = valor;
            resp = true;
        }
        return resp;
    }

    public String getTimePlayer(){
        return this.timePlayer;
    }

    public Boolean setTimePlayer(String data){
        Boolean resp = false;
        ContentValues values = new ContentValues();
        values.put(Banco.KEY_LIMIT_TIME_PLAYER,data+"");
        if(Banco.getIntance(context).getWritableDatabase().update(Banco.TABLE_CONFIGURCAO,values,null,null) > 0){
            this.timePlayer = data;
            resp = true;
        }
        return resp;
    }

    public boolean getDicaInicial(){
        return dicaInicial;
    }

    public void noAutomatcDicaInicial(){
        ContentValues values = new ContentValues();
        values.put(Banco.KEY_DICA_LEI,"true");
        Banco.getIntance(context).getWritableDatabase().update(Banco.TABLE_CONFIGURCAO,values,null,null);
        this.dicaInicial = true;
    }
}