package dk.brics.tajs.xwidl;

import org.msgpack.MessageTypeException;
import org.msgpack.annotation.Message;
import org.msgpack.packer.Packer;
import org.msgpack.template.AbstractTemplate;
import org.msgpack.unpacker.Unpacker;

import java.io.IOException;

/**
 * Created by zz on 16-10-21.
 */

@Message
public class PrimTypeTemplate extends AbstractTemplate<PrimType> {
    private PrimTypeTemplate() {

    }

    public void write(Packer pk, PrimType target, boolean required) throws IOException {
        if (target == null) {
            if (required) {
                throw new MessageTypeException("Attempted to write null");
            }
            pk.writeNil();
            return;
        }
        pk.write(target.getName());
    }

    public PrimType read(Unpacker u, PrimType to, boolean required) throws IOException {
        if (!required && u.trySkipNil()) {
            return null;
        }
        if(u.readString().equals("PTyInt")) {
            return PrimType.PTyInt;
        }
        return null;
    }

    static public PrimTypeTemplate getInstance() {
        return instance;
    }

    static final PrimTypeTemplate instance = new PrimTypeTemplate();

}