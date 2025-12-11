package ifsc.joe.domain.ui;

import ifsc.joe.domain.api.ComMontaria;
import ifsc.joe.domain.core.Personagem;
import ifsc.joe.domain.impl.Aldeao;
import ifsc.joe.domain.impl.Arqueiro;
import ifsc.joe.domain.impl.Cavaleiro;
import ifsc.joe.domain.enums.Direcao;
import ifsc.joe.domain.api.Guerreiro;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Tela extends JPanel {

    private final Set<Personagem> personagens;

    // Novas variáveis para o Fade Out de Morte
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

    public void movimentar(Direcao direcao, String tipoRadioButton) {
        for (Personagem p : this.personagens) {

            boolean deveMover = false;

            // Se o tipo for TODOS, move sempre
            if (tipoRadioButton.equals("TODOS")) {
                deveMover = true;
            }else if (tipoRadioButton.equals("ALDEAO") && p instanceof Aldeao) {
                deveMover = true;
            }else if (tipoRadioButton.equals("ARQUEIRO") && p instanceof Arqueiro) {
                deveMover = true;
            }else if (tipoRadioButton.equals("CAVALEIRO") && p instanceof Cavaleiro) {
                deveMover = true;
            }

            // Se passou em algum teste acima, executa o movimento
            if (deveMover) {
                p.mover(direcao, this.getWidth(), this.getHeight());
            }
        }

        this.repaint();
    }

    public void atacar(String tipoRadioButton) {
        for (Personagem atacante : this.personagens) {

            // Verificação do filtro, igual para a movimentação
            boolean deveAtacar = false;

            if (tipoRadioButton.equals("TODOS")) {
                deveAtacar = true;
            }
            else if (tipoRadioButton.equals("ALDEAO") && atacante instanceof Aldeao) {
                deveAtacar = true;
            }
            else if (tipoRadioButton.equals("ARQUEIRO") && atacante instanceof Arqueiro) {
                deveAtacar = true;
            }
            else if (tipoRadioButton.equals("CAVALEIRO") && atacante instanceof Cavaleiro) {
                deveAtacar = true;
            }

            // Se não passou no filtro ele pula para o próximo personagem
            if (!deveAtacar){
                continue;
            }

            // Executa o ataque (inicia o tempo no Personagem)
            atacante.atacar();

            // Lógica do dano só se for Guerreiro e passou no filtro
            if (atacante instanceof Guerreiro) {
                for (Personagem alvo : this.personagens) {
                    // Requisito: Alcance Variável
                    if (atacante != alvo && !alvo.estarMorto() &&
                            calcularDistancia(atacante, alvo) <= atacante.getAlcance()) {

                        ((Guerreiro) atacante).atacar(alvo);
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

            // Inicia o Fade Out de Morte
            iniciarAnimacaoMorte();
        }

        this.repaint();


        // Isso obriga a tela a atualizar enquanto o círculo desaparece
        Timer timerAtaque = new Timer(50, null);
        long inicioAnimacao = System.currentTimeMillis();

        timerAtaque.addActionListener(e -> {
            this.repaint(); // Redesenha a tela

            // Para o timer depois de 1.1 segundos
            if (System.currentTimeMillis() - inicioAnimacao > 1100) {
                timerAtaque.stop();
            }
        });
        timerAtaque.start();
    }

    // Método novo para controlar o Timer da animação de morte
    private void iniciarAnimacaoMorte() {
        this.opacidade = 1.0f; // Reseta opacidade

        if (timerAnimacao != null && timerAnimacao.isRunning()) {
            timerAnimacao.stop();
        }

        // Cria um timer
        timerAnimacao = new Timer(50, e -> {
            opacidade -= 0.1f;

            if (opacidade <= 0.0f) {
                opacidade = 0.0f;
                morrendo.clear();
                ((Timer)e.getSource()).stop();
            }
            repaint();
        });

        timerAnimacao.start();
    }

    private double calcularDistancia(Personagem p1, Personagem p2) {
        return Math.sqrt(Math.pow(p1.getPosX() - p2.getPosX(), 2) + Math.pow(p1.getPosY() - p2.getPosY(), 2));
    }

    public void alternarMontaria(String tipoRadioButton){
        for(Personagem p : this.personagens){
            if(p instanceof ComMontaria){
                boolean deveMontar = false;

                // Verificação do filtro, igual para a movimentação e atacar
                if(tipoRadioButton.equals("TODOS")){
                    deveMontar = true;
                }else if(tipoRadioButton.equals("ALDEAO") && p instanceof Aldeao){
                    deveMontar = true;
                }else if(tipoRadioButton.equals("CAVALEIRO") && p instanceof Cavaleiro){
                    deveMontar = true;
                }

                // Se passou em algum teste acima, executa o comando
                if(deveMontar){
                    ((ComMontaria) p).alternarMontaria();
                }
            }
        }
        this.repaint();
    }
}