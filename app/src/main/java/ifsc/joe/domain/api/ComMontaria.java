package ifsc.joe.domain.api;

// Interface para personagens que podem usar cavalo (Aldeão e Cavaleiro)
public interface ComMontaria {
    void montar();
    void desmontar();
    void alternarMontaria(); // Facilita para o botão "Montar/Desmontar"
}