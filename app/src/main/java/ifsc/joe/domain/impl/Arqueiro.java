package ifsc.joe.domain.impl;

import ifsc.joe.domain.api.Coletador;
import ifsc.joe.domain.consts.Constantes;
import ifsc.joe.domain.core.Personagem;
import ifsc.joe.domain.api.Guerreiro;
import ifsc.joe.domain.enums.Recurso;

public class Arqueiro extends Personagem implements Guerreiro, Coletador {
    private static final String NOME_IMAGEM = "arqueiro";

    public Arqueiro(int x, int y){
        super(x, y, NOME_IMAGEM, Constantes.ARQUEIRO_VIDA_INICIAL);
        this.velocidade = Constantes.ARQUEIRO_VELOCIDADE;
    }

    // Mét0do obrigatório da interface Guerreiro
    @Override
    public void atacar(Personagem alvo) {
        // Verifica se não está atacando a si mesmo
        if (alvo != this) {
            System.out.println("Arqueiro disparou flecha!");
            alvo.sofrerDano(Constantes.ARQUEIRO_ATAQUE);
        }
    }

    @Override
    public void coletar(Recurso recurso){
        System.out.println("Arqueiro coletando: " + recurso);
        // Implementar inventario
    }
}