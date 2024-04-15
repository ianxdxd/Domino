import java.util.ArrayList;

public class Jogador {
    private final String nome;
    private final ArrayList<Peca> pecas = new ArrayList<>();

    Jogador(String nome){
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public ArrayList<Peca> getPecas() {
        return pecas;
    }

    public void adicionarPeca(Peca peca) {
        this.pecas.add(peca);
    }

    public void mostrarPecas(){
        for (Peca peca : pecas) {
            System.out.println(peca);
        }
    }

    public boolean temPeca(Peca procurada) {
        for (Peca procurando : this.pecas) {
            if (procurando.getA().equals(procurada.getA()) && procurando.getB().equals(procurada.getB())) {
                return true;
            }
        }
        return false;
    }

    public void removerPeca(Peca p) {
        for(int i = 0; i < pecas.size(); i++){
            Peca pecaNoJogador = pecas.get(i);
            if(pecaNoJogador.getA().equals(p.getA()) && pecaNoJogador.getB().equals(p.getB())){
                pecas.remove(i);
            }
        }
    }

}
