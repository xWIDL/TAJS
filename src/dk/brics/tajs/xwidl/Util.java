package dk.brics.tajs.xwidl;

import org.msgpack.packer.Packer;

import java.io.IOException;

/**
 * Created by zz on 16-10-21.
 */
public class Util {
    public static void writeADTBranch(Packer pk, ADT t) throws IOException {
        pk.writeMapBegin(2);
        pk.write("tag");
        pk.write(t.getTag());
        pk.write("contents");
        t.writeContents(pk);
        pk.writeMapEnd();
    }
}
