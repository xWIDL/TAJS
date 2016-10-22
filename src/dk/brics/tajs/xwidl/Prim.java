package dk.brics.tajs.xwidl;

import org.msgpack.packer.Packer;

import java.io.IOException;

/**
 * Created by zz on 16-10-21.
 */
public class Prim implements ADT {
    public enum PrimKind {
        PNumber("PNumber"),
        PBool("PBool"),
        PInt("PInt"),
        PString("PString"),
        PNull("PNull");

        private String tag;

        PrimKind(String tag) {
            this.tag = tag;
        }
    }

    private PrimKind kind;

    public Prim(double n) {
        this.kind = PrimKind.PNumber;
        this.pnumber = n;
    }

    public double getPnumber() {
        return pnumber;
    }

    public int getPint() {
        return pint;
    }

    public boolean isPbool() {
        return pbool;
    }

    public String getPstring() {
        return pstring;
    }

    private double pnumber;
    private int pint;
    private boolean pbool;
    private String pstring;

    @Override
    public String getTag() {
        return kind.tag;
    }

    @Override
    public void writeContents(Packer pk) throws IOException {
        switch (kind) {
            case PNumber:
                pk.write(pnumber);
                break;
            case PBool:
                break;
            case PInt:
                break;
            case PString:
                break;
            case PNull:
                break;
        }
    }
}

