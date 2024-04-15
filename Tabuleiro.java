import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


public class Tabuleiro {

    private ArrayList<Jogador> jogadores;
    private final ArrayList<Peca> disponiveis = new ArrayList<>();
    private final ArrayList<Peca> jogadas = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    private boolean iniciado = false;
    private Integer vezDe;

    private Jogador vencedor;


    public boolean setJogadores(ArrayList<Jogador> jogadores) {
        if(this.iniciado) return false;
        if(jogadores.size() < 2 || jogadores.size() > 4) return false;
        this.jogadores = jogadores;
        return true;
    }

    private Peca procurarPecaJogada(Peca peca) {
        for (Peca jogada : this.jogadas) {
            if (jogada.igual(peca)) {
                return jogada;
            }
        }
        return null;
    }

    // Retorn a ponta direita
    public Peca getRightEdge(){
        return this.jogadas.getLast();
    }

    // Retorn a ponta esquerda
    public Peca getLeftEdge(){
        return this.jogadas.getFirst();
    }

    // Retorna qual lado da peca nao possui um link
    public Integer getFreeSide(Peca p){
        if(!p.getALinked())return p.getA();
        return p.getB();
    }

    // Achar se uma peca esta na ponta
    public boolean isInEdge(Peca p){
        return (getRightEdge().igual(p) || getLeftEdge().igual(p));
    }

    public String getVezDe(){
        return jogadores.get(this.vezDe).getNome();
    }

    public String getVencedor(){
        return vencedor.getNome();
    }

    public ArrayList<Jogador> getJogadores() {
        return jogadores;
    }

    public boolean atribuirVezDe_ePecas(){

        if(this.iniciado) return false;
        if(this.jogadores.size() < 2 || this.jogadores.size() > 4) return false;

        //populate disponiveis(Peças)
        for (int i = 0; i <= 6; i++) {
            for (int j = 0; j < i + 1; j++) {
                disponiveis.add(new Peca(i, j));
            }
        }

        // Randomiza as peças para cada jogador
        for (int i = 0; i < jogadores.size(); i++) {

            Jogador jogador = jogadores.get(i); // retorna o elemento em (i)

            int idx = -1;
            for (int j = 0; j < 7; j++) {

                if(disponiveis.size() - 1 != 0){
                    idx = new Random().nextInt(disponiveis.size() - 1); // Index da peça
                }else{
                    idx = 0;
                }
                Peca peca = disponiveis.get(idx);
                disponiveis.remove(idx);
                jogador.adicionarPeca(peca); // Atribui a peca ao jogador

            }
        }


        // Achar maior duplo
        if(vezDe == null) {
            // Achar o maior duplo
            Peca maiorDuplo = null;
            for (int i = 0; i < jogadores.size(); i++) {

                Jogador jogador = jogadores.get(i);

                for (int j = 0; j < jogador.getPecas().size(); j++) {

                    Peca peca = jogador.getPecas().get(j);
                    if (peca.getA().equals(peca.getB())) {
                        if(maiorDuplo == null) {
                            maiorDuplo = peca;
                            vezDe = i;
                        }else if (peca.getA() > maiorDuplo.getA()) {
                            maiorDuplo = peca;
                            vezDe = i;
                        }
                    }
                }
            }

            if(maiorDuplo != null) {
                Jogador j = jogadores.get(this.vezDe);
                jogadas.add(maiorDuplo);
                j.removerPeca(maiorDuplo);
                nextPlayer();
                this.iniciado = true;
                return true;
            }

            Peca maiorPeca = null;
            // Caso nenhum maior duplo seja achado, procuramos pela maior soma
            for (int i = 0; i < jogadores.size(); i++) {

                Jogador jogador = jogadores.get(i);

                for (int j = 0; j < jogador.getPecas().size(); j++) {

                    Peca peca = jogador.getPecas().get(j);
                    if(maiorPeca == null){
                        maiorPeca = peca;
                    }else if(peca.getSoma() > maiorPeca.getSoma()){
                        maiorPeca = peca;
                        vezDe = i;
                    }
                }
            }

            Jogador j = jogadores.get(this.vezDe);
            jogadas.add(maiorPeca);
            j.removerPeca(maiorPeca);
            nextPlayer();
            this.iniciado = true;
            return true;


        }

        this.iniciado = true;
        return true;
    }

    // Passa para o proximo jogador
    private void nextPlayer() {
        this.vezDe = vezDe + 1 > jogadores.size() - 1 ? 0 : vezDe + 1;
    }

