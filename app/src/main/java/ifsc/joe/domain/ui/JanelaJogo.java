package ifsc.joe.domain.ui;

import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import ifsc.joe.domain.enums.Direcao;

/**
 * Classe responsável pela configuração e exibição da janela principal do jogo.
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

    /**
     * Configura o conteúdo da janela
     */
    private void configurarJanela() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setContentPane(painelControles.getPainelPrincipal());
        frame.pack();
        frame.setLocationRelativeTo(null); // Centralizar na tela

        //CORREÇÃO IMPORTANTE
        // Desativa a função padrão do TAB (que é mudar o foco entre botões)
        frame.setFocusTraversalKeysEnabled(false);

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // System.out.println("Recebi a tecla: " + e.getKeyCode());
                String tipoSelecionado = painelControles.getTipoSelecionado();

                switch (e.getKeyCode()){
                    case KeyEvent.VK_W -> painelControles.getTela().movimentar(Direcao.CIMA, tipoSelecionado);
                    case KeyEvent.VK_A -> painelControles.getTela().movimentar(Direcao.ESQUERDA, tipoSelecionado);
                    case KeyEvent.VK_S -> painelControles.getTela().movimentar(Direcao.BAIXO, tipoSelecionado);
                    case KeyEvent.VK_D -> painelControles.getTela().movimentar(Direcao.DIREITA, tipoSelecionado);

                    case KeyEvent.VK_1 -> painelControles.criarAldeaoAleatorio();
                    case KeyEvent.VK_2 -> painelControles.criarArqueiroAleatorio();
                    case KeyEvent.VK_3 -> painelControles.criarCavaleiroAleatorio();

                    case KeyEvent.VK_SPACE -> painelControles.getTela().atacar(tipoSelecionado);
                    case KeyEvent.VK_M -> painelControles.getTela().alternarMontaria(tipoSelecionado);
                    case KeyEvent.VK_TAB -> painelControles.selecionarProximoFiltro();

                    // ADICIONADO: Atalho 'C' para coletar recursos
                    case KeyEvent.VK_C -> painelControles.getTela().coletarRecursosProximos(tipoSelecionado);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    /**
     * Torna a janela visível.
     */
    public void exibir() {
        frame.setVisible(true);

        frame.setFocusable(true);
        frame.requestFocus();
    }
}