package ifsc.joe.domain.impl;

import ifsc.joe.domain.consts.Constantes;
import ifsc.joe.domain.core.Personagem;

public class Aldeao extends Personagem{
    private static final String NOME_IMAGEM = "aldeao";

    public Aldeao(int x, int y){
        super(x, y, NOME_IMAGEM, Constantes.ALDEAO_VIDA_INICIAL);
    }
}