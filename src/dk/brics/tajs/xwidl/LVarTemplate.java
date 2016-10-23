package dk.brics.tajs.xwidl;

import org.msgpack.MessageTypeException;
import org.msgpack.packer.Packer;
import org.msgpack.template.AbstractTemplate;
import org.msgpack.unpacker.Unpacker;

import java.io.IOException;

/**
 * Created by zz on 16-10-23.
 */
public class LVarTemplate extends AbstractTemplate<LVar> {
    private LVarTemplate() {

    }

    public void write(Packer pk, LVar target, boolean required) throws IOException {
        if (target == null) {
            if (required) {
                throw new MessageTypeException("Attempted to write null");
            }
            pk.writeNil();
            return;
        }
        Util.writeADTBranch(pk, target);
    }

    public LVar read(Unpacker u, LVar to, boolean required) throws IOException {
        if (!required && u.trySkipNil()) {
            return null;
        }

        // TODO: write parser
        return null;
    }

    static public LVarTemplate getInstance() {
        return instance;
    }

    static final LVarTemplate instance = new LVarTemplate();

}
