package dk.brics.tajs.xwidl;

import org.msgpack.packer.Packer;

import java.io.IOException;

/**
 * Created by zz on 16-10-21.
 */
public enum JsExpr implements ADT {
    JEBinary("JEBinary"), JEPrim("JEPrim"), JEVar("JEVar");

    private String tag;

    // JEBinary
    private RelBiOp op;
    private JsExpr e1, e2;

    // JEPrim
    private Prim prim;

    // JEVar
    private String varName;

    JsExpr(String tag) {
        this.tag = tag;
    }

    public JsExpr makeJEBinary(RelBiOp op, JsExpr e1, JsExpr e2) {
        JsExpr e = JEBinary;
        e.op = op;
        e.e1 = e1;
        e.e2 = e2;

        return e;
    }

    public JsExpr makeJEPrim(Prim p) {
        JsExpr e = JEPrim;
        e.prim = p;
        return e;
    }

    public void writeContents(Packer pk) throws IOException {
        switch (this) {
            case JEBinary:
                pk.writeArrayBegin(3);
                pk.write(op);
                pk.write(e1);
                pk.write(e2);
                pk.writeArrayEnd();
                break;
            case JEPrim:
                break;
            case JEVar:
                break;
        }
    }

    public String getTag() {
        return tag;
    }
}
