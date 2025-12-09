package ifsc.joe.domain.impl;

import ifsc.joe.domain.consts.Constantes;
import ifsc.joe.domain.core.Personagem;
import ifsc.joe.domain.api.Coletador;
import ifsc.joe.domain.enums.Recurso;

public class Aldeao extends Personagem implements Coletador {
    private static final String NOME_IMAGEM = "aldeao";

    public Aldeao(int x, int y){
        super(x, y, NOME_IMAGEM, Constantes.ALDEAO_VIDA_INICIAL);
    }

    // Mét0do obrigatório da interface Coletador
    @Override
    public void coletar(Recurso recurso) {
        System.out.println("Aldeão coletando: " + recurso);
    }
}