package ggtec.lei_concursospublicos.api_antiga;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ggtec.lei_concursospublicos.api_antiga.db.Banco;
import ggtec.lei_concursospublicos.api_antiga.net.Link;

import static java.lang.Long.*;

/**
 * Created by Vagner on 17/05/2015.
 */
public class Usuario extends Lista {
    private static Usuario usuario;
    private Context context;

    private String id = "0";//ID referente a tabela do sistema no site
    private String nome = "";
    private String email = "";
    private String prazo = "0";
    private String senha = "";
    private Boolean busca = true;
    private final String DB = "DB_Usuario";
    private boolean premium = false;

    public static int SEM_CONEXAO = 0;
    public static int ERRO = -1;
    public static int SUCESS = 1;
    public static int EMAIL_DUPLO = 2;

    private Usuario(Context con) {
        context = con;

        boolean status = false;
        String[] colunas = {"Nome", "IDsistema", "Email", "Senha", "Prazo"};
        Cursor cursor = Banco.getIntance(context).getReadableDatabase().query(Banco.TABLE_LOGIN, colunas, null, null, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            this.nome = cursor.getString(0);
            this.id = cursor.getString(1);
            this.email = cursor.getString(2);
            this.senha = cursor.getString(3);
            this.prazo = cursor.getString(4);
            if ((this.prazo == null)) {
                this.prazo = "0";
            }
        } else {
            this.nome = "Visitante";
            this.id = "0";
            this.email = "";
            this.prazo = "0";
        }
        Debug.d("User:" + this.id + " " + this.nome);
    }

    public static Usuario getInstance(Context context) {
        if (usuario == null) {
            usuario = new Usuario(context);
        }
        return usuario;
    }

    public int getId() {
        return Integer.parseInt(this.id);
    }

    public String getNome() {
        return this.nome;
    }

    public String getEmail() {
        return this.email;
    }

    public String getSenha() {
        return this.senha;
    }


    public interface OnBoll {
        void resp(Boolean boll);
    }

    public interface OnString {
        void resp(String resp);
    }

    public void setNome(final String newNome, final OnBoll bool) {
        new Link(context).setNomeUser(this.getId(), this.getSenha(), newNome)
                .send(new Link.OnJSONObject() {
                    @Override
                    public void resp(JSONObject jsonObject) {
                        ContentValues values = new ContentValues();
                        values.put(Banco.KEY_NOME, newNome);
                        int res = Banco.getIntance(context).getWritableDatabase().update(Banco.TABLE_LOGIN, values, null, null);
                        if (res > 0) {
                            nome = newNome;
                            bool.resp(true);
                        } else {
                            bool.resp(false);
                        }
                        Debug.d("Tempo Net ");
                    }

                    @Override
                    public void erro(String tipo, String mensagem) {
                        bool.resp(false);
                    }
                });
    }

    public void setEmail(final String newEmail, final OnString bool) {
        new Link(context)
                .setEmailUser(this.getId(), this.getSenha(), newEmail)
                .send(new Link.OnJSONObject() {
                    @Override
                    public void resp(JSONObject jsonObject) {
                        ContentValues values = new ContentValues();
                        values.put(Banco.KEY_EMAIL, newEmail);
                        int res = Banco.getIntance(context).getWritableDatabase().update(Banco.TABLE_LOGIN, values, null, null);
                        if (res > 0) {
                            email = newEmail;
                            bool.resp("Sucess");
                        } else {
                            bool.resp("erro");
                        }
                    }

                    @Override
                    public void erro(String tipo, String mensagem) {
                        bool.resp(mensagem);
                    }
                });
    }

    public Boolean isCadastrado() {
        Boolean resp = false;
        if (this.id == "0") {
            resp = false;
        } else {
            resp = true;
        }
        return resp;
    }

    int userPrazo;
    int now;
    Boolean resp;

    public void isPremium(final OnBoll boll) {
        resp = false;
        userPrazo = Integer.parseInt(this.prazo);
        now = 0;
        if (isCadastrado()) {
            //now en segundos
            GregorianCalendar time = new GregorianCalendar();
            Long tm = time.getTimeInMillis();
            String tempo = tm.toString();
            int co = tempo.length();
            co = co - 3;
            String finaltempo = tempo.substring(0, co);
            now = Integer.parseInt(finaltempo);

            if (now > userPrazo) {
                //internet com busca de prazo
                new Link(context)
                        .getPrazo(id)
                        .send(new Link.OnJSONObject() {
                            @Override
                            public void resp(JSONObject jsonObject) {
                                if (jsonObject.has("prazo")) {
                                    try {
                                        userPrazo = jsonObject.getInt("prazo");
                                        if (now < userPrazo) {
                                            //update prazo
                                            ContentValues values = new ContentValues();
                                            values.put(Banco.KEY_PRAZO, userPrazo + "");
                                            Banco.getIntance(context).getWritableDatabase().update(Banco.TABLE_LOGIN, values, null, null);
                                            Debug.d(true + " now:" + now + " prazo:" + userPrazo);
                                            boll.resp(true);
                                        } else {
                                            Debug.d(false + "");
                                            boll.resp(false);
                                        }

                                    } catch (JSONException e) {
                                        boll.resp(false);
                                        e.printStackTrace();
                                        Debug.d("erro json usuario premum" + jsonObject.toString());
                                    }
                                }
                            }

                            @Override
                            public void erro(String tipo, String mensagem) {
                                Debug.d("controle usuario " + mensagem);
                                boll.resp(false);
                            }
                        });
            } else {
                Debug.d(true + " now:" + now + " prazo:" + userPrazo);
                boll.resp(true);
            }
        } else {
            boll.resp(false);
        }
    }

