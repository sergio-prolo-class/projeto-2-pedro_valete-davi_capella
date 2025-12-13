package ifsc.joe.domain.impl;

import ifsc.joe.domain.consts.Constantes;
import ifsc.joe.domain.core.Personagem;
import ifsc.joe.domain.api.Guerreiro;
import ifsc.joe.domain.api.ComMontaria;

// Cavaleiro: Guerreiro forte que já começa montado
public class Cavaleiro extends Personagem implements Guerreiro, ComMontaria {
    public static final String NOME_IMAGEM = "cavaleiro";
    private boolean montado;

    public Cavaleiro(int x, int y){
        super(x, y, NOME_IMAGEM, Constantes.CAVALEIRO_VIDA_INICIAL);
        this.montar(); // Já nasce montado
        this.alcance = Constantes.CAVALEIRO_ALCANCE;
        this.chanceEsquiva = Constantes.CAVALEIRO_CHANCE_ESQUIVA;
    }

    @Override
    public void atacar(Personagem alvo) {
        if (alvo != this) {
            System.out.println("Cavaleiro atacou com espada!");
            alvo.sofrerDano(Constantes.CAVALEIRO_ATAQUE);
        }
    }

    @Override
    public void montar(){
        if(!montado){
            this.montado = true;
            this.velocidade = Constantes.CAVALEIRO_VELOCIDADE;
            this.nomeImagemBase = "cavaleiro";
            this.icone = this.carregarImagem(this.nomeImagemBase);
        }
    }

    @Override
    public void desmontar(){
        if(montado){
            this.montado = false;
            this.velocidade = Constantes.CAVALEIRO_VELOCIDADE_DESMONTADO; // Fica mais lento a pé
            this.nomeImagemBase = "cavaleiro_desmontado";
            this.icone = this.carregarImagem(this.nomeImagemBase);
        }
    }

    @Override
    public void alternarMontaria(){
        if(montado){
            desmontar();
        }else{
            montar();
        }
    }
}