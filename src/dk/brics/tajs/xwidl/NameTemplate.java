package dk.brics.tajs.xwidl;

import org.msgpack.MessageTypeException;
import org.msgpack.packer.Packer;
import org.msgpack.template.AbstractTemplate;
import org.msgpack.template.StringTemplate;
import org.msgpack.template.Templates;
import org.msgpack.unpacker.Unpacker;

import java.io.IOException;

/**
 * Created by zz on 16-10-21.
 */
public class NameTemplate extends AbstractTemplate<Name> {
    private NameTemplate() {

    }

    public void write(Packer pk, Name target, boolean required) throws IOException {
        if (target == null) {
            if (required) {
                throw new MessageTypeException("Attempted to write null");
            }
            pk.writeNil();
            return;
        }
        pk.writeMapBegin(1);
        pk.write("unName");
        pk.write(target.getName());
        pk.writeMapEnd();
    }

    public Name read(Unpacker u, Name to, boolean required) throws IOException {
        if (!required && u.trySkipNil()) {
            return null;
        }
//        if(u.readString().equals("PTyInt")) {
//            return Name;
//        }
        return null;
    }

    static public NameTemplate getInstance() {
        return instance;
    }

    static final NameTemplate instance = new NameTemplate();

}
