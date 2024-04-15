import java.io.InputStream;
import java.util.Scanner;

public class Peca {

    private Integer A;
    private Integer B;

    // Colocamos o link como true quando A ou B da atual peça ja esta conectado com a anterior
    private boolean ALinked = false;
    private boolean BLinked = false;

    Peca(int A, int B) {
        this.store(A, B);
    }

    void store(int A, int B){
        if(A > B) {
            this.A = A;
            this.B = B;
        }else {
            this.B = A;
            this.A = B;
        }
    }

    static Peca LerPeca () {
        int A = -1;
        int B = -1;
        Scanner scanner = new Scanner(System.in);

        while(A > 6 || A < 0) {

            System.out.print("Digite o lado A: ");
            try {
                A = scanner.nextInt();
            } catch (Exception e) {
                A = -1;
                scanner.nextLine(); // funcao .ignore()
            }
        }

        while(B > 6 || B < 0) {

            System.out.print("Digite o lado B: ");
            try {
                B = scanner.nextInt();
            } catch (Exception e) {
                B = -1;
                scanner.nextLine();
            }
        }

        return new Peca(A, B);
    }

    Integer getA() {
        return A;
    }

    Integer getB() {
        return B;
    }

    Integer getSoma() { return  A + B; }

    boolean igual(Peca p) {
        return p.getA().equals(this.getA()) && p.getB().equals(this.getB());
    }

    public boolean getALinked(){
        return this.ALinked;
    }

    public boolean getBLinked(){
        return this.BLinked;
    }

    public void linkB(){
        BLinked = true;
    }

    public void linkA() {
        ALinked = true;
    }

    @Override
    public String toString() {
        return this.A.toString() + " - " + this.B.toString();
    }
}
