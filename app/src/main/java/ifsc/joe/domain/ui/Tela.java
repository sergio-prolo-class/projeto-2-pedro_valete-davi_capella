package ifsc.joe.domain.ui;

import ifsc.joe.domain.core.Personagem;
import ifsc.joe.domain.impl.Aldeao;
import ifsc.joe.domain.impl.Arqueiro;
import ifsc.joe.domain.impl.Cavaleiro;
import ifsc.joe.domain.enums.Direcao;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Tela extends JPanel {

    private final Set<Personagem> personagens;

    // Novas variáveis para o Fade Out
    private final Set<Personagem> morrendo; // Lista temporária para animação
    private float opacidade = 1.0f; // Começa totalmente visível
    private Timer timerAnimacao;

    // Contadores
    private int baixasAldeoes = 0;
    private int baixasArqueiros = 0;
    private int baixasCavaleiros = 0;

    public Tela() {
        this.setBackground(Color.white);
        this.personagens = new HashSet<>();
        this.morrendo = new HashSet<>();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 1. Desenha os personagens vivos normalmente
        for (Personagem personagem : this.personagens) {
            personagem.desenhar(g, this);
        }

        // 2. Desenha os personagens morrendo com transparência (Fade Out)
        if (!morrendo.isEmpty()) {
            Graphics2D g2d = (Graphics2D) g;
            // Configura a transparência baseada na variável 'opacidade'
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacidade));

            for (Personagem p : this.morrendo) {
                p.desenhar(g, this);
            }

            // Reseta para não afetar os próximos desenhos
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }

        g.dispose();
    }

    public void criarAldeao(int x, int y) {
        Aldeao aldeao = new Aldeao(x, y);
        aldeao.desenhar(super.getGraphics(), this);
        this.personagens.add(aldeao);
    }

    public void criarArqueiro(int x, int y){
        Arqueiro arqueiro = new Arqueiro(x, y);
        arqueiro.desenhar(super.getGraphics(), this);
        this.personagens.add(arqueiro);
    }

    public void criarCavaleiro(int x, int y){
        Cavaleiro cavaleiro = new Cavaleiro(x, y);
        cavaleiro.desenhar(super.getGraphics(), this);
        this.personagens.add(cavaleiro);
    }

    public void movimentarTodos(Direcao direcao) {
        this.personagens.forEach(aldeao -> aldeao.mover(direcao, this.getWidth(), this.getHeight()));
        this.repaint();
    }

    public void atacarTodos() {
        // Animação de ataque dos vivos
        this.personagens.forEach(Personagem::atacar);

        // Lógica de Dano
        for (Personagem atacante : this.personagens) {
            if (atacante instanceof ifsc.joe.domain.api.Guerreiro) {
                for (Personagem alvo : this.personagens) {
                    if (atacante != alvo && calcularDistancia(atacante, alvo) <= 50) {
                        ((ifsc.joe.domain.api.Guerreiro) atacante).atacar(alvo);
                    }
                }
            }
        }

        // Identifica quem morreu agora
        Set<Personagem> novosMortos = this.personagens.stream()
                .filter(Personagem::estarMorto)
                .collect(Collectors.toSet());

        // Processa as mortes
        if (!novosMortos.isEmpty()) {
            novosMortos.forEach(p -> {
                if (p instanceof Aldeao) baixasAldeoes++;
                else if (p instanceof Arqueiro) baixasArqueiros++;
                else if (p instanceof Cavaleiro) baixasCavaleiros++;

                System.out.println("Baixa confirmada: " + p.getClass().getSimpleName() +
                        " eliminado! (Total: " +
                        (p instanceof Aldeao ? baixasAldeoes :
                                p instanceof Arqueiro ? baixasArqueiros : baixasCavaleiros) + ")");
            });

            // Transfere dos vivos para a lista de animação "morrendo"
            this.morrendo.addAll(novosMortos);
            this.personagens.removeAll(novosMortos);

            // Inicia o Fade Out
            iniciarAnimacaoMorte();
        }

        this.repaint();
    }

    // Método novo para controlar o Timer da animação
    private void iniciarAnimacaoMorte() {
        this.opacidade = 1.0f; // Reseta opacidade

        if (timerAnimacao != null && timerAnimacao.isRunning()) {
            timerAnimacao.stop();
        }

        // Cria um timer que roda a cada 50ms
        timerAnimacao = new Timer(50, e -> {
            opacidade -= 0.1f; // Diminui 10% da opacidade por ciclo

            if (opacidade <= 0.0f) {
                opacidade = 0.0f;
                morrendo.clear(); // Limpa a lista quando fica invisível
                ((Timer)e.getSource()).stop();
            }
            repaint(); // Redesenha a tela com a nova opacidade
        });

        timerAnimacao.start();
    }

    private double calcularDistancia(Personagem p1, Personagem p2) {
        return Math.sqrt(Math.pow(p1.getPosX() - p2.getPosX(), 2) + Math.pow(p1.getPosY() - p2.getPosY(), 2));
    }
}