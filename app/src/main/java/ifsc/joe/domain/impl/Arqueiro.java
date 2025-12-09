package ifsc.joe.domain.impl;

import ifsc.joe.domain.Coletador;
import ifsc.joe.domain.Guerreiro;
import ifsc.joe.domain.Personagem;
import ifsc.joe.enums.Recurso;

public class Arqueiro extends Personagem implements Guerreiro, Coletador {
    private static final String NOME_IMAGEM = "arqueiro";
    private int dano = 15;

    public Arqueiro(int x, int y){
        super(x, y, NOME_IMAGEM, 100);
    }

    @Override
    public void atacar(Personagem alvo){
        if(!alvo.estarMorto()){
            alvo.receberDano(this.dano);
        }
    }

    @Override
    public void coletar(Recurso recurso){
        if(recurso == Recurso.COMIDA || recurso == Recurso.MADEIRA){
            // Implementar inventario
        }else{
            // Não pode coletar
        }
    }
}
