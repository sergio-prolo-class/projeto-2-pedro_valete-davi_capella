package ifsc.joe.domain.core;

import ifsc.joe.domain.consts.Constantes;
import ifsc.joe.domain.enums.Direcao;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Random;

public abstract class Personagem {

    protected int posX, posY;

    // Controles Visuais
    protected boolean atacando;        // Aura Vermelha
    protected boolean coletando;       // Aura Verde
    protected boolean esquivou;        // Fumaça e Texto "Esquivou!"
    protected boolean spriteAlternado; // Troca de sprite

    protected Image icone;
    protected String nomeImagemBase;
    protected int vida;
    protected int vidaMaxima;
    protected int velocidade;
    protected int alcance;

    // Atributo de combate
    protected double chanceEsquiva;

    // Timers de Animação
    protected long tempoInicioAtaque;
    protected long tempoInicioColeta;
    protected long tempoInicioEsquiva;

    public Personagem(int x, int y, String nomeImagemBase, int vidaMaxima){
        this.posX = x;
        this.posY = y;
        this.nomeImagemBase = nomeImagemBase;
        this.atacando = false;
        this.coletando = false;
        this.esquivou = false;
        this.spriteAlternado = false;
        this.icone = this.carregarImagem(nomeImagemBase);
        this.vida = vidaMaxima;
        this.vidaMaxima = vidaMaxima;
        this.chanceEsquiva = 0.0;
        this.tempoInicioAtaque = 0;
        this.tempoInicioColeta = 0;
        this.tempoInicioEsquiva = 0;
    }

    public int getPosX() { return posX; }
    public int getPosY() { return posY; }
    public int getAlcance() { return alcance; }

    public void desenhar(Graphics g, JPanel painel) {
        long agora = System.currentTimeMillis();
        int duracaoPadrao = 1000;

        // --- 1. LÓGICA DE FADE OUT VERMELHO (ATAQUE) ---
        if (this.atacando) {
            long tempoDecorrido = agora - this.tempoInicioAtaque;
            if (tempoDecorrido < duracaoPadrao) {
                float opacidade = 1.0f - ((float) tempoDecorrido / duracaoPadrao);
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
                this.spriteAlternado = false;
            }
        }

        // --- 2. LÓGICA DE FADE OUT VERDE (COLETA) ---
        if (this.coletando) {
            long tempoDecorrido = agora - this.tempoInicioColeta;
            if (tempoDecorrido < duracaoPadrao) {
                float opacidade = 1.0f - ((float) tempoDecorrido / duracaoPadrao);
                int alphaFill = Math.max(0, (int)(50 * opacidade));
                int alphaBorder = Math.max(0, (int)(255 * opacidade));
                g.setColor(new Color(144, 238, 144, alphaFill));
                int centroX = this.posX + 25;
                int centroY = this.posY + 25;
                int raioColeta = Constantes.ALDEAO_ALCANCE;
                g.fillOval(centroX - raioColeta, centroY - raioColeta, raioColeta * 2, raioColeta * 2);
                g.setColor(new Color(50, 205, 50, alphaBorder));
                g.drawOval(centroX - raioColeta, centroY - raioColeta, raioColeta * 2, raioColeta * 2);
            } else {
                this.coletando = false;
            }
        }

        // --- 3. NOVA ANIMAÇÃO DE FUMAÇA E ESQUIVA (Com Fade Out) ---
        // Desenhamos ANTES do sprite para a fumaça ficar atrás
        if (this.esquivou) {
            long tempoDecorrido = agora - this.tempoInicioEsquiva;
            int duracaoFumaca = 700; // Fumaça é mais rápida (0.7s)

            if (tempoDecorrido < duracaoFumaca) {
                // Calcula opacidade (vai de 1.0 a 0.0)
                float opacidade = 1.0f - ((float) tempoDecorrido / duracaoFumaca);
                // Calcula o tamanho da nuvem (expande com o tempo)
                int expansao = (int)(tempoDecorrido / 15);
                int tamanhoNuvem = 30 + expansao;

                // Cor Cinza com transparência variável
                int alphaFumaca = Math.max(0, Math.min(255, (int)(180 * opacidade)));
                g.setColor(new Color(120, 120, 120, alphaFumaca));

                // Desenha 3 elipses ovais para simular uma nuvem de poeira atrás do pé do boneco
                int centroX = this.posX + 25;
                int centroY = this.posY + 45; // Um pouco mais para baixo

                g.fillOval(centroX - tamanhoNuvem + 10, centroY - (tamanhoNuvem/2), tamanhoNuvem, tamanhoNuvem/2 + 10);
                g.fillOval(centroX - (tamanhoNuvem/2), centroY - tamanhoNuvem + 5, tamanhoNuvem + 10, tamanhoNuvem/2 + 10);
                g.fillOval(centroX - 10, centroY - (tamanhoNuvem/2) + 5, tamanhoNuvem - 5, tamanhoNuvem/2 + 10);

                // Texto "ESQUIVOU!" (Também com fade out agora)
                int alphaTexto = Math.max(0, Math.min(255, (int)(255 * opacidade)));
                g.setColor(new Color(0, 255, 255, alphaTexto)); // Ciano
                g.setFont(new Font("Arial", Font.BOLD, 14));
                g.drawString("ESQUIVOU!", this.posX - 10, this.posY - 15);

            } else {
                this.esquivou = false;
            }
        }

        // Desenha Sprite (Fica na frente da fumaça)
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

    public void iniciarColeta() {
        this.coletando = true;
        this.tempoInicioColeta = System.currentTimeMillis();
    }

    protected Image carregarImagem(String imagem){
        return new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("./" + imagem + ".png"))).getImage();
    }

    // LÓGICA DE ESQUIVA
    public void sofrerDano(int dano){
        // Verifica se esquivou baseado na chance
        if (new Random().nextDouble() <= this.chanceEsquiva) {
            this.esquivou = true; // Ativa a animação de fumaça e texto
            this.tempoInicioEsquiva = System.currentTimeMillis();
            System.out.println(this.getClass().getSimpleName() + " ESQUIVOU do ataque!");
            return; // Retorna sem tirar vida!
        }

        // Se não esquivou, toma dano normal
        this.vida -= dano;
        if(this.vida < 0) this.vida = 0;
    }

    public boolean estarMorto(){
        return this.vida <= 0;
    }
}