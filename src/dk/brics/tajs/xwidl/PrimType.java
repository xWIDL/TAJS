package dk.brics.tajs.xwidl;

/**
 * Created by zz on 16-10-21.
 */

public enum PrimType {
    PTyInt("PTyInt");

    private String name;

    PrimType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
