package ifsc.joe.domain.impl;

import ifsc.joe.domain.consts.Constantes;
import ifsc.joe.domain.core.Personagem;

public class Cavaleiro extends Personagem{
    public static final String NOME_IMAGEM = "cavaleiro";

    public Cavaleiro(int x, int y){
        super(x, y, NOME_IMAGEM, Constantes.CAVALEIRO_VIDA_INICIAL);
    }
}
