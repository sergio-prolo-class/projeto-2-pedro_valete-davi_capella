package ifsc.joe.domain.impl;

import ifsc.joe.domain.consts.Constantes;
import ifsc.joe.domain.core.Personagem;
import ifsc.joe.domain.api.Guerreiro;

public class Arqueiro extends Personagem implements Guerreiro {
    private static final String NOME_IMAGEM = "arqueiro";

    public Arqueiro(int x, int y){
        super(x, y, NOME_IMAGEM, Constantes.ARQUEIRO_VIDA_INICIAL);
    }

    // Mét0do obrigatório da interface Guerreiro
    @Override
    public void atacar(Personagem alvo) {
        System.out.println("Arqueiro atacou um alvo!");
    }
}