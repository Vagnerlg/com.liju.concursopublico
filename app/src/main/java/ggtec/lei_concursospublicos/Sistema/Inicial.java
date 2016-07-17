package ggtec.lei_concursospublicos.Sistema;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;


/**
 * Created by Vagner on 17/05/2015.
 */
public class Inicial {
    private Context context;
    private OnResp observerAsync = null;

    public Inicial(Context con) {
        context = con;
    }

    private class Async extends AsyncTask<Integer, Void, Integer> {
        protected Integer doInBackground(Integer... urls) {
            Integer resp = -1;
            if (urls[0] == 0) {
                resp = versaoInterno();
            }
            return resp;
        }

        protected void onPostExecute(Integer result) {
            if (observerAsync != null) {
                observerAsync.resp(result);
            }
        }
    }

    public interface OnResp {
        void resp(Integer resp);
    }

    public void versao(final OnResp observer) {
        observerAsync = new OnResp() {
            @Override
            public void resp(Integer resp) {
                if (resp == 1) {
                    createversao1();
                }
                if (resp == 2) {
                    upVersao2();
                }
                if (resp == 3) {
                    upVersao3();
                }
                if (resp == 4) {
                    upVersao4();
                }
                if (resp == 5) {
                    upVersao5();
                }
                observer.resp(1);
                Banco.getIntance(context).close();
            }
        };
        new Async().execute(0);
    }

    private int versaoInterno() {
        int resp = 0;
        String[] coluna = {Banco.KEY_TIPO, Banco.KEY_NOME};
        Cursor cur;
        cur = Banco.getIntance(context).getReadableDatabase().query(Banco.TABLE_LEIS, coluna, null, null, null, null, null);
        cur.moveToFirst();
        if (cur.getCount() == 0) {
            resp = 1;
        } else {
            cur = Banco.getIntance(context).getReadableDatabase().query(Banco.TABLE_LEIS, coluna, Banco.KEY_TIPO + "='prev'", null, null, null, Banco.KEY_TIPO);
            cur.moveToFirst();
            if (cur.getCount() == 0) {
                resp = 2;
            } else {
                cur = Banco.getIntance(context).getReadableDatabase().query(Banco.TABLE_LEIS, coluna, Banco.KEY_TIPO + "='penal'", null, null, null, Banco.KEY_TIPO);
                cur.moveToFirst();
                if (cur.getCount() == 0) {
                    resp = 3;
                } else {
                    Cursor versao4 = Banco.getIntance(context).getReadableDatabase().query(Banco.TABLE_LEIS, coluna, Banco.KEY_NUMERO + "='12850'", null, null, null, Banco.KEY_TIPO);
                    cur.moveToFirst();
                    if (versao4.getCount() == 0) {
                        resp = 4;
                    }
                }
            }
        }
        return resp;
    }

