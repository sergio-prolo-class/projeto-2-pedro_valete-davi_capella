package ifsc.joe.domain.impl;

import ifsc.joe.domain.consts.Constantes;
import ifsc.joe.domain.core.Personagem;
import ifsc.joe.domain.api.Guerreiro;
import ifsc.joe.domain.api.Montaria;

public class Cavaleiro extends Personagem implements Guerreiro, Montaria {
    public static final String NOME_IMAGEM = "cavaleiro";

    public Cavaleiro(int x, int y){
        super(x, y, NOME_IMAGEM, Constantes.CAVALEIRO_VIDA_INICIAL);
    }

    @Override
    public void atacar(Personagem alvo) {
        System.out.println("Cavaleiro atacou com espada!");
    }

    @Override
    public void montar() {
        System.out.println("Cavaleiro está montado.");
    }

    @Override
    public void desmontar() {
        System.out.println("Cavaleiro desmontou.");
    }
}