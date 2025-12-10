package ifsc.joe.domain.ui;

import ifsc.joe.domain.enums.Direcao;

import javax.swing.*;
import java.util.Random;

/**
 * Classe responsável por gerenciar os controles e interações da interface.
 * Conecta os componentes visuais com a lógica do jogo (Tela).
 */
public class PainelControles {

    private final Random sorteio;
    private Tela tela;

    // Componentes da interface (gerados pelo Form Designer)
    private JPanel painelPrincipal;
    private JPanel painelTela;
    private JPanel painelLateral;
    private JButton bCriaAldeao;
    private JButton bCriaArqueiro;
    private JButton bCriaCavaleiro;
    private JRadioButton todosRadioButton;
    private JRadioButton aldeaoRadioButton;
    private JRadioButton arqueiroRadioButton;
    private JRadioButton cavaleiroRadioButton;
    private JButton atacarButton;
    private JButton buttonCima;
    private JButton buttonEsquerda;
    private JButton buttonBaixo;
    private JButton buttonDireita;
    private JLabel logo;

    public PainelControles() {
        this.sorteio = new Random();
        configurarListeners();
    }

    /**
     * Configura todos os listeners dos botões.
     */
    private void configurarListeners() {
        configurarBotoesMovimento();
        configurarBotoesCriacao();
        configurarBotaoAtaque();
    }

    /**
     * Configura todos os listeners dos botões de movimento, agora com o tipo selecionado
     */
    private void configurarBotoesMovimento() {
        buttonCima.addActionListener(e -> getTela().movimentar(Direcao.CIMA, getTipoSelecionado()));
        buttonBaixo.addActionListener(e -> getTela().movimentar(Direcao.BAIXO, getTipoSelecionado()));
        buttonEsquerda.addActionListener(e -> getTela().movimentar(Direcao.ESQUERDA, getTipoSelecionado()));
        buttonDireita.addActionListener(e -> getTela().movimentar(Direcao.DIREITA, getTipoSelecionado()));
    }

    /**
     * Configura todos os listeners dos botões de criação
     */
    private void configurarBotoesCriacao() {
        bCriaAldeao.addActionListener(e -> criarAldeaoAleatorio());

        bCriaArqueiro.addActionListener(e -> criarArqueiroAleatorio());

        bCriaCavaleiro.addActionListener(e -> criarCavaleiroAleatorio());
    }

    /**
     * Configura o listener do botão de ataque
     */
    private void configurarBotaoAtaque() {
        atacarButton.addActionListener(e -> {
            getTela().atacar(getTipoSelecionado());
        });
    }

    /**
     * Cria um aldeão em posição aleatória na tela.
     */
    private void criarAldeaoAleatorio() {
        final int PADDING = 50;
        int posX = sorteio.nextInt(painelTela.getWidth() - PADDING);
        int posY = sorteio.nextInt(painelTela.getHeight() - PADDING);

        getTela().criarAldeao(posX, posY);
    }

    private void criarArqueiroAleatorio(){
        final int PADDING = 50;
        int posX = sorteio.nextInt(painelTela.getWidth() - PADDING);
        int posY = sorteio.nextInt(painelTela.getHeight() - PADDING);

        getTela().criarArqueiro(posX, posY);
    }

    private void criarCavaleiroAleatorio(){
        final int PADDING = 50;
        int posX = sorteio.nextInt(painelTela.getWidth() - PADDING);
        int posY = sorteio.nextInt(painelTela.getHeight() - PADDING);

        getTela().criarCavaleiro(posX, posY);
    }

    /**
     * Exibe mensagem informando que a funcionalidade ainda não foi implementada.
     */
    private void mostrarMensagemNaoImplementado(String funcionalidade) {
        JOptionPane.showMessageDialog(
                painelPrincipal,
                "Preciso ser implementado",
                funcionalidade,
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Obtém a referência da Tela com cast seguro.
     */
    private Tela getTela() {
        if (tela == null) {
            tela = (Tela) painelTela;
        }
        return tela;
    }

    /**
     * Retorna o painel principal para ser adicionado ao JFrame.
     */
    public JPanel getPainelPrincipal() {
        return painelPrincipal;
    }

    /**
     * Método chamado pelo Form Designer para criar componentes customizados.
     * Este método é invocado antes do construtor.
     */
    private void createUIComponents() {
        this.painelTela = new Tela();
    }

    // Retorna o nome do tipo selecionado nos radios buttons
    private String getTipoSelecionado(){
        if(aldeaoRadioButton.isSelected()){
            return "ALDEAO";
        }else if(arqueiroRadioButton.isSelected()){
            return "ARQUEIRO";
        }else if(cavaleiroRadioButton.isSelected()){
            return "CAVALEIRO";
        }
        return "TODOS";
    }
}