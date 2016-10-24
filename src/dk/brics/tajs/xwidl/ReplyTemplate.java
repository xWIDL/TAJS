package dk.brics.tajs.xwidl;

import dk.brics.tajs.lattice.ObjectLabel;
import dk.brics.tajs.lattice.Value;
import org.msgpack.packer.Packer;
import org.msgpack.template.AbstractTemplate;
import org.msgpack.unpacker.Unpacker;

import java.io.IOException;

/**
 * Created by zz on 16-10-24.
 */
public class ReplyTemplate extends AbstractTemplate<Reply> {
    private ReplyTemplate() {

    }

    public void write(Packer pk, Reply target, boolean required) throws IOException {
        throw new IOException("No serialization of Reply on the client side");
    }

    public Reply read(Unpacker u, Reply to, boolean required) throws IOException {
        if (!required && u.trySkipNil()) {
            return null;
        }

        u.readMapBegin();
        u.read("tag");
        switch (u.readString()) {
            case "Sat":
                u.read("contents");
                u.readArrayBegin();
                u.readMapBegin();
                u.read("tag");
                switch (u.readString()) {
                    case "JVRPrim":
                        u.read("contents");
                        u.readArrayBegin();
                        switch (u.readString()) {
                            case "PTyNull": return new Reply(Value.makeNull());
                            default: return null;
                        }
                    case "JVRRRef":
                        u.read("contents");
                        u.readArrayBegin();
                        JRef ref = JRefTemplate.instance.read(u, null, required);
                        return new Reply(Value.makeObject(new ObjectLabel(null, ref)));
                    default:
                        return null;
                }
            case "Unsat":
                u.read("contents");
                return Reply.makeUnsatReply(u.readString());
            case "InvalidRequst":
                u.read("contents");
                return Reply.makeInvalidReply(u.readString());

        }

        return null;
    }

    static public ReplyTemplate getInstance() {
        return instance;
    }

    static final ReplyTemplate instance = new ReplyTemplate();

}
