package procon.tp00.repaso.e02;

class Guitarra extends Instrumento {
    
    public void tocar() {
        System.out.println("Guitarra.tocar()");
    }

    public String tipo() {
        return "Guitarra";
    }

    public void afinar() {
    }
}
