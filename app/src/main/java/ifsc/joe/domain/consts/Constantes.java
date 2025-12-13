package ifsc.joe.domain.consts;

// Classe final para guardar todos os valores fixos do jogo
// Assim fica fácil de balancear a vida ou dano sem caçar números no código
public final class Constantes {

    // Configurações do Arqueiro
    public static final int    ARQUEIRO_VIDA_INICIAL           = 35;
    public static final int    ARQUEIRO_ATAQUE                 = 2;
    public static final int    ARQUEIRO_VELOCIDADE             = 10;
    public static final int    ARQUEIRO_FLECHAS_INICIAL        = 1;
    public static final int    ARQUEIRO_FLECHAS_PRODUCAO       = 10;
    public static final int    ARQUEIRO_ALCANCE                = 150;
    public static final double ARQUEIRO_CHANCE_ESQUIVA         = 0.25; // 25% de chance de esquivar

    // Configurações do Aldeão
    public static final int    ALDEAO_VIDA_INICIAL             = 25;
    public static final int    ALDEAO_VELOCIDADE               = 10;
    public static final int    ALDEAO_VELOCIDADE_MONTADO       = 15;
    public static final int    ALDEAO_ALCANCE                  = 50; // Alcance curto (coleta)
    public static final double ALDEAO_CHANCE_ESQUIVA           = 0.10; // 10% de chance

    // Configurações do Cavaleiro
    public static final int    CAVALEIRO_VIDA_INICIAL          = 50;
    public static final int    CAVALEIRO_ATAQUE                = 3;
    public static final int    CAVALEIRO_VELOCIDADE            = 20;
    public static final int    CAVALEIRO_VELOCIDADE_DESMONTADO = 10;
    public static final int    CAVALEIRO_ALCANCE               = 75; // Alcance médio
    public static final double CAVALEIRO_CHANCE_ESQUIVA        = 0.15; // 15% de chance
}