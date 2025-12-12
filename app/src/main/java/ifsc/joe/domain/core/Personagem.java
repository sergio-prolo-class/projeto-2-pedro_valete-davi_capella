package ifsc.joe.domain.core;

import ifsc.joe.domain.enums.Direcao;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public abstract class Personagem {

    protected int posX, posY;
    protected boolean atacando;        // Controla o Fade Out (Círculo)
    protected boolean spriteAlternado; // Controla a troca de Imagem
    protected Image icone;
    protected String nomeImagemBase;
    protected int vida;
    protected int vidaMaxima;
    protected int velocidade;
    protected int alcance;
    protected long tempoInicioAtaque;

    public Personagem(int x, int y, String nomeImagemBase, int vidaMaxima){
        this.posX = x;
        this.posY = y;
        this.nomeImagemBase = nomeImagemBase;
        this.atacando = false;
        this.spriteAlternado = false;
        this.icone = this.carregarImagem(nomeImagemBase);
        this.vida = vidaMaxima;
        this.vidaMaxima = vidaMaxima;
        this.tempoInicioAtaque = 0;
    }

    // Getters
    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getAlcance() {
        return alcance;
    }

    /**
     * Desenhando o Personagem, nas coordenadas X e Y, com a imagem 'icone'
     * no JPanel 'pai'
     *
     * @param g objeto do JPanel que será usado para desenhar o Aldeão
     */

    public void desenhar(Graphics g, JPanel painel) {
        if (this.atacando) {
            long tempoDecorrido = System.currentTimeMillis() - this.tempoInicioAtaque;
            int duracao = 1000;

            if (tempoDecorrido < duracao) {
                float opacidade = 1.0f - ((float) tempoDecorrido / duracao);

                int alphaFill = Math.max(0, (int)(50 * opacidade));
                int alphaBorder = Math.max(0, (int)(255 * opacidade));

                g.setColor(new Color(255, 0, 0, alphaFill));
                int centroX = this.posX + 25;
                int centroY = this.posY + 25;
                g.fillOval(centroX - this.alcance, centroY - this.alcance, this.alcance * 2, this.alcance * 2);

                g.setColor(new Color(255, 0, 0, alphaBorder));
                g.drawOval(centroX - this.alcance, centroY - this.alcance, this.alcance * 2, this.alcance * 2);
            } else {
                this.atacando = false;
            }
        }

        // Agora usa spriteAlternado em vez de atacando para escolher a imagem
        this.icone = this.carregarImagem(nomeImagemBase + (spriteAlternado ? "2" : ""));
        g.drawImage(this.icone, this.posX, this.posY, painel);

        // Calcula a porcentagem de 0 a 1
        double porcentagem = (double) this.vida / this.vidaMaxima;

        int larguraBarra = 40;
        int alturaBarra = 5;

        // Define a posição Y, se estiver no topo a barra vai pra baixo
        int posYBarra = (this.posY < 10) ? this.posY + 52 : this.posY - 8;

        g.setColor(Color.GRAY); // Fundo da barra
        g.fillRect(this.posX, posYBarra, larguraBarra, alturaBarra);

        // Cor baseada na porcentagem da vida
        if (porcentagem >= 0.75) {
            g.setColor(Color.GREEN);
        } else if (porcentagem >= 0.25) {
            g.setColor(Color.YELLOW);
        } else {
            g.setColor(Color.RED);
        }

        // Preenche de acordo com a vida automaticamente
        int larguraVida = (int) (larguraBarra * porcentagem);
        g.fillRect(this.posX, posYBarra, larguraVida, alturaBarra);
    }

    /**
     * Atualiza as coordenadas X e Y do personagem
     *
     * @param direcao indica a direcao a ir.
     */

    public void mover(Direcao direcao, int maxLargura, int maxAltura){
        switch (direcao){
            case CIMA -> this.posY -= this.velocidade; // Não mais uma velocidade fixa, agora varia de acordo com as constantes
            case BAIXO -> this.posY += this.velocidade;
            case ESQUERDA -> this.posX -= this.velocidade;
            case DIREITA -> this.posX += this.velocidade;
        }
        this.posX = Math.min(Math.max(0, this.posX), maxLargura - this.icone.getWidth(null));
        this.posY = Math.min(Math.max(0, this.posY), maxAltura - this.icone.getHeight(null));
    }

    public void atacar(){
        this.atacando = true; // Inicia o Fade Out
        this.tempoInicioAtaque = System.currentTimeMillis();
        this.spriteAlternado = !this.spriteAlternado; // Alterna a imagem instantaneamente
    }

    /**
     * Metodo auxiliar para carregar uma imagem do disco
     *
     * @param imagem Caminho da imagem
     * @return Retorna um objeto Image
     */

    protected Image carregarImagem(String imagem){
        return new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("./" + imagem + ".png"))).getImage();
    }

    // Metodo para receber o dano de um ataque

    public void sofrerDano(int dano){
        this.vida -= dano;
        if(this.vida < 0){
            this.vida = 0;
        }
    }

    // Metodo para verificar se esta morto
    public boolean estarMorto(){
        return this.vida <= 0;
    }
}