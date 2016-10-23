package dk.brics.tajs.xwidl;

import dk.brics.tajs.lattice.Value;
import org.msgpack.MessageTypeException;
import org.msgpack.packer.Packer;
import org.msgpack.template.AbstractTemplate;
import org.msgpack.unpacker.Unpacker;
import java.io.IOException;

/**
 * Created by zz on 16-10-21.
 */
public class ValueTemplate extends AbstractTemplate<Value> {

    private ValueTemplate() {
    }

    public void write(Packer pk, Value target, boolean required) throws IOException {
        if (target == null) {
            if (required) {
                throw new MessageTypeException("Attempted to write null");
            }
            pk.writeNil();
            return;
        }
        pk.write(target.toString());
    }

    public Value read(Unpacker u, Value to, boolean required) throws IOException {
        if (!required && u.trySkipNil()) {
            return null;
        }
        // TODO: Complete parser??? Wait ... Maybe the returned type can be more structural
//        u.readMapBegin();
//        u.read("tag");
//        switch (u.readString()) {
//            case "JVRRef":
//                u.read("contents");
//                u.readMapBegin();
//                u.read("tag");
//                int i = u.readInt();
//                u.readMapEnd();
//                return Value.makeJRef(i);
//            default:
//                return null;
//        }
        return null;
    }

    static public ValueTemplate getInstance() {
        return instance;
    }

    static final ValueTemplate instance = new ValueTemplate();
}