import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        int choice = -1;
        while(true){

            Scanner myObj = new Scanner(System.in);

            Perguntar perguntaInicio = new Perguntar("1 - Jogar\n2 - Sobre\n0 - Sair", 0, 2);
            perguntaInicio.exibirPergunta();
            choice = myObj.nextInt();
            perguntaInicio.passouVerificacao(choice);

            switch(choice){
                case 0:
                {
                    return;
                }
                case 1:
                {
                    // Inicio do Jogo
                    Tabuleiro tabuleiro = new Tabuleiro();
                    ArrayList<Jogador> jogadores = new ArrayList<>();
                    int qntJogadores = -1;

                    while(true) {
                        Perguntar perguntaQuantidadeJogadores = new Perguntar("Quantidade de Jogadores", 2, 4);
                        perguntaQuantidadeJogadores.exibirPergunta();
                        qntJogadores = myObj.nextInt();
                        if(perguntaQuantidadeJogadores.passouVerificacao(qntJogadores))break;
                    }


                    for(int i = 0; i < qntJogadores; i++){
                        System.out.print("Jogador [" + (i+1) + "]: ");
                        String nome = myObj.next();
                        jogadores.add(new Jogador(nome));
                        System.out.print("\n");
                    }
                    tabuleiro.setJogadores(jogadores);
                    tabuleiro.atribuirVezDe_ePecas();

                    while(!tabuleiro.gameEnded()){
                        tabuleiro.display();
                        tabuleiro.play();
                        tabuleiro.mostrarPecasJogadas();
                    }
                    System.out.println("Fim de jogo!");
                    System.out.println("Vencedor: " + tabuleiro.getVencedor());

                    myObj.next();
                    break;

                }
                case 2:
                {

                    System.out.println("Universidade do Vale do Itajaí");
                    System.out.println("Alunos: Ian C. Aragão e Lucas R. Losekann");
                    System.out.println("Matéria: Programação Orientada a Objetos");
                    System.out.println("Professor: Marcos Carrard");
                    System.out.println("Data: 14/04/2024");
                    System.out.println("0 - Voltar");

                    myObj.next(); // funciona como enter(mas preciso do 0)
                    break;
                }
            }
        }
    }
}