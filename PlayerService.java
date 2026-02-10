//import static org.junit.jupiter.api.Assertions.assertEquals;
//import org.junit.jupiter.api.Test;

public class PlayerService {

private final RecomendacaoStrategy estrategia;
private final NotificacaoService notificacao;

// DI por construtor (dependências obrigatórias)
public PlayerService(RecomendacaoStrategy estrategia, NotificacaoService
notificacao) {
this.estrategia = estrategia;
this.notificacao = notificacao;
}

public String gerarRecomendacao(String usuario) {
// comportamento varia conforme a Strategy
return estrategia.recomendarProximaMusica(usuario);
}

public void gerarE_Notificar(String usuario) {
String musica = gerarRecomendacao(usuario);
notificacao.enviarMensagem(usuario, "Recomendação do dia: " + musica);
}

}