package ifsc.joe.domain.core;

import ifsc.joe.domain.consts.Constantes;
import ifsc.joe.domain.enums.Direcao;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Random;

// Classe base  de todos os personagens.
// Tem toda a lógica de desenho, vida, movimento e animações.
public abstract class Personagem {

    protected int posX, posY;

    // Controles Visuais (booleanos para saber o que desenhar)
    protected boolean atacando;        // Aura Vermelha
    protected boolean coletando;       // Aura Verde
    protected boolean esquivou;        // Fumaça e Texto "Esquivou!"
    protected boolean spriteAlternado; // Troca de sprite (animação simples)

    protected Image icone;
    protected String nomeImagemBase;
    protected int vida;
    protected int vidaMaxima;
    protected int velocidade;
    protected int alcance;

    // Atributo de combate
    protected double chanceEsquiva;

    // Timers para controlar o tempo das animações (Fade Out)
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
        this.icone = this.carregarImagem(nomeImagemBase); // Carrega a imagem do disco
        this.vida = vidaMaxima;
        this.vidaMaxima = vidaMaxima;
        this.chanceEsquiva = 0.0;

        // Inicializa os tempos zerados
        this.tempoInicioAtaque = 0;
        this.tempoInicioColeta = 0;
        this.tempoInicioEsquiva = 0;
    }

    public int getPosX() { return posX; }
    public int getPosY() { return posY; }
    public int getAlcance() { return alcance; }

    // Método principal que desenha o personagem e seus efeitos na tela
    public void desenhar(Graphics g, JPanel painel) {
        long agora = System.currentTimeMillis();
        int duracaoPadrao = 1000; // 1 segundo de animação

        //LÓGICA DE FADE OUT VERMELHO (ATAQUE)
        if (this.atacando) {
            long tempoDecorrido = agora - this.tempoInicioAtaque;
            if (tempoDecorrido < duracaoPadrao) {
                // Calcula a transparência baseada no tempo
                float opacidade = 1.0f - ((float) tempoDecorrido / duracaoPadrao);
                int alphaFill = Math.max(0, (int)(50 * opacidade));
                int alphaBorder = Math.max(0, (int)(255 * opacidade));

                // Desenha o círculo vermelho transparente
                g.setColor(new Color(255, 0, 0, alphaFill));
                int centroX = this.posX + 25;
                int centroY = this.posY + 25;
                g.fillOval(centroX - this.alcance, centroY - this.alcance, this.alcance * 2, this.alcance * 2);
                g.setColor(new Color(255, 0, 0, alphaBorder));
                g.drawOval(centroX - this.alcance, centroY - this.alcance, this.alcance * 2, this.alcance * 2);
            } else {
                // Acabou o tempo, desliga o ataque
                this.atacando = false;
                this.spriteAlternado = false;
            }
        }

        // LÓGICA DE FADE OUT VERDE (COLETA)
        if (this.coletando) {
            long tempoDecorrido = agora - this.tempoInicioColeta;
            if (tempoDecorrido < duracaoPadrao) {
                float opacidade = 1.0f - ((float) tempoDecorrido / duracaoPadrao);
                int alphaFill = Math.max(0, (int)(50 * opacidade));
                int alphaBorder = Math.max(0, (int)(255 * opacidade));

                // Cor verde claro
                g.setColor(new Color(144, 238, 144, alphaFill));
                int centroX = this.posX + 25;
                int centroY = this.posY + 25;
                int raioColeta = Constantes.ALDEAO_ALCANCE; // Raio para coleta

                g.fillOval(centroX - raioColeta, centroY - raioColeta, raioColeta * 2, raioColeta * 2);
                g.setColor(new Color(50, 205, 50, alphaBorder));
                g.drawOval(centroX - raioColeta, centroY - raioColeta, raioColeta * 2, raioColeta * 2);
            } else {
                this.coletando = false;
            }
        }

        // ANIMAÇÃO DE FUMAÇA E ESQUIVA
        // Desenhamos ANTES do sprite para a fumaça ficar atrás (errei na primeira vez)
        if (this.esquivou) {
            long tempoDecorrido = agora - this.tempoInicioEsquiva;
            int duracaoFumaca = 700; // Fumaça é mais rápida

            if (tempoDecorrido < duracaoFumaca) {
                float opacidade = 1.0f - ((float) tempoDecorrido / duracaoFumaca);
                int expansao = (int)(tempoDecorrido / 15);
                int tamanhoNuvem = 30 + expansao;

                // Cor cinza com transparência
                int alphaFumaca = Math.max(0, Math.min(255, (int)(180 * opacidade)));
                g.setColor(new Color(120, 120, 120, alphaFumaca));

                // Desenha circulos simulando poeira
                int centroX = this.posX + 25;
                int centroY = this.posY + 45;

                g.fillOval(centroX - tamanhoNuvem + 10, centroY - (tamanhoNuvem/2), tamanhoNuvem, tamanhoNuvem/2 + 10);
                g.fillOval(centroX - (tamanhoNuvem/2), centroY - tamanhoNuvem + 5, tamanhoNuvem + 10, tamanhoNuvem/2 + 10);
                g.fillOval(centroX - 10, centroY - (tamanhoNuvem/2) + 5, tamanhoNuvem - 5, tamanhoNuvem/2 + 10);

                // Texto "ESQUIVOU!" em ciano
                int alphaTexto = Math.max(0, Math.min(255, (int)(255 * opacidade)));
                g.setColor(new Color(0, 255, 255, alphaTexto));
                g.setFont(new Font("Arial", Font.BOLD, 14));
                g.drawString("ESQUIVOU!", this.posX - 10, this.posY - 15);

            } else {
                this.esquivou = false;
            }
        }

        // Desenha a imagem do boneco (com troca de sprite se tiver atacando)
        this.icone = this.carregarImagem(nomeImagemBase + (spriteAlternado ? "2" : ""));
        g.drawImage(this.icone, this.posX, this.posY, painel);

        // Barra de vida
        desenharBarraVida(g);
    }

    // Desenha a barrinha colorida em cima da cabeça
    private void desenharBarraVida(Graphics g) {
        double porcentagem = (double) this.vida / this.vidaMaxima;
        int larguraBarra = 40;
        int alturaBarra = 5;
        int posYBarra = (this.posY < 10) ? this.posY + 52 : this.posY - 8; // Ajusta se tiver no topo da tela

        // Fundo cinza
        g.setColor(Color.GRAY);
        g.fillRect(this.posX, posYBarra, larguraBarra, alturaBarra);

        // Muda cor conforme a vida (Verde > Amarelo > Vermelho)
        if (porcentagem >= 0.75) g.setColor(Color.GREEN);
        else if (porcentagem >= 0.25) g.setColor(Color.YELLOW);
        else g.setColor(Color.RED);

        // Preenchimento da vida
        int larguraVida = (int) (larguraBarra * porcentagem);
        g.fillRect(this.posX, posYBarra, larguraVida, alturaBarra);
    }

    // Atualiza X e Y baseado na velocidade
    public void mover(Direcao direcao, int maxLargura, int maxAltura){
        switch (direcao){
            case CIMA -> this.posY -= this.velocidade;
            case BAIXO -> this.posY += this.velocidade;
            case ESQUERDA -> this.posX -= this.velocidade;
            case DIREITA -> this.posX += this.velocidade;
        }
        // Impede de sair da tela
        this.posX = Math.min(Math.max(0, this.posX), maxLargura - this.icone.getWidth(null));
        this.posY = Math.min(Math.max(0, this.posY), maxAltura - this.icone.getHeight(null));
    }

    // Inicia a animação de ataque (aura vermelha)
    public void atacar(){
        this.atacando = true;
        this.tempoInicioAtaque = System.currentTimeMillis();
        this.spriteAlternado = !this.spriteAlternado; // Troca a imagem
    }

    // Inicia a animação de coleta (aura verde)
    public void iniciarColeta() {
        this.coletando = true;
        this.tempoInicioColeta = System.currentTimeMillis();
    }

    protected Image carregarImagem(String imagem){
        return new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("./" + imagem + ".png"))).getImage();
    }

    // Recebe dano com chance de esquiva
    public void sofrerDano(int dano){
        // Rola o dado para ver se esquivou
        if (new Random().nextDouble() <= this.chanceEsquiva) {
            this.esquivou = true; // Ativa animação
            this.tempoInicioEsquiva = System.currentTimeMillis();
            System.out.println(this.getClass().getSimpleName() + " ESQUIVOU do ataque!");
            return; // Sai do método sem tirar vida!
        }

        // Se não esquivou, toma o dano
        this.vida -= dano;
        if(this.vida < 0) this.vida = 0;
    }

    public boolean estarMorto(){
        return this.vida <= 0;
    }
}