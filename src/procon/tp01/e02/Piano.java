package procon.tp01.e02;

class Piano extends Instrumento {
    
    public void tocar() {
        System.out.println("Piano.tocar()");
    }

    public String tipo() {
        return "Piano";
    }

    public void afinar() {
    }
}
