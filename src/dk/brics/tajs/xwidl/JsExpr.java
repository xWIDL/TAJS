package dk.brics.tajs.xwidl;

import org.msgpack.packer.Packer;

import java.io.IOException;

/**
 * Created by zz on 16-10-21.
 */
public class JsExpr implements ADT {
    public enum JsExprKind {
        JEBinary("JEBinary"), JEPrim("JEPrim"), JEVar("JEVar");

        private String tag;

        JsExprKind(String tag) {
            this.tag = tag;
        }
    }

    private JsExprKind kind;

    // JEBinary
    private RelBiOp op;
    private JsExpr e1, e2;

    // JEPrim
    private Prim prim;

    // JEVar
    private String varName;

    public JsExpr(RelBiOp op, JsExpr e1, JsExpr e2) {
        this.kind = JsExprKind.JEBinary;
        this.op = op;
        this.e1 = e1;
        this.e2 = e2;
    }

    public JsExpr(Prim p) {
        this.kind = JsExprKind.JEPrim;
        this.prim = p;
    }

    public void writeContents(Packer pk) throws IOException {
        switch (this.kind) {
            case JEBinary:
                pk.writeArrayBegin(3);
                pk.write(op);
                pk.write(e1);
                pk.write(e2);
                pk.writeArrayEnd();
                break;
            case JEPrim:
                pk.write(this.prim);
                break;
            case JEVar:
                break;
        }
    }

    public String getTag() {
        return kind.tag;
    }
}
