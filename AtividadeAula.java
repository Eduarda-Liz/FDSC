import java.util.ArrayList;
import java.util.List;
//Decorator:
interface Item {
String getDescricao();
double getPreco();
}
class MarmitaBase implements Item {
public String getDescricao() { return "Marmita de Frango"; }
public double getPreco() { return 22.0; }
}
abstract class AcrescimoDecorator implements Item { //criou o metodo decorator e faz que o
acrescimo receba as informações da descrição do item.
protected Item item;
public AcrescimoDecorator(Item item) { this.item = item; }
}
class OvoAdicional extends AcrescimoDecorator { //O metodo Decorator adiciona a
informação do "ovo frito" adicional sem alterar a interface Item.
public OvoAdicional(Item item) { super(item); }
public String getDescricao() { return item.getDescricao() + " + Ovo Frito"; } //pega a
descrição completa do item e adiciona "+ ovo frito"
public double getPreco() { return item.getPreco() + 2.5; } //além da descrição, ele adiciona
também o preço extra pelo ovo a mais (2.5)
}
// Strategy:
interface CalculoFrete {
double calcular(double km);
}
class FreteMoto implements CalculoFrete {
public double calcular(double km) { return km * 1.5; } //Strategy aparece quando o metodo
pode ser feito de diversas formas, ao inves de usar if/else
} //aqui ele aparece como uma classe que especificamente será usada quando o frete for
solicitado via MOTO
class FreteDrone implements CalculoFrete { //dessa forma é quando o frete for solicitado
por via DRONE
public double calcular(double km) { return km * 5.0; } //é uma estretégia de uso dos
métodos para evitar acoplamento
}

//Adapter:
class FreteAntigo {
double calcularFreteAntigo(double distancia) {
return distancia * 2.0;
}
}
class FreteAdapter implements CalculoFrete { //CRIOU UM OBJETO QUE IMPLEMENTA A
INTERFACE DO CALCULO DO FRETE
private FreteAntigo freteAntigo = new FreteAntigo();
public double calcular(double km) {
return freteAntigo.calcularFreteAntigo(km);//ADAPTOU O FRETE ANTIGO PARA
CALCULAR O VALOR ATUAL DO FRETE.
} //pegou o " modelo de calculo" do metodo antigo (frete antigo) que estava em "distancia"
e adaptou para o valor do parametro do metodo "calcular" que está em KM
}
//O metodo está pegando o "molde" do calculo, mas retorna o valor com o parametro em
KM. Assim, ele pode ser adaptado para funcionar neste programa, sem alterar o objeto.
// Observer:
interface Observer {
void atualizar(String msg); //Cria a interface com a mensagem que vai ser enviada.
}
class ClienteApp implements Observer {
private String nome;
public ClienteApp(String nome) { this.nome = nome; } //Recebe o nome do cliente (que no
metodo Main aparece como Ana)
public void atualizar(String msg) { //O metodo Observer é responsável por notificar o
cliente quando o pedido for recebido.
System.out.println("Cliente " + nome + " recebeu: " + msg); //Imprime na tela,
notificando que o cliente (Ana) recebeu o pedido (mensagem)
}
}
//Command:
interface Command {
void executar();
}
class EnviarCozinhaCommand implements Command { //O command cria um processo para
nao sobrecarregar o sistema.
private String prato;

public EnviarCozinhaCommand(String prato) { this.prato = prato; } //Cria a ação
public void executar() {
System.out.println("Cozinha processando: " + prato); //notifica a ação, qual "prato" está
sendo processado
}
}
// Facade:
class DeliveryFacade {
private List<Observer> usuarios = new ArrayList<>(); //Guarda todos os observadores,
que querem ser avisados quando algo acontecer com o pedido
private List<Command> comandos = new ArrayList<>(); //guarda todos os comandos, que
serão executados quando algo acontecer com o pedido
public void adicionarUsuario(Observer o) { //adicionar observadores na lista
usuarios.add(o);
}
public void finalizarPedido(boolean urgente) { //metodo chamado pelo cliente.
Item pedido = new OvoAdicional(new MarmitaBase());
CalculoFrete frete;
if (urgente) { //O parametro URGENTE muda o comportamento do método. Se urgente
for verdadeiro, então o pedido será entregue de drone.
frete = new FreteDrone();
} else {
frete = new FreteAdapter();
}
double total = pedido.getPreco() + frete.calcular(10.0); //Soma todas as informações
para informar ao cliente no final
System.out.println("Item: " + pedido.getDescricao());
System.out.println("Total: R$ " + total);
comandos.add(new EnviarCozinhaCommand(pedido.getDescricao())); //Adiciona o
comando para a cozinha, com a descrição do produto a ser produzido.
processarComandos();
avisar("Seu pedido foi finalizado e enviado!"); //Avisa que o pedido foi finalizado
}
private void processarComandos() {
for (Command c : comandos) c.executar(); //Executa os comandos da lista
comandos.clear(); //Limpa a lista após a execução

}
private void avisar(String texto) {
for (Observer o : usuarios) o.atualizar(texto); //Notifica todos os observadores da
mensagem (texto como parametro)
}
}
// Main:
public class Main {
public static void main(String[] args) {
DeliveryFacade api = new DeliveryFacade();
api.adicionarUsuario(new ClienteApp("Ana"));
api.finalizarPedido(false);
}
}
/*O padrão Decorator é usado para adicionar funcionalidades ao pedido, como itens extras,
sem alterar a classe base. A marmita recebe os acrescimos, adicionando a descrição e
alteração do preço.
O Strategy define as diferentes formas de calcular o frete, evitando o uso excessivo das
condicionais (if/else)
O Adapter adapta o sistema antigo de calculo de frete para um padrão atualizado, com KM,
possibilitando a reutilização do codigo.
O Observer notifica os clientes quando o pedido é finalizado
O command encapsula ações, permitindo organizar e executar comandos, como o envio do
pedido para a cozinha
E o facade centraliza toda a logica em uma interface simples, facilitando o uso pelo cliente.
*/
