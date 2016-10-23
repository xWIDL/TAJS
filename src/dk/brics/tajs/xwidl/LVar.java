package dk.brics.tajs.xwidl;

import org.msgpack.packer.Packer;

import java.io.IOException;

/**
 * Created by zz on 16-10-22.
 */
public class LVar implements ADT {
    public enum LVarKind {
        LRef("LRef"), LInterface("LInterface");

        private String tag;

        LVarKind(String tag) {
            this.tag = tag;
        }
    }

    private JRef jref;

    public JRef getJref() {
        return jref;
    }

    public Name getIname() {
        return iname;
    }

    private Name iname;

    private LVarKind kind;

    public LVar(JRef r) {
        this.kind = LVarKind.LRef;
        this.jref = r;
    }

    public LVar(Name n) {
        this.kind = LVarKind.LInterface;
        this.iname = n;
    }

    public String getTag() {
        return this.kind.tag;
    }

    @Override
    public void writeContents(Packer pk) throws IOException {
        switch (this.kind) {
            case LRef:
                pk.write(this.jref);
                break;
            case LInterface:
                pk.write(this.iname);
                break;
        }
    }
}
