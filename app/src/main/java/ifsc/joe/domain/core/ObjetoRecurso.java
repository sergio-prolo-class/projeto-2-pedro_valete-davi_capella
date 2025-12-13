package ifsc.joe.domain.core;

import ifsc.joe.domain.enums.Recurso;
import javax.swing.*;
import java.awt.*;

// Classe que representa os itens no chão (ouro, madeira, comida)
public class ObjetoRecurso {
    private int posX, posY;
    private Recurso tipoRecurso;
    private boolean coletado; // para saber se já foi pego

    public ObjetoRecurso(int x, int y, Recurso tipoRecurso) {
        this.posX = x;
        this.posY = y;
        this.tipoRecurso = tipoRecurso;
        this.coletado = false;
    }

    // Desenha o quadradinho colorido dependendo do tipo
    public void desenhar(Graphics g) {
        if (coletado) return; // Se já coletou, não desenha nada

        // Define a cor baseada no tipo
        switch (tipoRecurso) {
            case OURO -> g.setColor(Color.YELLOW);
            case MADEIRA -> g.setColor(new Color(139, 69, 19)); // Marrom
            case COMIDA -> g.setColor(Color.PINK);
        }

        // Desenha um quadrado pequeno (20x20)
        g.fillRect(posX, posY, 20, 20);

        // Borda preta pra ficar visível
        g.setColor(Color.BLACK);
        g.drawRect(posX, posY, 20, 20);
    }

    public int getPosX() { return posX; }
    public int getPosY() { return posY; }
    public Recurso getTipoRecurso() { return tipoRecurso; }

    public boolean isColetado() { return coletado; }
    public void setColetado(boolean coletado) { this.coletado = coletado; }
}