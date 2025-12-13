package ifsc.joe.domain.impl;

import ifsc.joe.domain.consts.Constantes;
import ifsc.joe.domain.core.Personagem;
import ifsc.joe.domain.api.Coletador;
import ifsc.joe.domain.enums.Recurso;
import ifsc.joe.domain.api.ComMontaria;

// Aldeão: Coleta e monta cavalo, mas não ataca
public class Aldeao extends Personagem implements Coletador, ComMontaria {
    private static final String NOME_IMAGEM = "aldeao";
    private boolean montado = false;

    public Aldeao(int x, int y){
        super(x, y, NOME_IMAGEM, Constantes.ALDEAO_VIDA_INICIAL);
        // Pega valores do arquivo de constantes
        this.velocidade = Constantes.ALDEAO_VELOCIDADE;
        this.alcance = Constantes.ALDEAO_ALCANCE;
        this.chanceEsquiva = Constantes.ALDEAO_CHANCE_ESQUIVA;
    }

    // Sobe no cavalo, muda velocidade e imagem
    @Override
    public void montar(){
        if(!montado){
            this.montado = true;
            this.velocidade = Constantes.ALDEAO_VELOCIDADE_MONTADO;
            this.nomeImagemBase = "aldeao_montado";
            this.icone = this.carregarImagem(this.nomeImagemBase);
        }
    }

    // Desce do cavalo
    @Override
    public void desmontar(){
        if(montado){
            this.montado = false;
            this.velocidade = Constantes.ALDEAO_VELOCIDADE;
            this.nomeImagemBase = "aldeao";
            this.icone = this.carregarImagem(this.nomeImagemBase);
        }
    }

    // Método para o botão funcionar como interruptor
    @Override
    public void alternarMontaria(){
        if(montado){
            desmontar();
        }else{
            montar();
        }
    }

    // Implementação da coleta
    @Override
    public void coletar(Recurso recurso) {
        System.out.println("Aldeão coletando: " + recurso);
    }
}