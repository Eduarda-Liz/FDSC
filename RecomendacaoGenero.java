public class RecomendacaoGenero implements RecomendacaoStrategy{

    @Override
    public String recomendarProximaMusica(String usuario) {
        return "Música recomendada por genero";
    }
    
}