    public void createversao1() {
        String[] coluna = {"Nome"};
        Cursor cur = Banco.getIntance(context).getReadableDatabase().query(Banco.TABLE_LEIS, coluna, null, null, null, null, null);
        cur.moveToFirst();

        //versão 1 do app
        if (cur.getCount() == 0) {

            //codigos
            //inserircodigo(String numero, String nome,String ano, String descricao, String tabela, String tipo,String favorito, String offline){
            inserircodigo("1002", "Código de Processo Penal Militar ", "1969", "Código de Processo Penal Militar", "dl", "codigo", "false", "false");
            inserircodigo("88", "Constituição Federal", "1988", "Constituição Federal de 1988", "cf", "cf", "false", "false");
            inserircodigo("1001", "Código Penal Militar", "1969", "Código Penal Militar", "dl", "codigo", "false", "false");
            inserircodigo("227", "Código de Minas", "1967", "Dá nova redação ao Decreto-lei nº 1.985, de 29 de janeiro de 1940. (Código de Minas)", "dl", "codigo", "false", "false");
            inserircodigo("3689", "Código de Processo Penal", "1941", "Código de Processo Penal.", "dl", "codigo", "false", "false");
            inserircodigo("2848", "Código Penal", "1940", "Código Penal.", "dl", "codigo", "false", "false");
            inserircodigo("12651", "Código Florestal", "2012", "Dispõe sobre a proteção da vegetação nativa; altera as Leis nos 6.938, de 31 de agosto de 1981, 9.393, de 19 de dezembro de 1996, e 11.428, de 22 de dezembro de 2006; revoga as Leis nos 4.771, de 15 de setembro de 1965, e 7.754, de 14 de abril de 1989, e a Medida Provisória nº 2.166-67, de 24 de agosto de 2001; e dá outras providências.", "lo", "codigo", "false", "false");
            inserircodigo("10406", "Código Civil", "2002", "Institui o Código Civil.", "lo", "codigo", "false", "false");
            inserircodigo("9503", "Código de Trânsito Brasileiro", "1997", "Institui o Código de Trânsito Brasileiro.", "lo", "codigo", "false", "false");
            inserircodigo("8078", "Código de Defesa do Consumidor", "1990", "Dispõe sobre a proteção do consumidor e dá outras providências.", "lo", "codigo", "false", "false");
            inserircodigo("7565", "Código Brasileiro de Aeronáutica", "1986", "Dispõe sobre o Código Brasileiro de Aeronáutica.", "lo", "codigo", "false", "false");
            inserircodigo("5869", "Código de Processo    Civil", "1973", "Institui o Código de Processo Civil.", "lo", "codigo", "false", "false");
            inserircodigo("5172", "Código Tributário Nacional", "1966", "Dispõe sobre o Sistema Tributário Nacional e institui normas gerais de direito tributário aplicáveis à União, Estados e Municípios.", "lo", "codigo", "false", "false");
            inserircodigo("4737", "Código Eleitoral", "1965", "Institui o Código Eleitoral.", "lo", "codigo", "false", "false");
            inserircodigo("4117", "Código Brasileiro de Telecomunicações", "1962", "Institui o Código Brasileiro de Telecomunicações.", "lo", "codigo", "false", "false");
            inserircodigo("556", "Código Comercial", "1850", "Código Comercial", "lo", "codigo", "false", "false");
            inserircodigo("1171", "Código de Ética Profissional do Servidor Público", "1994", "Aprova o Código de Ética Profissional do Servidor Público Civil do Poder Executivo Federal.", "d", "codigo", "false", "false");
            inserircodigo("24643", "Código de Águas", "1934", "Decreta o Código de Águas.", "d", "codigo", "false", "false");
            inserircodigo("5452", "Consolidação das Leis do Trabalho", "1943", "Aprova a Consolidação das Leis do Trabalho.", "dl", "codigo", "false", "false");
            //estatutos
            inserircodigo("123", "Estatuto Nacional da Microempresa e da Empresa de Pequeno Porte", "2006", "Institui o \n" +
                    "\tEstatuto Nacional da Microempresa e da Empresa de Pequeno Porte; altera \n" +
                    "\tdispositivos das Leis número 8.212 e 8.213, ambas de 24 de \n" +
                    "\tjulho de 1991, da Consolidação das Leis do Trabalho - CLT, aprovada pelo \n" +
                    "\tDecreto-Lei número 5.452, de 1º de maio de \n" +
                    "\t1943, da Lei número 10.189, de 14 de fevereiro de 2001, da Lei \n" +
                    "\tComplementar número 63, de 11 de janeiro de 1990; e revoga as \n" +
                    "\tLeis número 9.317, de 5 de dezembro de 1996, e 9.841, de 5 de \n" +
                    "\toutubro de 1999.", "lc", "estatuto", "false", "false");

            inserircodigo("12852", "Estatuto da Juventude", "2013", "Institui o Estatuto da Juventude e dispõe sobre os direitos dos jovens, os princípios e diretrizes das políticas públicas de juventude e o Sistema Nacional de Juventude SINAJUVE.", "lo", "estatuto", "false", "false");
            inserircodigo("12288", "Estatuto da Igualdade Racial", "2010", "Institui o Estatuto da Igualdade Racial; altera as Leis nos 7.716, de 5 de janeiro de 1989, 9.029, de 13 de abril de 1995, 7.347, de 24 de julho de 1985, e 10.778, de 24 de novembro de 2003.", "lo", "estatuto", "false", "false");
            inserircodigo("11904", "Estatuto de Museus", "2009", "Institui o Estatuto de Museus e dá outras providências.", "lo", "estatuto", "false", "false");
            inserircodigo("10826", "Estatuto do Desarmamento", "2003", "Dispõe sobre registro, posse e comercialização de armas de fogo e munição, sobre o Sistema Nacional\r\n    de Armas &#150; Sinarm, define crimes e dá outras providências.", "lo", "estatuto", "false", "false");
            inserircodigo("10741", "Estatuto do Idoso", "2003", "Dispõe sobre o Estatuto do Idoso e dá outras providências.", "lo", "estatuto", "false", "false");
            inserircodigo("10671", "Estatuto de Defesa do Torcedor", "2003", "Dispõe sobre o Estatuto de Defesa do Torcedor e \r\n	dá outras providências.", "lo", "estatuto", "false", "false");
            inserircodigo("10257", "Estatuto da Cidade", "2001", "Regulamenta\r\n    os arts. 182 e 183 da Constituição Federal, estabelece diretrizes gerais da política\r\n    urbana e dá outras providências.", "lo", "estatuto", "false", "false");
            inserircodigo("9474", "Estatuto dos Refugiados", "1997", "Define\r\n    mecanismos para a implementação do Estatuto dos Refugiados de 1951, e determina outras\r\n    providências.", "lo", "estatuto", "false", "false");
            inserircodigo("8906", "Estatuto da    Advocacia e a Ordem dos Advogados do Brasil (OAB)", "1994", "Dispõe sobre o Estatuto da\r\n    Advocacia e a Ordem dos Advogados do Brasil (OAB).", "lo", "estatuto", "false", "false");
            inserircodigo("8069", "Estatuto da Criança e do Adolescente", "1990", "Dispõe\r\n    sobre o Estatuto da Criança e do Adolescente e dá outras providências.", "lo", "estatuto", "false", "false");
            inserircodigo("6880", "Estatuto dos    Militares", "1980", "Dispõe sobre o Estatuto dos\r\n    Militares. ", "lo", "estatuto", "false", "false");
            inserircodigo("6815", "Estatuto do Estrangeiro", "1980", "Define\r\n    a situação jurídica do estrangeiro no Brasil, cria o Conselho Nacional de Imigração.", "lo", "estatuto", "false", "false");
            inserircodigo("6001", "Estatuto do    Índio", "1973", "Dispõe sobre o Estatuto do\r\n    Índio. ", "lo", "estatuto", "false", "false");
            inserircodigo("4504", "Estatuto da    Terra", "1964", "Dispõe sobre o Estatuto da\r\n    Terra, e dá outras providências.", "lo", "estatuto", "false", "false");

            //administracao
            inserircodigo("1171", "Código de Ética Profissional do Servidor Público", "1994", "Aprova o Código de Ética Profissional do Servidor Público Civil do Poder Executivo Federal.", "d", "adm", "false", "false");
            inserircodigo("200", "Organização da Administração Federal", "1967", "Dispõe\n" +
                    "    sôbre a organização da Administração Federal, estabelece diretrizes para a Reforma\n" +
                    "    Administrativa e dá outras providências.", "dl", "adm", "false", "false");
            inserircodigo("9790", "Organizações da Sociedade Civil de Interesse Público", "1999", "Dispõe sobre\n" +
                    "    a qualificação de pessoas jurídicas de direito privado, sem fins lucrativos, como\n" +
                    "    Organizações da Sociedade Civil de Interesse Público, institui e disciplina o Termo de\n" +
                    "    Parceria, e dá outras providências.", "lo", "adm", "false", "false");
            inserircodigo("9637", "Organizações sociais", "1998", "Dispõe sobre a qualificação de entidades como organizações\n" +
                    "    sociais, a criação do Programa Nacional de Publicização, a extinção dos órgãos e\n" +
                    "    entidades que menciona e a absorção de suas atividades por organizações sociais, e dá\n" +
                    "    outras providências.", "lo", "adm", "false", "false");
            inserircodigo("12527", "Acesso a informações", "2011", "\n" +
                    "Regula o \n" +
                    "acesso a informações previsto no inciso XXXIII do art. 5º, no \n" +
                    "inciso II do § 3º do art. 37 e no § 2º do \n" +
                    "art. 216 da Constituição Federal; altera a Lei nº 8.112, de 11 \n" +
                    "de dezembro de 1990; revoga a Lei nº 11.111, de 5 de maio de \n" +
                    "2005, e dispositivos da Lei nº 8.159, de 8 de janeiro de 1991; \n" +
                    "e dá outras providências.", "lo", "adm", "false", "false");
            inserircodigo("10520", "Modalidade de licitação denominada pregão", "2002", "Institui, no âmbito da União,\n" +
                    "    Estados, Distrito Federal e Municípios, nos termos do art. 37, inciso XXI, da\n" +
                    "    Constituição Federal, modalidade de licitação denominada pregão, para aquisição de\n" +
                    "    bens e serviços comuns, e dá outras providências.", "lo", "adm", "false", "false");
            inserircodigo("8429", "Improbidade administrativa", "1992", "Dispõe\n" +
                    "    sobre as sanções aplicáveis aos agentes públicos nos casos de enriquecimento ilícito\n" +
                    "    no exercício de mandato, cargo, emprego ou função na administração pública direta,\n" +
                    "    indireta ou fundacional e dá outras providências.", "lo", "adm", "false", "false");
            inserircodigo("9784", "Processo administrativo", "1999", "Regula o\n" +
                    "    processo administrativo no âmbito da Administração Pública Federal.", "lo", "adm", "false", "false");
            inserircodigo("8666", "licitações e    contratos", "1993", "Regulamenta o\n" +
                    "    art. 37, inciso XXI, da Constituição Federal, institui normas para licitações e\n" +
                    "    contratos da Administração Pública e dá outras providências.", "lo", "adm", "false", "false");

            //Configuracao
            ContentValues val = new ContentValues();
            val.put(Banco.KEY_FONTE, "14");
            val.put(Banco.KEY_DICA_EDICAO, "false");
            val.put(Banco.KEY_DICA_LEI, "false");
            Banco.getIntance(context).getWritableDatabase().insert(Banco.TABLE_CONFIGURCAO, null, val);
        }
        upVersao2();
        upVersao3();
        upVersao4();
        upVersao5();
    }

