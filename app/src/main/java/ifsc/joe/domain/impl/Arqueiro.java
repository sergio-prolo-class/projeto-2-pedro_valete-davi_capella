package ifsc.joe.domain.impl;

import ifsc.joe.domain.consts.Constantes;
import ifsc.joe.domain.core.Personagem;

public class Arqueiro extends Personagem{
    private static final String NOME_IMAGEM = "arqueiro";

    public Arqueiro(int x, int y){
        super(x, y, NOME_IMAGEM, Constantes.ARQUEIRO_VIDA_INICIAL);
    }
}
