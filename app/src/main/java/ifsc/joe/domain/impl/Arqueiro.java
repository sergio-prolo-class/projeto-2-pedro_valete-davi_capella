package ifsc.joe.domain.impl;

import ifsc.joe.domain.api.Coletador;
import ifsc.joe.domain.consts.Constantes;
import ifsc.joe.domain.core.Personagem;
import ifsc.joe.domain.api.Guerreiro;
import ifsc.joe.domain.enums.Recurso;

// Arqueiro: Ataca de longe e coleta, mas não monta
public class Arqueiro extends Personagem implements Guerreiro, Coletador {
    private static final String NOME_IMAGEM = "arqueiro";

    public Arqueiro(int x, int y){
        super(x, y, NOME_IMAGEM, Constantes.ARQUEIRO_VIDA_INICIAL);
        this.velocidade = Constantes.ARQUEIRO_VELOCIDADE;
        this.alcance = Constantes.ARQUEIRO_ALCANCE; // Alcance longo
        this.chanceEsquiva = Constantes.ARQUEIRO_CHANCE_ESQUIVA; // Esquiva alta
    }

    // Lógica de ataque
    @Override
    public void atacar(Personagem alvo) {
        if (alvo != this) { // Não pode se atacar
            System.out.println("Arqueiro disparou flecha!");
            alvo.sofrerDano(Constantes.ARQUEIRO_ATAQUE);
        }
    }

    @Override
    public void coletar(Recurso recurso){
        System.out.println("Arqueiro coletando: " + recurso);
    }
}