    public void upVersao2() {
        String[] coluna2 = {Banco.KEY_TIPO};
        Cursor cur2 = Banco.getIntance(context).getReadableDatabase().query(Banco.TABLE_LEIS, coluna2, Banco.KEY_TIPO + "='prev'", null, null, null, Banco.KEY_TIPO);
        cur2.moveToFirst();
        //versao 2 do app
        if (cur2.getCount() == 0) {
            String[] col = {"_id"};
            Cursor cur3 = Banco.getIntance(context).getReadableDatabase().query(Banco.TABLE_LEIS, col, Banco.KEY_NUMERO + "='8212' AND " + Banco.KEY_TABELA + "='lo'", null, null, null, Banco.KEY_TIPO);
            cur3.moveToFirst();
            if (cur3.getCount() == 0) {
                inserircodigo("8212", "Organização da Seguridade Social", "1991", "Dispõe sobre a organização da Seguridade Socia...", "lo", "prev", "false", "false");
            } else {
                cur3.moveToPosition(0);
                ContentValues values = new ContentValues();
                values.put(Banco.KEY_TIPO, "prev");
                values.put(Banco.KEY_NOME, "Organização da Seguridade Social");
                Banco.getIntance(context).getWritableDatabase().update(Banco.TABLE_LEIS, values, Banco.KEY_NUMERO + "='8212' AND " + Banco.KEY_TABELA + "='lo'", null);
            }
            cur3 = Banco.getIntance(context).getReadableDatabase().query(Banco.TABLE_LEIS, col, Banco.KEY_NUMERO + "='8213' AND " + Banco.KEY_TABELA + "='lo'", null, null, null, Banco.KEY_TIPO);
            cur3.moveToFirst();
            if (cur3.getCount() == 0) {
                inserircodigo("8213", "Planos de  Previdência Social", "1991", "Dispõe sobre os Planos de Benefícios da Previdênci...", "lo", "prev", "false", "false");
            } else {
                ContentValues values = new ContentValues();
                values.put(Banco.KEY_TIPO, "prev");
                values.put(Banco.KEY_NOME, "Planos de  Previdência Social");
                Banco.getIntance(context).getWritableDatabase().update(Banco.TABLE_LEIS, values, Banco.KEY_NUMERO + "='8213' AND " + Banco.KEY_TABELA + "='lo'", null);
            }
            cur3 = Banco.getIntance(context).getReadableDatabase().query(Banco.TABLE_LEIS, col, Banco.KEY_NUMERO + "='8742' AND " + Banco.KEY_TABELA + "='lo'", null, null, null, Banco.KEY_TIPO);
            cur3.moveToFirst();
            if (cur3.getCount() == 0) {
                inserircodigo("8742", "Assistência Social – LOAS", "1993", "Dispõe sobre a organização da Assistência Soci...", "lo", "prev", "false", "false");
            } else {
                ContentValues values = new ContentValues();
                values.put(Banco.KEY_TIPO, "prev");
                values.put(Banco.KEY_NOME, "Assistência Social – LOAS");
                Banco.getIntance(context).getWritableDatabase().update(Banco.TABLE_LEIS, values, Banco.KEY_NUMERO + "='8742' AND " + Banco.KEY_TABELA + "='lo'", null);
            }
            cur3 = Banco.getIntance(context).getReadableDatabase().query(Banco.TABLE_LEIS, col, Banco.KEY_NUMERO + "='3048' AND " + Banco.KEY_TABELA + "='d'", null, null, null, Banco.KEY_TIPO);
            cur3.moveToFirst();
            if (cur3.getCount() == 0) {
                inserircodigo("3048", "Regulamento da Previdência Social", "1999", "Aprova o Regulamento da Previdência Social, e ...", "d", "prev", "false", "false");
            } else {
                ContentValues values = new ContentValues();
                values.put(Banco.KEY_TIPO, "prev");
                values.put(Banco.KEY_NOME, "Regulamento da Previdência Social");
                Banco.getIntance(context).getWritableDatabase().update(Banco.TABLE_LEIS, values, Banco.KEY_NUMERO + "='3048' AND " + Banco.KEY_TABELA + "='d'", null);
            }
            cur3 = Banco.getIntance(context).getReadableDatabase().query(Banco.TABLE_LEIS, col, Banco.KEY_NUMERO + "='6214' AND " + Banco.KEY_TABELA + "='d'", null, null, null, Banco.KEY_TIPO);
            cur3.moveToFirst();
            if (cur3.getCount() == 0) {
                inserircodigo("6214", "Prestação continuada da assistência social à pessoa com deficiência e ao idoso", "2007", "Regulamenta o benefício de prestação continuada ...", "d", "prev", "false", "false");
            } else {
                ContentValues values = new ContentValues();
                values.put(Banco.KEY_TIPO, "prev");
                values.put(Banco.KEY_NOME, "Prestação continuada da assistência social à pessoa com deficiência e ao idoso");
                Banco.getIntance(context).getWritableDatabase().update(Banco.TABLE_LEIS, values, Banco.KEY_NUMERO + "='6214' AND " + Banco.KEY_TABELA + "='d'", null);
            }
        }
        upVersao3();
        upVersao4();
        upVersao5();
    }

