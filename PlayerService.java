import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class PlayerService implements RecomendacaoStrategy, NotificacaoService {

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

@Override
public String recomendarProximaMusica(String usuario) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'recomendarProximaMusica'");
}
@Override
public void enviarMensagem(String usuario, String mensagem) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'enviarMensagem'");
}
}