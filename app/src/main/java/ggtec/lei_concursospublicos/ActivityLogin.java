package ggtec.lei_concursospublicos;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import ggtec.lei_concursospublicos.Outros.BaseAnalytics;
import ggtec.lei_concursospublicos.Sistema.Usuario;

public class ActivityLogin extends AppCompatActivity {

    LinearLayout telaEscolha;
    LinearLayout telaCadastroLogin;

    Button btnSend;
    //trio de tratamento de  erro
    FrameLayout progressBar;
    FrameLayout progressBarVisitante;
    TextView textErro;

    EditText campoEmail;
    EditText campoSenha;
    EditText campoNome;

    TextView tipo;
    FrameLayout conteinerErro;

    String nome = null;
    String email = null;
    String senha = null;

    public static final String KEY_TIPO = "tipo";
    public static final int CADASTRO = 13;
    public static final int LOGIN = 15;

    int escolha = 0;
    Boolean externo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inicializar();
        if (getIntent() != null) {
            escolha = getIntent().getIntExtra(KEY_TIPO, 0);
            if (escolha == CADASTRO) {
                externo = true;
                cadastro(null);
            }
            if (escolha == LOGIN) {
                externo = true;
                login(null);
            }
        }else {
            externo = false;
        }
    }

    private void inicializar() {
        setContentView(R.layout.layout_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        telaEscolha = (LinearLayout) findViewById(R.id.escolha);
        telaCadastroLogin = (LinearLayout) findViewById(R.id.cadastro_login);
        telaEscolha.setVisibility(View.VISIBLE);
        telaCadastroLogin.setVisibility(View.GONE);
        campoEmail = (EditText) findViewById(R.id.email);
        campoSenha = (EditText) findViewById(R.id.senha);
        campoNome = (EditText) findViewById(R.id.nome);
        tipo = (TextView) findViewById(R.id.text_tipo);
        btnSend = (Button) findViewById(R.id.btn_send);
        progressBar = (FrameLayout) findViewById(R.id.load_conteiner);
        progressBarVisitante = (FrameLayout) findViewById(R.id.progress_bar_login);
        conteinerErro = (FrameLayout) findViewById(R.id.erro_conteiner);
        textErro = (TextView) findViewById(R.id.erro_mensagem);
        ImageView imagem = (ImageView) findViewById(R.id.erro_ilustration);
        imagem.setVisibility(View.GONE);
        campoNome.setVisibility(View.GONE);
        textErro.setTextColor(getResources().getColor(R.color.colorPenal));
    }

    public void cadastro(View v) {
        telaEscolha.setVisibility(View.GONE);
        telaCadastroLogin.setVisibility(View.VISIBLE);
        tipo.setText("Cadastro");
        btnSend.setText("Cadastrar!");
        campoNome.setVisibility(View.VISIBLE);
        escolha = CADASTRO;
    }

    public void login(View v) {
        telaEscolha.setVisibility(View.GONE);
        telaCadastroLogin.setVisibility(View.VISIBLE);
        campoNome.setVisibility(View.GONE);
        tipo.setText("Login");
        btnSend.setText("Fazer Login!");
        escolha = LOGIN;
    }

    public void visitante(View v) {
        progressBarVisitante.setVisibility(View.VISIBLE);
        Intent intent = new Intent(getBaseContext(), ActivityMain.class);
        startActivity(intent);
    }

    public void sendCadastroLogin(View v) {
        conteinerErro.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        if (escolha == CADASTRO) {
            //verificação from-end
            String error = Usuario.validarCadastro(campoNome.getText().toString(),campoEmail.getText().toString(),campoSenha.getText().toString());
            if (!error.equalsIgnoreCase("valido")) {
                textErro.setText(error);
                conteinerErro.setVisibility(View.VISIBLE);
            } else {
                nome = campoNome.getText().toString().trim();
                email = campoEmail.getText().toString().trim();
                senha = Usuario.parseMD5(campoSenha.getText().toString().trim());
                Usuario.getInstance(getBaseContext()).addRootView(findViewById(R.id.root))
                .cadastro(nome, email, senha, new Usuario.OnBoll() {
                    @Override
                    public void resp(Boolean boll) {
                        Intent intent = new Intent(getBaseContext(), ActivityMain.class);
                        startActivity(intent);
                        BaseAnalytics.getInstance(getBaseContext()).evento(BaseAnalytics.CAT_SISTEMA, "Novo cadastro", null);
                        finish();
                    }
                });
            }
        }
        if (escolha == LOGIN) {
            //envia para webService
            String error = Usuario.validarLogin(campoEmail.getText().toString(),campoSenha.getText().toString());
            if (!error.equalsIgnoreCase("valido")) {
                textErro.setText(error);
                conteinerErro.setVisibility(View.VISIBLE);
            } else {
                email = campoEmail.getText().toString().trim();
                senha = Usuario.parseMD5(campoSenha.getText().toString().trim());
                Usuario.getInstance(getBaseContext()).addRootView(findViewById(R.id.root))
                        .login(email, senha, new Usuario.OnBoll() {
                            @Override
                            public void resp(Boolean boll) {
                                Intent intent = new Intent(getBaseContext(), ActivityMain.class);
                                startActivity(intent);
                                BaseAnalytics.getInstance(getBaseContext()).evento(BaseAnalytics.CAT_SISTEMA, "Logou no app", null);
                            }
                        });
            }
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(telaEscolha.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        if (telaEscolha.getVisibility() == View.VISIBLE || externo) {
            super.onBackPressed();
        } else {
            telaEscolha.setVisibility(View.VISIBLE);
            telaCadastroLogin.setVisibility(View.GONE);
            campoNome.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            conteinerErro.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStop(){
        super.onStop();
        finish();
    }
}