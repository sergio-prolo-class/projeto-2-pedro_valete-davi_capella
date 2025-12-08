package ifsc.joe.domain;

import ifsc.joe.enums.Direcao;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public abstract class Personagem {

    protected int posX, posY;
    protected boolean atacando;
    protected Image icone;
    protected String nomeImagemBase;

    public Personagem(int x, int y, String nomeImagemBase){
        this.posX = x;
        this.posY = y;
        this.nomeImagemBase = nomeImagemBase;
        this.atacando = false;
        this.icone = this.carregarImagem(nomeImagemBase);
    }

    /**
     * Desenhando o Personagem, nas coordenadas X e Y, com a imagem 'icone'
     * no JPanel 'pai'
     *
     * @param g objeto do JPanel que será usado para desenhar o Aldeão
     */

    public void desenhar(Graphics g, JPanel painel){
        this.icone = this.carregarImagem(nomeImagemBase + (atacando ? "2" : ""));
        g.drawImage(this.icone, this.posX, this.posY, painel);
    }

    /**
     * Atualiza as coordenadas X e Y do personagem
     *
     * @param direcao indica a direcao a ir.
     */

    public void mover(Direcao direcao, int maxLargura, int maxAltura){
        switch (direcao){
            case CIMA -> this.posY -= 10;
            case BAIXO -> this.posY += 10;
            case ESQUERDA -> this.posX -= 10;
            case DIREITA -> this.posX += 10;
        }
        this.posX = Math.min(Math.max(0, this.posX), maxLargura - this.icone.getWidth(null));
        this.posY = Math.min(Math.max(0, this.posY), maxAltura - this.icone.getHeight(null));
    }

    public void atacar(){
        this.atacando = !this.atacando;
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
}
