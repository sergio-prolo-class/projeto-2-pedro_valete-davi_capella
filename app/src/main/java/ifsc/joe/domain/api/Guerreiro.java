package ifsc.joe.domain.api;

import ifsc.joe.domain.core.Personagem;

// Interface que define quem pode atacar e causar dano (Arqueiro e Cavaleiro)
public interface Guerreiro {
    void atacar(Personagem alvo);
}