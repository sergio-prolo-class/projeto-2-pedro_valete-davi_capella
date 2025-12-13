package ifsc.joe.domain.core;

import ifsc.joe.domain.consts.Constantes; // Import necessário para pegar o range do aldeão
import ifsc.joe.domain.enums.Direcao;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public abstract class Personagem {

    protected int posX, posY;

    // Controles Visuais
    protected boolean atacando;        // Aura Vermelha
    protected boolean coletando;       // Aura Verde (NOVO)
    protected boolean spriteAlternado; // Troca de sprite

    protected Image icone;
    protected String nomeImagemBase;
    protected int vida;
    protected int vidaMaxima;
    protected int velocidade;
    protected int alcance;

    // Timers de Animação
    protected long tempoInicioAtaque;
    protected long tempoInicioColeta; // (NOVO)

    public Personagem(int x, int y, String nomeImagemBase, int vidaMaxima){
        this.posX = x;
        this.posY = y;
        this.nomeImagemBase = nomeImagemBase;
        this.atacando = false;
        this.coletando = false;
        this.spriteAlternado = false;
        this.icone = this.carregarImagem(nomeImagemBase);
        this.vida = vidaMaxima;
        this.vidaMaxima = vidaMaxima;
        this.tempoInicioAtaque = 0;
        this.tempoInicioColeta = 0;
    }

    public int getPosX() { return posX; }
    public int getPosY() { return posY; }
    public int getAlcance() { return alcance; }

    public void desenhar(Graphics g, JPanel painel) {
        long agora = System.currentTimeMillis();
        int duracao = 1000;

        // --- 1. LÓGICA DE FADE OUT VERMELHO (ATAQUE) ---
        if (this.atacando) {
            long tempoDecorrido = agora - this.tempoInicioAtaque;

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
                this.spriteAlternado = false; // Reseta sprite ao fim do ataque
            }
        }

        // --- 2. LÓGICA DE FADE OUT VERDE (COLETA) ---
        if (this.coletando) {
            long tempoDecorrido = agora - this.tempoInicioColeta;

            if (tempoDecorrido < duracao) {
                float opacidade = 1.0f - ((float) tempoDecorrido / duracao);
                int alphaFill = Math.max(0, (int)(50 * opacidade));
                int alphaBorder = Math.max(0, (int)(255 * opacidade));

                // Cor Verde Claro
                g.setColor(new Color(144, 238, 144, alphaFill)); // LightGreen com transparência
                int centroX = this.posX + 25;
                int centroY = this.posY + 25;

                // Requisito: "Mesmo range do Aldeão para ambos"
                int raioColeta = Constantes.ALDEAO_ALCANCE;

                g.fillOval(centroX - raioColeta, centroY - raioColeta, raioColeta * 2, raioColeta * 2);

                g.setColor(new Color(50, 205, 50, alphaBorder)); // LimeGreen para borda
                g.drawOval(centroX - raioColeta, centroY - raioColeta, raioColeta * 2, raioColeta * 2);
            } else {
                this.coletando = false;
            }
        }

        // Desenha Sprite
        this.icone = this.carregarImagem(nomeImagemBase + (spriteAlternado ? "2" : ""));
        g.drawImage(this.icone, this.posX, this.posY, painel);

        // Barra de Vida
        desenharBarraVida(g);
    }

    private void desenharBarraVida(Graphics g) {
        double porcentagem = (double) this.vida / this.vidaMaxima;
        int larguraBarra = 40;
        int alturaBarra = 5;
        int posYBarra = (this.posY < 10) ? this.posY + 52 : this.posY - 8;

        g.setColor(Color.GRAY);
        g.fillRect(this.posX, posYBarra, larguraBarra, alturaBarra);

        if (porcentagem >= 0.75) g.setColor(Color.GREEN);
        else if (porcentagem >= 0.25) g.setColor(Color.YELLOW);
        else g.setColor(Color.RED);

        int larguraVida = (int) (larguraBarra * porcentagem);
        g.fillRect(this.posX, posYBarra, larguraVida, alturaBarra);
    }

    public void mover(Direcao direcao, int maxLargura, int maxAltura){
        switch (direcao){
            case CIMA -> this.posY -= this.velocidade;
            case BAIXO -> this.posY += this.velocidade;
            case ESQUERDA -> this.posX -= this.velocidade;
            case DIREITA -> this.posX += this.velocidade;
        }
        this.posX = Math.min(Math.max(0, this.posX), maxLargura - this.icone.getWidth(null));
        this.posY = Math.min(Math.max(0, this.posY), maxAltura - this.icone.getHeight(null));
    }

    public void atacar(){
        this.atacando = true;
        this.tempoInicioAtaque = System.currentTimeMillis();
        this.spriteAlternado = !this.spriteAlternado;
    }

    // Método novo para iniciar animação verde
    public void iniciarColeta() {
        this.coletando = true;
        this.tempoInicioColeta = System.currentTimeMillis();
    }

    protected Image carregarImagem(String imagem){
        return new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("./" + imagem + ".png"))).getImage();
    }

    public void sofrerDano(int dano){
        this.vida -= dano;
        if(this.vida < 0) this.vida = 0;
    }

    public boolean estarMorto(){
        return this.vida <= 0;
    }
}