    public void UpdatePremium(final OnBoll boll) {
        GregorianCalendar time = new GregorianCalendar();
        Long tm = time.getTimeInMillis();
        String tempo = tm.toString();
        int co = tempo.length();
        co = co - 3;
        String finaltempo = tempo.substring(0, co);
        now = Integer.parseInt(finaltempo);
        resp = false;
        userPrazo = Integer.parseInt(this.prazo);
        if (isCadastrado()) {
            new Link(context)
                    .getPrazo(id)
                    .send(new Link.OnJSONObject() {
                        @Override
                        public void resp(JSONObject jsonObject) {
                            if (jsonObject.has("prazo")) {
                                try {
                                    userPrazo = jsonObject.getInt("prazo");
                                    if (now < userPrazo) {
                                        //update prazo
                                        ContentValues values = new ContentValues();
                                        values.put(Banco.KEY_PRAZO, userPrazo + "");
                                        Banco.getIntance(context).getWritableDatabase().update(Banco.TABLE_LOGIN, values, null, null);
                                        Debug.d(true + " now:" + now + " prazo:" + userPrazo);
                                        boll.resp(true);
                                    } else {
                                        Debug.d(false + "");
                                        boll.resp(false);
                                    }
                                } catch (JSONException e) {
                                    boll.resp(false);
                                    e.printStackTrace();
                                    Debug.d("erro json usuario premum" + jsonObject.toString());
                                }
                            }
                        }

                        @Override
                        public void erro(String tipo, String mensagem) {
                            Debug.d("controle usuario " + mensagem);
                            boll.resp(false);
                        }
                    });
        }
    }

    public String premiumLimit() {
        if (!this.prazo.equalsIgnoreCase("0")) {
            Long prazo = parseLong(this.prazo + "000");
            Date date = new Date(prazo);
            Calendar cal = new GregorianCalendar();
            Long now = cal.getTimeInMillis();
            Log.d(DB, "now " + now);
            Log.d(DB, "premiumLimit" + this.prazo);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yy");
            sdf.setCalendar(cal);
            cal.setTime(date);
            return sdf.format(date);
        }
        return "";
    }

    private void cadastroInterno(String IDsistema, String Nome, String Email, String Senha) {
        ContentValues values = new ContentValues();
        values.put("IDsistema", IDsistema);
        values.put("Nome", Nome);
        values.put("Email", Email);
        values.put("Senha", Senha);
        if (Banco.getIntance(context).getWritableDatabase().insert(Banco.TABLE_LOGIN, null, values) != -1) {
            usuario = new Usuario(context);
        }
    }

    public static String parseMD5(String senha) {
        String s = senha;
        String resp = null;
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
            m.update(s.getBytes(), 0, s.length());
            resp = new BigInteger(1, m.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return resp;
    }

    public static String validarCadastro(String nome, String email, String senha) {
        if (nome.isEmpty()) {
            return "Campo nome é obrigatório.";
        }
        return validarLogin(email, senha);
    }

    public static String validarLogin(String email, String senha) {
        if (senha.length() < 6 || senha.length() > 12) {
            return "Campo Senha deve ter entre 6 a 12 caracteres.";
        }
        if (!email.matches(".+@.+\\.[a-z]+")) {
            return "Email inválido. Informe um email válido.";
        }
        return "valido";
    }

    public Usuario addRootView(View root) {
        addViewRoot(root);
        return this;
    }

    public void cadastro(final String nome, final String email, final String senha, final OnBoll observer) {
        iniciarLoad();
        new Link(context).cadastroWebService(nome, email, senha)
                .send(new Link.OnJSONObject() {
                    @Override
                    public void resp(JSONObject jsonObject) {
                        try {
                            String id = jsonObject.getString("ID");
                            cadastroInterno(id, nome, email, senha);
                            observer.resp(true);
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

    public void login(final String email, final String senha, final OnBoll observer) {
        iniciarLoad();
        new Link(context).loginWebService(email, senha)
                .send(new Link.OnJSONObject() {
                    @Override
                    public void resp(JSONObject jsonObject) {
                        try {
                            String id = jsonObject.getString("ID");
                            String nome = jsonObject.getString("Nome");
                            cadastroInterno(id, nome, email, senha);
                            observer.resp(true);
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