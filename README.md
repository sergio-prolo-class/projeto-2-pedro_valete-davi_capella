Java of Empires ⚔️

Projeto desenvolvido na disciplina de Programação Orientada a Objetos.

## Funcionalidades Implementadas

** Título: Sistema de Combate **
** Ataque Básico: ** 
- *Implementa sistema de ataque onde personagens podem atacar outros personagens no jogo.*

** Sistema de Morte: **
- *Personagens com vida zero são removidos do jogo com efeitos visuais.*

** Alcance Várivael: **
- *Ataques têm limite de distância para acertar, variando conforme tipo de personagem.*

** Esquiva: **
- *Alvos têm chance de esquivar ataques, evitando completamente o dano.*

** Título: Controles Avançados **
** Filtro por Tipo: **
- *Implementa radio buttons para filtrar quais personagens serão afetados pelos comandos.*

** Controle de Montaria: **
- *Adiciona botão para alternar estado montado/desmontado dos personagens com montaria.*

** Atalhos de Teclado: **
- *Implementa controles por teclado para facilitar comandos rápidos.*

** Título: Arquitetura de Software **
** Arquivo de Configurações: **
- *Centralizar valores constantes em arquivo ou classe de configuração.*

** Título: Interface do Usuário **
** Barra de Vida: **
- *Indicador visual da vida atual de cada personagem acima do sprite.*

** Título: Funcionalidades de Jogo **
** Sistema de Coleta: **
- *Implementa mecânica de coleta de recursos adicionados ao jogador.*

---

### 🎮 Controles e Interação
- **Movimentação:** Controle de direção das unidades selecionadas via teclado (`W`, `A`, `S`, `D`).
- **Ações de Unidade:**
  - `Espaço`: Realiza a ação de ataque com a unidade selecionada.
  - `M`: Alterna o estado de montaria (para unidades compatíveis).
  - `C`: Coleta recursos dísponiveis por perto (para unidades compatíveis).
  - `Tab`: Alterna entre os filtros de tipo.
- **Criação de Unidades:** Atalhos numéricos para spawnar entidades no mapa:
  - `1`: Aldeão
  - `2`: Arqueiro
  - `3`: Cavaleiro
