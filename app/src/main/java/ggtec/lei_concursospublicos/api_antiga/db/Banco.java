package ggtec.lei_concursospublicos.api_antiga.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ggtec.lei_concursospublicos.api_antiga.Debug;

import static android.provider.BaseColumns._ID;

/**
 * Created by Vagner on 07/04/2015.
 */
public class Banco extends SQLiteOpenHelper {

    private static Banco banco = null;

    private static final int DATABASE_VERSION = 3;

    private static final String DATABASE_NAME = "concursoPublico";

    // Tabela Login
    public static final String TABLE_LOGIN = "login";
    public static final String KEY_NOME = "Nome";
    public static final String KEY_EMAIL = "Email";
    public static final String KEY_SENHA = "Senha";
    public static final String KEY_ID = "IDsistema";
    public static final String KEY_PRAZO = "Prazo";
    private static final String CREATE_LOGIN = "CREATE TABLE " + TABLE_LOGIN + " (" +_ID +" , "+ KEY_NOME + " TEXT , "+ KEY_EMAIL + " TEXT , "+ KEY_SENHA + " TEXT , "+KEY_ID+" TEXT  , "+KEY_PRAZO+" TEXT );";

    //Tabela Leis
    public static final String TABLE_LEIS = "leis";
    public static final String KEY_NUMERO = "Numero";
    public static final String KEY_ANO = "Ano";
    public static final String KEY_DESCRICAO = "Descricao";
    public static final String KEY_TABELA = "Tabela";
    public static final String KEY_FAVORITO = "Favorito";
    public static final String KEY_TIPO = "Tipo";
    public static final String KEY_OFF_LINE = "Off_line";
    private static final String CREATE_LEIS = "CREATE TABLE "+TABLE_LEIS+" (" +_ID +",  "+KEY_NUMERO+" TEXT," +"  "+KEY_NOME+" TEXT," +"  "+KEY_ANO+" TEXT," +"  "+KEY_DESCRICAO+" TEXT," +"    "+KEY_TABELA+" TEXT" +", "+KEY_FAVORITO+" TEXT, "+KEY_TIPO+" TEXT, "+KEY_OFF_LINE+" TEXT);";

    //Tabela Configuracao
    public static final  String TABLE_CONFIGURCAO = "configuracao";
    public static final String KEY_FONTE = "fonte";
    public static final String KEY_DICA_LEI = "dicaslei";
    public static final String KEY_DICA_EDICAO = "dicasleiedicao";
    public static final String KEY_LIMIT_TIME_PLAYER = "limitplayer";
    private static final String CREATE_CONFIG = "CREATE TABLE "+TABLE_CONFIGURCAO+" ("+_ID+", "+KEY_FONTE+" TEXT, "+KEY_DICA_LEI+" TEXT, "+KEY_DICA_EDICAO+" TEXT);";

    //Tabela lei
    public static final String KEY_DATE = "Data_hora";
    public static final String KEY_LIMIT = "Limit_ID";
    public static final String KEY_LEI_ID = "lei_ID";

    private static final String ALTER_HISTORICO_LIMIT = "ALTER TABLE "+TABLE_LEIS+" ADD "+KEY_LIMIT+" TEXT";
    private static final String ALTER_HISTORICO_DATE = "ALTER TABLE "+TABLE_LEIS+" ADD "+KEY_DATE+" int (32)";
    private static final String ALTER_HISTORICO_LEI_ID = "ALTER TABLE "+TABLE_LEIS+" ADD "+KEY_LEI_ID+"  TEXT";

    //Tabela lei
    public static final String KEY_CONFIG_VERSAO_ANTES = "versoantes";

    private static final String ALTER_CONFIG_VERSAO = "ALTER TABLE "+TABLE_CONFIGURCAO+" ADD "+KEY_CONFIG_VERSAO_ANTES+" TEXT";

    private Banco(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static Banco getIntance(Context context){
        if(banco == null){
            banco = new Banco(context);
        }
        return banco;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LOGIN);
        db.execSQL(CREATE_LEIS);
        db.execSQL(CREATE_CONFIG);
        //versao 2 add coluna KEY_LIMIT_TIME_PLAYER em TABLE_CONFIGURCAO
        String up1for2 = "ALTER TABLE "+TABLE_CONFIGURCAO+" ADD "+KEY_LIMIT_TIME_PLAYER+" TEXT";
        db.execSQL(up1for2);
        //versao 3 Add colunas na  tabela leis
        db.execSQL(ALTER_HISTORICO_LEI_ID);
        db.execSQL(ALTER_HISTORICO_LIMIT);
        db.execSQL(ALTER_HISTORICO_DATE);
        db.execSQL(ALTER_CONFIG_VERSAO);
        Debug.d("Banco Criado");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion == 1 && newVersion == 3){
            String up1for2 = "ALTER TABLE "+TABLE_CONFIGURCAO+" ADD "+KEY_LIMIT_TIME_PLAYER+" TEXT";
            db.execSQL(up1for2);
            //versao 3 Add colunas na  tabela leis
            db.execSQL(ALTER_HISTORICO_LEI_ID);
            db.execSQL(ALTER_HISTORICO_LIMIT);
            db.execSQL(ALTER_HISTORICO_DATE);
            db.execSQL(ALTER_CONFIG_VERSAO);
            Debug.d("Banco Atualizado de v1 para v3");
        }
        if(oldVersion == 2 && newVersion == 3){
            //versao 3 Add colunas na  tabela leis
            db.execSQL(ALTER_HISTORICO_LEI_ID);
            db.execSQL(ALTER_HISTORICO_LIMIT);
            db.execSQL(ALTER_HISTORICO_DATE);
            db.execSQL(ALTER_CONFIG_VERSAO);
            db.execSQL("UPDATE "+TABLE_CONFIGURCAO+" SET "+KEY_FONTE+"='20'");
            db.execSQL("UPDATE "+TABLE_CONFIGURCAO+" SET "+KEY_CONFIG_VERSAO_ANTES+"='5'");
            Debug.d("Banco Atualizado de v2 para v3");
        }
    }
}