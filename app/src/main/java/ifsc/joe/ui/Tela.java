package ifsc.joe.ui;

import ifsc.joe.domain.Personagem;
import ifsc.joe.domain.Guerreiro;
import ifsc.joe.domain.Montaria;
import ifsc.joe.domain.Coletador;
import ifsc.joe.domain.impl.Aldeao;
import ifsc.joe.domain.impl.Arqueiro;
import ifsc.joe.domain.impl.Cavaleiro;
import ifsc.joe.enums.Direcao;
import jdk.dynalink.linker.GuardingDynamicLinkerExporter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Set;

public class Tela extends JPanel implements MouseListener {

    private final Set<Personagem> personagens;
    private Personagem atacanteSelecionado = null;
    private boolean modoAtaqueAtivo = false;

    public Tela() {

        //TODO preciso ser melhorado

        this.setBackground(Color.white);
        this.personagens = new HashSet<>();
        this.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e){
        gerenciarCliquesAtaque(e.getX(), e.getY());
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
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

        //TODO preciso ser melhorado

        // Percorrendo a lista de aldeões e pedindo para todos atacarem
        this.personagens.forEach(p -> p.atacar());

        // Fazendo o JPanel ser redesenhado
        this.repaint();
    }

    // Ativa o modo de ataque
    public void ativarModoAtaque(){
        this.modoAtaqueAtivo = true;
        this.atacanteSelecionado = null;
        System.out.println("Selecione quem vai atacar");
    }

    // Metodo para gerenciar os cliques de quem vai atacar e quem vai ser atacado
    private void gerenciarCliquesAtaque(int x, int y){
        if(!modoAtaqueAtivo) return;

        Personagem clicado = null;
        for(Personagem p : personagens){
            if(x >= p.getPosX() && x <= p.getPosX() + 50 && y >= p.getPosY() && y <= p.getPosY() + 50){
                clicado = p;
                break;
            }
        }
        if(clicado == null){
            cancelarModoAtaque();
            return;
        }

        if(atacanteSelecionado == null){
            if(clicado instanceof Guerreiro){
                atacanteSelecionado = clicado;
                System.out.println("Agora clique no alvo");
            }else{
                System.out.println("Não é guerreiro");
            }
        }else{
            if(clicado != atacanteSelecionado){
                efetuarAtaque((Guerreiro) atacanteSelecionado, clicado);
            }
        }
    }
    private void efetuarAtaque(Guerreiro atacante, Personagem alvo){
        atacante.atacar(alvo);
        repaint();

        this.personagens.removeIf(Personagem::estarMorto);
        cancelarModoAtaque();
    }

    private void cancelarModoAtaque(){
        this.modoAtaqueAtivo = false;
        this.atacanteSelecionado = null;
    }
}