    public void upVersao3() {
        String[] coluna2 = {Banco.KEY_TIPO};
        Cursor curPenal = Banco.getIntance(context).getReadableDatabase().query(Banco.TABLE_LEIS, coluna2, Banco.KEY_TIPO + "='penal'", null, null, null, Banco.KEY_TIPO);
        curPenal.moveToFirst();
        if (curPenal.getCount() == 0) {
            inserircodigo("3689", "Código de Processo Penal", "1941", "Código de Processo Penal.", "dl", "penal", "false", "false");
            inserircodigo("2848", "Código Penal", "1940", "Código Penal.", "dl", "penal", "false", "false");
            inserircodigo("8072", "Crimes hediondos", "1990", "Dispõe sobre os crimes hediondos, nos termos do art. 5º, inciso XLIII, da Constituição Federal, e determina outras providências.", "lo", "penal", "false", "false");
            inserircodigo("7960", "Prisão temporária", "1989", "Dispõe sobre prisão temporária.", "lo", "penal", "false", "false");
            inserircodigo("7716", "Crimes  de preconceito de raça ou de cor ", "1989", "Define os crimes resultantes de preconceito de raça ou de cor.	", "lo", "penal", "false", "false");
            inserircodigo("11343", "Políticas Públicas sobre Drogas - Sisnad", "2006", "Institui o Sistema Nacional de Políticas Públicas sobre Drogas - Sisnad; prescreve medidas para prevenção do uso indevido, atenção e reinserção social de usuários e dependentes de drogas; estabelece normas para repressão à produção não autorizada e ao tráfico ilícito de drogas; define crimes e dá outras providências.", "lo", "penal", "false", "false");
            inserircodigo("11340", "Maria da Penha – violência doméstica e familiar contra a mulher", "2006", "Cria mecanismos para coibir a violência doméstica e familiar contra a mulher, nos termos do § 8º do art. 226 da Constituição Federal, da Convenção sobre a Eliminação de Todas as Formas de Discriminação contra as Mulheres e da Convenção Interamericana para Prevenir, Punir e Erradicar a Violência contra a Mulher; dispõe sobre a criação dos Juizados de Violência Doméstica e Familiar contra a Mulher; altera o Código de Processo Penal, o Código Penal e a Lei de Execução Penal; e dá outras providências.", "lo", "penal", "false", "false");
            inserircodigo("10259", "juizados especiais cíveis e criminais no âmbito da Justiça Federal", "2001", "Dispõe sobre a instituição dos Juizados Especiais Cíveis e Criminais no âmbito da Justiça Federal.", "lo", "penal", "false", "false");
            inserircodigo("9605", "Lei dos crimes contra o meio ambiente", "1998", "Dispõe sobre as sanções penais e administrativas derivadas de condutas e atividades lesivas ao meio ambiente, e dá outras providências.", "lo", "penal", "false", "false");
            inserircodigo("9455", "Crimes de tortura", "1997", "Define os crimes de tortura e dá outras providências.", "lo", "penal", "false", "false");
            inserircodigo("9099", "Juizados Especiais Cíveis e Criminais", "1995", "Dispõe sobre os Juizados Especiais Cíveis e Criminais e dá outras providências.", "lo", "penal", "false", "false");
            inserircodigo("9034", "Crime organizado", "1995", "Dispõe sobre a utilização de meios operacionais para a prevenção e repressão de ações praticadas por organizações criminosas.", "lo", "penal", "false", "false");
            inserircodigo("5553", "Apresentação e uso de documentos de identificação pessoal", "1968", "Apresentação e uso de documentos de identificação pessoal Dispõe sobre a apresentação e uso de documentos de identificação pessoal.", "lo", "penal", "false", "false");
            inserircodigo("4898", "Abuso de autoridade", "1965", "Regula o Direito de Representação e o processo de Responsabilidade Administrativa Civil e Penal, nos casos de abuso de autoridade.", "lo", "penal", "false", "false");
            inserircodigo("3688", "Lei das Contravenções Penais", "1941", "Lei das Contravenções Penais", "dl", "penal", "false", "false");
            inserircodigo("5948", "Tráfico de Pessoas - Grupo de Trabalho Interministerial", "2006", "Aprova a Política Nacional de Enfrentamento ao Tráfico de Pessoas e institui Grupo de Trabalho Interministerial com o objetivo de elaborar proposta do Plano Nacional de Enfrentamento ao Tráfico de Pessoas&nbsp;-&nbsp;PNETP.	", "d", "penal", "false", "false");
            inserircodigo("6347", "Tráfico de Pessoas - PNETP", "2008", "Aprova o Plano Nacional de Enfrentamento ao Tráfico de Pessoas&nbsp;-&nbsp;PNETP e institui Grupo Assessor de Avaliação e Disseminação do referido Plano.", "d", "penal", "false", "false");
            inserircodigo("7901", "Tráfico de Pessoas - Coordenação Tripartite", "2013", "Institui a Coordenação Tripartite da Política Nacional de Enfrentamento ao Tráfico de Pessoas e o Comitê Nacional de Enfrentamento ao Tráfico de Pessoas&nbsp;-&nbsp;CONATRAP.", "d", "penal", "false", "false");
        }
        upVersao4();
        upVersao5();
    }

