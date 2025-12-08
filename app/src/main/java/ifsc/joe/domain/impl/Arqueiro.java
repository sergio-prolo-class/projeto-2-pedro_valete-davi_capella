package ifsc.joe.domain.impl;

import ifsc.joe.domain.Personagem;

public class Arqueiro extends Personagem{
    private static final String NOME_IMAGEM = "arqueiro";

    public Arqueiro(int x, int y){
        super(x, y, NOME_IMAGEM);
    }
}
