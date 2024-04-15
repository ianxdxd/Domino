import java.net.SocketTimeoutException;
import java.util.Scanner;

public class Perguntar {

    private String pergunta;
    private int min, max;

    public Perguntar(String pergunta, int min, int max) {
        this.pergunta = pergunta;
        this.min = min;
        this.max = max;
    }

    public void exibirPergunta(){
        System.out.println(this.pergunta);
    }

    public boolean passouVerificacao(int choice){
        return (choice >= this.min && choice <= this.max);
    }

    public int perguntar(){
        int res = min - 1;
        Scanner scanner = new Scanner(System.in);
        while (!passouVerificacao(res)){
            exibirPergunta();
            res = scanner.nextInt();
        }
        return res;
    }


}