    public void upVersao4() {
        //versao 4 do app
        String[] coluna2 = {Banco.KEY_TIPO};
        Cursor versao4 = Banco.getIntance(context).getReadableDatabase().query(Banco.TABLE_LEIS, coluna2, Banco.KEY_NUMERO + "='12850'", null, null, null, Banco.KEY_TIPO);
        versao4.moveToFirst();
        if (versao4.getCount() == 0) {
            //adcionar lei 12850
            inserircodigo("12850", "Organização Criminosa", "2013", "\n" +
                    "Define organização criminosa e dispõe sobre a \n" +
                    "investigação criminal, os meios de obtenção da prova, infrações penais \n" +
                    "correlatas e o procedimento criminal; altera o Decreto-Lei nº \n" +
                    "2.848, de 7 de dezembro de 1940 (Código Penal); revoga a Lei nº \n" +
                    "9.034, de 3 de maio de 1995; e dá outras providências.", "lo", "penal", "false", "false");
            //alterar tipo da lei 9034
            ContentValues values = new ContentValues();
            values.put(Banco.KEY_TIPO, " ");
            Banco.getIntance(context).getWritableDatabase().update(Banco.TABLE_LEIS, values, Banco.KEY_NUMERO + "='9034' AND " + Banco.KEY_TABELA + "='lo'", null);
        }
        upVersao5();
    }

