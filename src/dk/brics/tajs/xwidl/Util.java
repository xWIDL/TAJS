package dk.brics.tajs.xwidl;

import org.msgpack.packer.Packer;

import java.io.IOException;

/**
 * Created by zz on 16-10-21.
 */
public class Util {
    public static void writeADTBranch(Packer pk, ADT t) throws IOException {
        pk.writeMapBegin(2);
        pk.write(new String[] { "tag", t.getTag() });
        pk.writeArrayBegin(2);
        pk.write("contents");
        t.writeContents(pk);
        pk.writeArrayEnd();
        pk.writeMapEnd();
    }

}
