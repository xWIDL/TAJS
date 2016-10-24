package dk.brics.tajs.xwidl;

import org.msgpack.MessageTypeException;
import org.msgpack.packer.Packer;
import org.msgpack.template.AbstractTemplate;
import org.msgpack.unpacker.Unpacker;

import java.io.IOException;

/**
 * Created by zz on 16-10-23.
 */
public class JRefTemplate extends AbstractTemplate<JRef> {
    private JRefTemplate() {

    }

    public void write(Packer pk, JRef target, boolean required) throws IOException {
        if (target == null) {
            if (required) {
                throw new MessageTypeException("Attempted to write null");
            }
            pk.writeNil();
            return;
        }
        pk.writeMapBegin(1);
        pk.write("unJRef");
        pk.write(target.getRef());
        pk.writeMapEnd();
    }

    public JRef read(Unpacker u, JRef to, boolean required) throws IOException {
        if (!required && u.trySkipNil()) {
            return null;
        }

        u.readMapBegin();
        u.read("unJRef");
        int i = u.readInt();
        return new JRef(i);
    }

    static public JRefTemplate getInstance() {
        return instance;
    }

    static final JRefTemplate instance = new JRefTemplate();

}