    public void upVersao5() {
        //versao 5 do app
        String[] coluna5 = {Banco.KEY_CONFIG_VERSAO_ANTES};
        Cursor versao5 = Banco.getIntance(context).getReadableDatabase().query(Banco.TABLE_CONFIGURCAO, coluna5,null, null, null, null, null);
        versao5.moveToFirst();
        if(versao5.getCount() > 0){
            String versao = versao5.getString(versao5.getColumnIndex(Banco.KEY_CONFIG_VERSAO_ANTES));
            if(versao != null) {
                if (!versao.equalsIgnoreCase("5")) {
                    //versao 4 do app
                    ContentValues values = new ContentValues();
                    values.put(Banco.KEY_FONTE, "20");
                    values.put(Banco.KEY_DICA_LEI, "false");
                    values.put(Banco.KEY_CONFIG_VERSAO_ANTES, "5");
                    Banco.getIntance(context).getWritableDatabase().update(Banco.TABLE_CONFIGURCAO, values, null, null);
                }
            }else {
                Debug.d("versao anterior = null");
                //versao 4 do app
                ContentValues values = new ContentValues();
                values.put(Banco.KEY_FONTE, "20");
                values.put(Banco.KEY_DICA_LEI, "false");
                values.put(Banco.KEY_CONFIG_VERSAO_ANTES, "5");
                Banco.getIntance(context).getWritableDatabase().update(Banco.TABLE_CONFIGURCAO, values, null, null);
            }
        }
    }


    private void inserircodigo(String numero, String nome, String ano, String descricao, String tabela, String tipo, String favorito, String offline) {
        ContentValues values2 = new ContentValues();
        values2.put("Numero", numero);
        values2.put("Nome", nome);
        values2.put("Ano", ano);
        values2.put("Descricao", descricao);
        values2.put("Tabela", tabela);
        values2.put("Tipo", tipo);
        values2.put(Banco.KEY_FAVORITO, favorito);
        values2.put(Banco.KEY_OFF_LINE, offline);
        if (Banco.getIntance(context).getWritableDatabase().insert(Banco.TABLE_LEIS, null, values2) != -1) {
            Debug.d("lei " + numero + "/" + ano + " inserida");
        }
    }
}