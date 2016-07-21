package ggtec.lei_concursospublicos.api_antiga;

import ggtec.lei_concursospublicos.R;

/**
 * Created by Vagner on 25/02/2016.
 */
public class Ajuda {

    public static final int AJUDA_GERAL = 50;
    public static final int AJUDA_BASE = 150;

    public static final int AJUDA_COMENTARIO = 0;
    public static final int AJUDA_MARCACAO = 1;
    public static final int AJUDA_PLAYER = 2;
    public static final int AJUDA_MODOS_LEITURA = 3;
    public static final int AJUDA_MENUS = 4;
    public static final int AJUDA_FONTE = 5;
    public static final int AJUDA_PESQUISA_NA_LEI = 6;
    //public static final int AJUDA_PESQUISA_DE_LEI = 7;
    //public static final int AJUDA_INFO = 8;

    private int[] imagem;
    private String[] titulo;
    private String[] subTitulo;
    private int total;
    private String[] descricao;

    public Ajuda(int tipo){
        if(tipo == AJUDA_GERAL){
            titulo = new String[]{
                    "Ola!",
                    "Comente, marque e ouça!",
                    "Barra de menu superior",
                    "Pronto!"};
            subTitulo = new String[]{
                    "Posso dar umas dicas antes de você começar a estudar?",
                    "Com os botões de edição de lei.",
                    "Com ele você navega e configura o modo de leitura.",
                    "Aproveite e bons estudos."};
            imagem = new int[]{-1, R.drawable.dica_geral_1,R.drawable.dica_geral_2,-1};
            descricao = new String[]{
                    null,
                    "Toque em qualquer trecho da lei para:" +
                        "\n\n1 - Fazer um comentário" +
                            "\n     A) público: comentários públicos podem ser vistos por outros usuários, ou;" +
                            "\n     B) privado: somente você tem acesso." +
                        "\n\n2 - Fazer marcações com até 4 cores diferentes apenas tocando e arrastando em cima das letras que quer marcar como uma caneta." +
                        "\n\n3 - Ouvir as leis como se música com o player de lei",
                    "1 - Modo de leitura em tela cheia (para voltar ao modo de edição e só tocar na tela)" +
                        "\n\n2 - Menus de Títulos, Artigos, Seus comentários e Suas Marcações" +
                        "\n\n3 - Alterar o tamanho da fonte" +
                            "\n\n4 - Pesquisa de palavras chave na lei que estiver lendo" +
                            "\n\n5 - Preâmbulo da lei(infomações geral)",
                    "Se quiser mais dicas acesse Menu->Ajuda na tela inical do app"};
            total = 4;
        }

        if(tipo == AJUDA_COMENTARIO){
            titulo = new String[]{
                    "Modo de edição",
                    "Botão Comentário",
                    "Público ou Privado",
                    "Pronto"
            };
            subTitulo = new String[]{
                    "Se estiver em modo de tela cheia toque na tela para apareça os botões de edição",
                    null,
                    "Escolha entre Comentário público(1) ou privado(2) ",
                    "Comentário feito!"
            };
            imagem = new int[]{
                    R.drawable.modo_de_edicao,
                    R.drawable.btn_comentario,
                    R.drawable.comentario_publico_privado,
                    R.drawable.menu_comentario
            };
            descricao = new String[]{
                    "Depois toque no trecho da lei que queira criar ou editar um comentário",
                    "Clique no botão comentário",
                            "Pense desta forma, sendo coloborativo você pode ajudar e aproveita os comentários de outros usuários para aprender mais.",
                    "E você pode ver todos os seus comentários no menu."
            };
            total = 4;
        }

        if(tipo == AJUDA_MARCACAO){
            titulo = new String[]{
                    "Modo de edição",
                    "Botão de marcação",
                    "Marcação",
                    "Pronto"
            };
            subTitulo = new String[]{
                    "Se estiver em modo de tela cheia toque na tela para apareça os botões de edição",
                    "clique neste botão para mostrar as possibilidades de cores",
                    "Selecione a core para marcar",
                    "Marcação feita!"
            };
            imagem = new int[]{
                    R.drawable.modo_de_edicao,
                    R.drawable.btn_marcacao,
                    R.drawable.painel_marcacao,
                    R.drawable.menu_marcacao

            };
            descricao = new String[]{
                    "Depois toque no trecho da lei que queira criar ou editar uma marcação",
                    null,
                    "Despois de selecionar a cor, toque e arraste na tela para marcar.\nComo na imagem você pode usar quantas cores quiser no mesmo trecho de lei.",
                    "Você pode ver todas as marcações no menu da lei."
            };
            total = 4;
        }

        if(tipo == AJUDA_PLAYER){
            titulo = new String[]{
                    "Modo Edição",
                    "Botão Player",
                    "Player",
                    "Notificações"
                    };
            subTitulo = new String[]{
                    "Se estiver em modo de tela cheia toque na tela para apareça os botões de edição",
                    "Clique no trecho de lei que deseja iniciar o player e depois no botão de áudio",
                    "Nesta tela você pode acompanha a leitura e controlar o player",
                    "Também e possível controlar o player pela notificação",
                    };
            imagem = new int[]{
                    R.drawable.modo_de_edicao,
                    R.drawable.painel_player,
                    R.drawable.player_de_lei,
                    R.drawable.notificacao_player
            };
            descricao = new String[]{
                    null,
                    "Depois, clique em 'Ouvir a partir da seleção' para iniciar o áudio.",
                    null,
                    "Isso depende da versão do Android que você esta usando" +
                            "\n\nOBS.: o player trabalha de forma independente, por tanto, você pode fazer outra tarefa no app sem deixar de ouvir sua lei."
                    };
            total = 4;
        }

        if(tipo == AJUDA_MODOS_LEITURA){
            titulo = new String[]{
                    "Modo edição",
                    "Modo Leitura"
            };
            subTitulo = new String[]{
                    "Use somente quando for fazer alguma edição.",
                    "Ou tela cheia"+
                            "\nClicar neste botão da imagem e entre em modo de leitura."
            };
            imagem = new int[]{
                    R.drawable.modo_de_edicao,
                    R.drawable.btn_tela_cheia
            };
            descricao = new String[]{
                    "Quando uma lei é aberta, ela entra em modo de tela cheia, assim você tem mais espaço para leitura." +
                            "\n\nPara acessar os botões de edição, clique em qualquer parte da tela.",
                   null
            };
            total = 2;
        }

        if(tipo == AJUDA_MENUS){
            titulo = new String[]{
                    "Menu de lei"
            };
            subTitulo = new String[]{
                    "Com este botão você acessa o menu Títulos, Artigos e também seus comentários e suas marcações"
            };
            imagem = new int[]{
                    R.drawable.btn_menus
            };
            descricao = new String[]{
                    null
            };
            total = 1;
        }

        if(tipo == AJUDA_FONTE){
            titulo = new String[]{
                    "Tamanho de fonte",
                    "Configure o tamanho"
            };
            subTitulo = new String[]{
                    "Com este botão você altera o tamanho da fonte.",
                    null
            };
            imagem = new int[]{
                    R.drawable.btn_fonte,
                    R.drawable.tela_fonte
            };
            descricao = new String[]{
                    null,
                    "Arraste para o tamanho desejado e depois confirme a alteração"
            };
            total = 2;
        }

        if(tipo == AJUDA_PESQUISA_NA_LEI){
            titulo = new String[]{
                    "Pesquise na lei",
                    "Digite sua pesquisa"
            };
            subTitulo = new String[]{
                    "Botão para pesquisar na lei",
                    null
            };
            imagem = new int[]{
                    R.drawable.btn_pesquisa_na_lei,
                    R.drawable.tela_pesquisa_na_lei
            };
            descricao = new String[]{
                    null,
                    null
            };
            total = 2;
        }

        if(tipo == AJUDA_BASE){
            titulo = new String[]{
            };
            subTitulo = new String[]{
            };
            imagem = new int[]{};
            descricao = new String[]{
            };
            total = 0;
        }
    }

    public int[] getImagem() {
        return imagem;
    }

    public String[] getTitulo() {
        return titulo;
    }

    public String[] getSubTitulo() {
        return subTitulo;
    }

    public int getTotal() {
        return total;
    }

    public String[] getDescricao() {
        return descricao;
    }
}
