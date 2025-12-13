package ifsc.joe.domain.ui;

import ifsc.joe.domain.api.Coletador;
import ifsc.joe.domain.api.ComMontaria;
import ifsc.joe.domain.core.ObjetoRecurso;
import ifsc.joe.domain.core.Personagem;
import ifsc.joe.domain.enums.Recurso;
import ifsc.joe.domain.impl.Aldeao;
import ifsc.joe.domain.impl.Arqueiro;
import ifsc.joe.domain.impl.Cavaleiro;
import ifsc.joe.domain.enums.Direcao;
import ifsc.joe.domain.api.Guerreiro;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class Tela extends JPanel {

    private final Set<Personagem> personagens;
    private final Set<ObjetoRecurso> recursosMapa; // Nova lista de recursos

    // Variáveis Visuais (Fade Out)
    private final Set<Personagem> morrendo;
    private float opacidade = 1.0f;
    private Timer timerAnimacao;

    // Placar de Baixas
    private int baixasAldeoes = 0;
    private int baixasArqueiros = 0;
    private int baixasCavaleiros = 0;

    // Placar de Recursos do Jogador (Novo Requisito)
    private int jogadorOuro = 0;
    private int jogadorMadeira = 0;
    private int jogadorComida = 0;

    public Tela() {
        this.setBackground(Color.white);
        this.personagens = new HashSet<>();
        this.morrendo = new HashSet<>();
        this.recursosMapa = new HashSet<>();

        // Gera recursos aleatórios no mapa ao iniciar
        inicializarRecursos();
    }

    private void inicializarRecursos() {
        Random r = new Random();
        // Cria 5 de cada tipo em posições aleatórias
        for (int i = 0; i < 5; i++) recursosMapa.add(new ObjetoRecurso(r.nextInt(700), r.nextInt(500), Recurso.OURO));
        for (int i = 0; i < 5; i++) recursosMapa.add(new ObjetoRecurso(r.nextInt(700), r.nextInt(500), Recurso.MADEIRA));
        for (int i = 0; i < 5; i++) recursosMapa.add(new ObjetoRecurso(r.nextInt(700), r.nextInt(500), Recurso.COMIDA));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 1. Desenha os Recursos no chão (antes dos personagens)
        for (ObjetoRecurso rec : this.recursosMapa) {
            rec.desenhar(g);
        }

        // 2. Desenha os personagens vivos normalmente
        for (Personagem personagem : this.personagens) {
            personagem.desenhar(g, this);
        }

        // 3. Desenha os personagens morrendo com transparência (Fade Out)
        if (!morrendo.isEmpty()) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacidade));

            for (Personagem p : this.morrendo) {
                p.desenhar(g, this);
            }

            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }

        // 4. Desenha o HUD de Recursos (Canto Superior Esquerdo)
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 12));
        g.drawString("💰 Ouro: " + jogadorOuro, 10, 20);
        g.drawString("🌲 Madeira: " + jogadorMadeira, 10, 35);
        g.drawString("🍎 Comida: " + jogadorComida, 10, 50);

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
            if (tipoRadioButton.equals("TODOS")) deveMover = true;
            else if (tipoRadioButton.equals("ALDEAO") && p instanceof Aldeao) deveMover = true;
            else if (tipoRadioButton.equals("ARQUEIRO") && p instanceof Arqueiro) deveMover = true;
            else if (tipoRadioButton.equals("CAVALEIRO") && p instanceof Cavaleiro) deveMover = true;

            if (deveMover) {
                p.mover(direcao, this.getWidth(), this.getHeight());
            }
        }
        this.repaint();
    }

    // --- MÉTODO NOVO: Lógica de Coleta ---
    public void coletarRecursosProximos() {
        boolean coletouAlgo = false;

        for (Personagem p : this.personagens) {
            // Apenas quem tem a interface Coletador pode pegar recursos
            if (p instanceof Coletador) {
                for (ObjetoRecurso rec : this.recursosMapa) {
                    // Se estiver perto (50px)
                    if (calcularDistanciaRecurso(p, rec) <= 50) {
                        ((Coletador) p).coletar(rec.getTipoRecurso());
                        rec.setColetado(true); // Marca para sumir
                        coletouAlgo = true;

                        // Adiciona ao jogador (HUD)
                        switch (rec.getTipoRecurso()) {
                            case OURO -> jogadorOuro += 10;
                            case MADEIRA -> jogadorMadeira += 10;
                            case COMIDA -> jogadorComida += 10;
                        }
                    }
                }
            }
        }

        // Remove recursos coletados da lista do mapa
        if (coletouAlgo) {
            this.recursosMapa.removeIf(ObjetoRecurso::isColetado);
            this.repaint(); // Atualiza a tela (somem os recursos e atualiza o placar)
        }
    }

    public void atacar(String tipoRadioButton) {
        for (Personagem atacante : this.personagens) {
            boolean deveAtacar = false;
            if (tipoRadioButton.equals("TODOS")) deveAtacar = true;
            else if (tipoRadioButton.equals("ALDEAO") && atacante instanceof Aldeao) deveAtacar = true;
            else if (tipoRadioButton.equals("ARQUEIRO") && atacante instanceof Arqueiro) deveAtacar = true;
            else if (tipoRadioButton.equals("CAVALEIRO") && atacante instanceof Cavaleiro) deveAtacar = true;

            if (!deveAtacar) continue;

            atacante.atacar();

            if (atacante instanceof Guerreiro) {
                for (Personagem alvo : this.personagens) {
                    if (atacante != alvo && !alvo.estarMorto() &&
                            calcularDistancia(atacante, alvo) <= atacante.getAlcance()) {
                        ((Guerreiro) atacante).atacar(alvo);
                    }
                }
            }
        }

        Set<Personagem> novosMortos = this.personagens.stream()
                .filter(Personagem::estarMorto)
                .collect(Collectors.toSet());

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

            this.morrendo.addAll(novosMortos);
            this.personagens.removeAll(novosMortos);
            iniciarAnimacaoMorte();
        }

        this.repaint();

        Timer timerAtaque = new Timer(50, null);
        long inicioAnimacao = System.currentTimeMillis();
        timerAtaque.addActionListener(e -> {
            this.repaint();
            if (System.currentTimeMillis() - inicioAnimacao > 1100) {
                timerAtaque.stop();
            }
        });
        timerAtaque.start();
    }

    private void iniciarAnimacaoMorte() {
        this.opacidade = 1.0f;
        if (timerAnimacao != null && timerAnimacao.isRunning()) {
            timerAnimacao.stop();
        }
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

    // Método auxiliar para calcular distância até o recurso
    private double calcularDistanciaRecurso(Personagem p, ObjetoRecurso r) {
        return Math.sqrt(Math.pow(p.getPosX() - r.getPosX(), 2) + Math.pow(p.getPosY() - r.getPosY(), 2));
    }

    public void alternarMontaria(String tipoRadioButton){
        for(Personagem p : this.personagens){
            if(p instanceof ComMontaria){
                boolean deveMontar = false;
                if(tipoRadioButton.equals("TODOS")) deveMontar = true;
                else if(tipoRadioButton.equals("ALDEAO") && p instanceof Aldeao) deveMontar = true;
                else if(tipoRadioButton.equals("CAVALEIRO") && p instanceof Cavaleiro) deveMontar = true;

                if(deveMontar) ((ComMontaria) p).alternarMontaria();
            }
        }
        this.repaint();
    }
}