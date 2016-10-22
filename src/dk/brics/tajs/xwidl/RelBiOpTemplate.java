package dk.brics.tajs.xwidl;

/**
 * Created by zz on 16-10-22.
 */
import org.msgpack.MessageTypeException;
import org.msgpack.packer.Packer;
import org.msgpack.template.AbstractTemplate;
import org.msgpack.unpacker.Unpacker;

import java.io.IOException;

public class RelBiOpTemplate extends AbstractTemplate<RelBiOp> {
    private RelBiOpTemplate() {

    }

    public void write(Packer pk, RelBiOp target, boolean required) throws IOException {
        if (target == null) {
            if (required) {
                throw new MessageTypeException("Attempted to write null");
            }
            pk.writeNil();
            return;
        }
        pk.write(target.getName());
    }

    public RelBiOp read(Unpacker u, RelBiOp to, boolean required) throws IOException {
        if (!required && u.trySkipNil()) {
            return null;
        }
        for(RelBiOp op : RelBiOp.values()) {
            if(u.readString().equals(op.getName())) {
                return op;
            }
        }

        return null;
    }

    static public RelBiOpTemplate getInstance() {
        return instance;
    }

    static final RelBiOpTemplate instance = new RelBiOpTemplate();

}
