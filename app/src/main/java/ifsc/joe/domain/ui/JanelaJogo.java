package ifsc.joe.domain.ui;

import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import ifsc.joe.domain.enums.Direcao;

/**
 * Classe principal que cria a janela (JFrame) e configura o teclado.
 */
public class JanelaJogo {

    private static final String TITULO = "Java of Empires";
    private final JFrame frame;
    private final PainelControles painelControles;

    public JanelaJogo() {
        this.frame = new JFrame(TITULO);
        this.painelControles = new PainelControles();
        this.configurarJanela();
    }

    private void configurarJanela() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setContentPane(painelControles.getPainelPrincipal());
        frame.pack();
        frame.setLocationRelativeTo(null); // Centralizar na tela

        // Impede que o TAB mude o foco dos botões,
        // permitindo usar o TAB como atalho do jogo
        frame.setFocusTraversalKeysEnabled(false);

        // Configura os atalhos de teclado
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                String tipoSelecionado = painelControles.getTipoSelecionado();

                switch (e.getKeyCode()){
                    // Movimento (WASD)
                    case KeyEvent.VK_W -> painelControles.getTela().movimentar(Direcao.CIMA, tipoSelecionado);
                    case KeyEvent.VK_A -> painelControles.getTela().movimentar(Direcao.ESQUERDA, tipoSelecionado);
                    case KeyEvent.VK_S -> painelControles.getTela().movimentar(Direcao.BAIXO, tipoSelecionado);
                    case KeyEvent.VK_D -> painelControles.getTela().movimentar(Direcao.DIREITA, tipoSelecionado);

                    // Criação (1, 2, 3)
                    case KeyEvent.VK_1 -> painelControles.criarAldeaoAleatorio();
                    case KeyEvent.VK_2 -> painelControles.criarArqueiroAleatorio();
                    case KeyEvent.VK_3 -> painelControles.criarCavaleiroAleatorio();

                    // Ações
                    case KeyEvent.VK_SPACE -> painelControles.getTela().atacar(tipoSelecionado);
                    case KeyEvent.VK_M -> painelControles.getTela().alternarMontaria(tipoSelecionado);
                    case KeyEvent.VK_TAB -> painelControles.selecionarProximoFiltro(); // Alterna filtro
                    case KeyEvent.VK_C -> painelControles.getTela().coletarRecursosProximos(tipoSelecionado); // Coleta
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    public void exibir() {
        frame.setVisible(true);
        frame.setFocusable(true); // Garante que a janela pegue o foco para o teclado funcionar
        frame.requestFocus();
    }
}