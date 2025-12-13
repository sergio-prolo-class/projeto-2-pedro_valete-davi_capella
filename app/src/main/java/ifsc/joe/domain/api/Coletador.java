package ifsc.joe.domain.api;

import ifsc.joe.domain.enums.Recurso;

// Interface que define quem pode coletar recursos (Aldeão e Arqueiro)
public interface Coletador {
    void coletar(Recurso recurso);
}