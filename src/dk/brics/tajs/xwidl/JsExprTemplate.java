package dk.brics.tajs.xwidl;

import org.msgpack.MessageTypeException;
import org.msgpack.packer.Packer;
import org.msgpack.template.AbstractTemplate;
import org.msgpack.unpacker.Unpacker;

import java.io.IOException;

/**
 * Created by zz on 16-10-21.
 */
public class JsExprTemplate extends AbstractTemplate<JsExpr> {

    private JsExprTemplate() {
    }

    public void write(Packer pk, JsExpr target, boolean required) throws IOException {
        if (target == null) {
            if (required) {
                throw new MessageTypeException("Attempted to write null");
            }
            pk.writeNil();
            return;
        }

        Util.writeADTBranch(pk, target);
    }

    public JsExpr read(Unpacker u, JsExpr to, boolean required) throws IOException {
        if (!required && u.trySkipNil()) {
            return null;
        }

        // TODO: parser

        return null;
    }

    static public dk.brics.tajs.xwidl.JsExprTemplate getInstance() {
        return instance;
    }

    static final dk.brics.tajs.xwidl.JsExprTemplate instance = new dk.brics.tajs.xwidl.JsExprTemplate();
}
