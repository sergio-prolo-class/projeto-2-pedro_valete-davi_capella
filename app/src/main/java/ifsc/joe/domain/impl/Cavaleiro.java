package ifsc.joe.domain.impl;

import ifsc.joe.domain.Guerreiro;
import ifsc.joe.domain.Montaria;
import ifsc.joe.domain.Personagem;

public class Cavaleiro extends Personagem implements Guerreiro, Montaria {
    public static final String NOME_IMAGEM = "cavaleiro";
    private int dano = 10;
    private boolean montado = true;

    public Cavaleiro(int x, int y){
        super(x, y, NOME_IMAGEM, 150);
    }

    @Override
    public void atacar(Personagem alvo){
        alvo.receberDano(this.dano);
    }

    @Override
    public void montar(){
        this.montado = true;
        // Implementar o incremento de velocidade
    }

    @Override
    public void desmontar(){
        this.montado = false;
        // Velocidade normal
    }
}
