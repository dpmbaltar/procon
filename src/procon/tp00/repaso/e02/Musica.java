package procon.tp00.repaso.e02;

public class Musica {
    
    // No importa el tipo de Instrumento,
    // seguirá funcionando debido a Polimorfismo:
    static void afinar(Instrumento i) {
        // ...
        i.tocar();
    }

    static void afinarTodo(Instrumento[] e) {
        for (int i = 0; i < e.length; i++)
            afinar(e[i]);
    }

    public static void main(String[] args) {
        Instrumento[] orquesta = new Instrumento[5];
        int i = 0;
        // Up-casting al asignarse el Arreglo
        orquesta[i++] = new Guitarra();
        orquesta[i++] = new Piano();
        orquesta[i++] = new Saxofon();
        orquesta[i++] = new Guzla();
        orquesta[i++] = new Ukelele();
        afinarTodo(orquesta);
    }
}
