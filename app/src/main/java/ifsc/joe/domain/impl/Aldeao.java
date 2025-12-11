package ifsc.joe.domain.impl;

import ifsc.joe.domain.consts.Constantes;
import ifsc.joe.domain.core.Personagem;
import ifsc.joe.domain.api.Coletador;
import ifsc.joe.domain.enums.Recurso;
import ifsc.joe.domain.api.ComMontaria;

public class Aldeao extends Personagem implements Coletador, ComMontaria {
    private static final String NOME_IMAGEM = "aldeao";
    private boolean montado = false;

    public Aldeao(int x, int y){
        super(x, y, NOME_IMAGEM, Constantes.ALDEAO_VIDA_INICIAL);
        this.velocidade = Constantes.ALDEAO_VELOCIDADE;
        this.alcance = Constantes.ALDEAO_ALCANCE;
    }

    // Logica de montar do cavalo, se estiver desmontado
    @Override
    public void montar(){
        if(!montado){
            this.montado = true;
            this.velocidade = Constantes.ALDEAO_VELOCIDADE_MONTADO;
            this.nomeImagemBase = "aldeao_montado";
            this.icone = this.carregarImagem(this.nomeImagemBase);
        }
    }

    // Logica de desmontar do cavalo, se estiver montado
    @Override
    public void desmontar(){
        if(montado){
            this.montado = false;
            this.velocidade = Constantes.ALDEAO_VELOCIDADE;
            this.nomeImagemBase = "aldeao";
            this.icone = this.carregarImagem(this.nomeImagemBase);
        }
    }
    // Para facilitar no button (toggle)
    @Override
    public void alternarMontaria(){
        if(montado){
            desmontar();
        }else{
            montar();
        }
    }

    // Mét0do obrigatório da interface Coletador
    @Override
    public void coletar(Recurso recurso) {
        System.out.println("Aldeão coletando: " + recurso);
    }
}