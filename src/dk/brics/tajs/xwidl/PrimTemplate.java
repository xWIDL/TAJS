package dk.brics.tajs.xwidl;


import org.msgpack.MessageTypeException;
import org.msgpack.packer.Packer;
import org.msgpack.unpacker.Unpacker;

import java.io.IOException;

/**
 * Created by zz on 16-10-22.
 */
public class PrimTemplate {
    private PrimTemplate() {

    }

    public void write(Packer pk, Prim target, boolean required) throws IOException {
        if (target == null) {
            if (required) {
                throw new MessageTypeException("Attempted to write null");
            }
            pk.writeNil();
            return;
        }
        Util.writeADTBranch(pk, target);
    }

    public Prim read(Unpacker u, Prim to, boolean required) throws IOException {
        if (!required && u.trySkipNil()) {
            return null;
        }

        // TODO: implement the parser
        return null;
    }

    static public PrimTemplate getInstance() {
        return instance;
    }

    static final PrimTemplate instance = new PrimTemplate();
}
