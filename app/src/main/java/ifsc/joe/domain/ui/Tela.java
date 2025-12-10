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

public class Tela extends JPanel {

    private final Set<Personagem> personagens;

    public Tela() {

        //TODO preciso ser melhorado

        this.setBackground(Color.white);
        this.personagens = new HashSet<>();
    }

    /**
     * Method que invocado sempre que o JPanel precisa ser resenhado.
     * @param g Graphics componente de java.awt
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        //TODO preciso ser melhorado

        // percorrendo a lista de personagens e pedindo para cada um se desenhar na tela
        this.personagens.forEach(personagem -> personagem.desenhar(g, this));

        // liberando o contexto gráfico
        g.dispose();
    }

    /**
     * Cria um aldeao nas coordenadas X e Y, desenha-o neste JPanel
     * e adiciona o mesmo na lista de aldeoes
     *
     * @param x coordenada X
     * @param y coordenada Y
     */
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

    /**
     * Atualiza as coordenadas X ou Y de todos os aldeoes
     *
     * @param direcao direcao para movimentar
     */
    public void movimentarTodos(Direcao direcao) {
        //TODO preciso ser melhorado

        this.personagens.forEach(aldeao -> aldeao.mover(direcao, this.getWidth(), this.getHeight()));

        // Depois que as coordenadas foram atualizadas é necessário repintar o JPanel
        this.repaint();
    }

    /**
     * Altera o estado do aldeão de atacando para não atacando e vice-versa
     */
    public void atacarTodos() {
        // Alterna a animação visual (mantém o que já existia)
        this.personagens.forEach(Personagem::atacar);

        for (Personagem atacante : this.personagens) {
            if (atacante instanceof ifsc.joe.domain.api.Guerreiro) {
                for (Personagem alvo : this.personagens) {
                    if (atacante != alvo && calcularDistancia(atacante, alvo) <= 50) {
                        ((ifsc.joe.domain.api.Guerreiro) atacante).atacar(alvo);
                    }
                }
            }
        }

        // 3. Remove os mortos da tela
        this.personagens.removeIf(Personagem::estarMorto);

        this.repaint();
    }

    private double calcularDistancia(Personagem p1, Personagem p2) {
        return Math.sqrt(Math.pow(p1.getPosX() - p2.getPosX(), 2) + Math.pow(p1.getPosY() - p2.getPosY(), 2));
    }
}