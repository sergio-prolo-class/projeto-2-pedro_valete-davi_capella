package ifsc.joe.domain.core;

import ifsc.joe.domain.enums.Recurso;
import javax.swing.*;
import java.awt.*;

public class ObjetoRecurso {
    private int posX, posY;
    private Recurso tipoRecurso;
    private boolean coletado;

    public ObjetoRecurso(int x, int y, Recurso tipoRecurso) {
        this.posX = x;
        this.posY = y;
        this.tipoRecurso = tipoRecurso;
        this.coletado = false;
    }

    public void desenhar(Graphics g) {
        if (coletado) return;

        // Define a cor baseada no tipo
        switch (tipoRecurso) {
            case OURO -> g.setColor(Color.YELLOW);
            case MADEIRA -> g.setColor(new Color(139, 69, 19)); // Marrom
            case COMIDA -> g.setColor(Color.PINK);
        }

        // Desenha um quadrado pequeno (Recurso)
        g.fillRect(posX, posY, 20, 20);

        // Borda
        g.setColor(Color.BLACK);
        g.drawRect(posX, posY, 20, 20);
    }

    public int getPosX() { return posX; }
    public int getPosY() { return posY; }
    public Recurso getTipoRecurso() { return tipoRecurso; }

    public boolean isColetado() { return coletado; }
    public void setColetado(boolean coletado) { this.coletado = coletado; }
}