    public boolean pegarPecaDoBolo(){

        Jogador jogador = jogadores.get(vezDe);

        if(disponiveis.isEmpty()){
            return false;
        }
        // Pega peca do monte ate que uma peça possa ser jogada
        while(!jogadorConsegueJogar(jogador)){

            if(disponiveis.isEmpty()){
                return false; // Caso nenhuma peca sirva
            }

            int idx = new Random().nextInt(disponiveis.size() - 1); // Index da peça
            Peca peca = disponiveis.get(idx);
            disponiveis.remove(idx);
            jogador.adicionarPeca(peca); // Atribui a peca ao jogador
            System.out.println(jogador.getNome() + " comprou a peca " + peca);
        }
        return true;
    }

    public void display() {

        if (jogadas.size() == 1) {
            System.out.println("Início: " + this.jogadas.getFirst());
            return;
        }

        System.out.println("Disponivel na ponta direita: " + this.getFreeSide(this.getRightEdge()));
        System.out.println("Disponivel na ponta esquerda: " + this.getFreeSide(this.getLeftEdge()));
    }

    private boolean jogadorConsegueJogar(Jogador j){
        boolean algumapecabate = false;
        for(Peca p: j.getPecas()){
            // Se algum dos lados da peça do jogador sevir entao ele pode efetuar uma jogada
            // (Usado para comprar peças do monte)
            if(
                    getFreeSide(getLeftEdge()).equals(p.getA()) ||
                    getFreeSide(getLeftEdge()).equals(p.getB()) ||
                    getFreeSide(getRightEdge()).equals(p.getA()) ||
                    getFreeSide(getRightEdge()).equals(p.getB())
            ){
                algumapecabate = true;
            }
        }
        return algumapecabate;
    }

    private boolean jogadoresConseguemJogar(){
        boolean conseguem = false;

        for(Jogador j: jogadores){
            if(jogadorConsegueJogar(j))conseguem = true;
        }
        return conseguem;
    }

    public boolean play() {

        // Peca jogada = peca do tabuleiro
        // Peca Jogando = peca do jogoador a ser jogada

        if(!this.iniciado) return false;
        Jogador jogando = jogadores.get(this.vezDe);
        System.out.println("Vez de: " + jogando.getNome() + ", ");
        jogando.mostrarPecas();

        Peca pecaJogada;
        while(true){
            boolean consegueJogar = jogadorConsegueJogar(jogando);
            if(!consegueJogar) {
                boolean ok = pegarPecaDoBolo();
                if (!ok) {
                    System.out.println("Monte vazio e sem possibilidade de jogadas, pulando a vez...");
                    nextPlayer();
                    return false;
                }
            }



            if(jogadas.size() != 1){
                System.out.println("Onde voce vai querer jogar? 0 para esquerda e 1 para direita");
                int side = scanner.nextInt();
                if(side != 0 && side != 1)continue;
                if(side == 0){
                    pecaJogada = getLeftEdge();
                }else {
                    pecaJogada = getRightEdge();
                }
            }else{
                pecaJogada = jogadas.getFirst();
            }


            System.out.println("Qual peça irá jogar?");
            Peca pecaJogando = Peca.LerPeca();
            if(!jogando.temPeca(pecaJogando)){
                System.out.println("Você não possui esta peça");
                continue;
            }

            
            Integer freeSide = getFreeSide(pecaJogada);
            if(pecaJogando.getA().equals(freeSide)){
                pecaJogando.linkA();
            } else if (pecaJogando.getB().equals(freeSide)) {
                pecaJogando.linkB();
            }else{
                System.out.println("Esta peça não encaixa");
                continue;
            }

            if(getLeftEdge().igual(pecaJogada)){
                jogadas.addFirst(pecaJogando); // adiciona a peça na ponta esquerda
            }else{
                jogadas.addLast(pecaJogando); // adiciona a peça na ponta direita
            }

            jogando.removerPeca(pecaJogando);
            nextPlayer();
            break;
        };
        return true;
    }

    public void mostrarPecasJogadas(){
        StringBuilder linha = new StringBuilder();
        for(Peca p: jogadas) {
            linha.append("[");
            linha.append(p);
            linha.append("] ");
        }
        System.out.println(linha);
    }


    public boolean gameEnded(){
        if(!this.iniciado) return true;
        for (Jogador j : jogadores) {
            if (j.getPecas().isEmpty()) {
                vencedor = j;
                return true;
            }
        }
        if(disponiveis.isEmpty() && !jogadoresConseguemJogar()){

            int somaMenorPeca = -1;
            for (Jogador j : jogadores) {

                for (int n = 0; n < j.getPecas().size(); n++) {

                    int somaPeca = j.getPecas().get(n).getSoma();
                    if(somaMenorPeca < somaPeca){
                        somaMenorPeca = somaPeca;
                        vencedor = j;
                    }
                }

            }
            return true;
        }
        return false;
    }




}
