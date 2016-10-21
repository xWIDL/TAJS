package dk.brics.tajs.xwidl;

import org.msgpack.packer.Packer;

import java.io.IOException;

/**
 * Created by zz on 16-10-21.
 */
public interface ADT {

    void writeContents(Packer pk) throws IOException;

    String getTag();
}
