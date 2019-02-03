package procon.tp02.e05;

public class Main {
    public static void main(String[] args) {
        Auto a1, a2, a3, a4, a5;
        Thread t1, t2, t3, t4, t5;
        Surtidor surtidor = new Surtidor();

        a1 = new Auto(2010, "Peogeot", "AA 281 AB", 500, 120, surtidor);
        a2 = new Auto(2010, "Chevrolet", "AB 002 CA", 400, 391, surtidor);
        a3 = new Auto(2010, "Toyota", "AC 392 JN", 400, 331, surtidor);
        a4 = new Auto(2010, "Nissan", "AA 291 JA", 450, 69, surtidor);
        a5 = new Auto(2010, "Ford", "AA 281 BB", 600, 600, surtidor);
        
        t1 = new Thread(a1);
        t2 = new Thread(a2);
        t3 = new Thread(a3);
        t4 = new Thread(a4);
        t5 = new Thread(a5);
        
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }
}
