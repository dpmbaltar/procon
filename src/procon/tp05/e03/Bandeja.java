package procon.tp05.e03;

import java.util.Random;

public class Bandeja {

    private final boolean tieneRefresco;

    public Bandeja(boolean tieneBotellin) {
        this.tieneRefresco = tieneBotellin;
    }

    public boolean tieneRefresco() {
        return tieneRefresco;
    }

    public boolean tieneVaso() {
        return !tieneRefresco;
    }

    public static Bandeja aleatorio() {
        return new Bandeja((new Random()).nextBoolean());
    }

}
