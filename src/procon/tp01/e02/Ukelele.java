package procon.tp01.e02;

//Un tipo de Guitarra
class Ukelele extends Guitarra {
    
    public void tocar() {
        System.out.println("Ukelele.tocar()");
    }

    public String tipo() {
        return "Ukelele";
    }
}
