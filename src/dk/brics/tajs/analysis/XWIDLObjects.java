package dk.brics.tajs.analysis;

import dk.brics.tajs.analysis.dom.DOMObjects;
import dk.brics.tajs.lattice.HostAPI;
import dk.brics.tajs.lattice.HostObject;
import dk.brics.tajs.lattice.ObjectLabel;
import dk.brics.tajs.lattice.State;
import dk.brics.tajs.lattice.Str;
import dk.brics.tajs.lattice.Value;
import dk.brics.tajs.xwidl.LVar;
import dk.brics.tajs.xwidl.Name;

import static dk.brics.tajs.analysis.HostAPIs.XWIDL_CUSTOM;

/**
 * Created by zz on 16-10-23.
 */
public enum XWIDLObjects implements HostObject {

    // File API TODO: Make this registration part automated
    BLOB_CONSTRUCTOR("Blob constructor"),
    BLOB_PROTOTYPE("Blob.prototype"),
    BLOB_INSTANCES("Blob instances"),
    BLOB_SIZE("Blob.size"),
    BLOB_TYPE("Blob.type"),
    BLOB_ISCLOSED("Blob.isClosed"),
    BLOB_SLICE("Blob.prototype.slice"),
    BLOB_CLOSE("Blob.close");

    private LVar lvar;
    private Name fname;

    @Override
    public HostAPI getAPI() {
        return XWIDL_CUSTOM;
    }

    private HostAPI api;
    private String string;

    XWIDLObjects(String str) {
        api = HostAPIs.XWIDL_CUSTOM;
        string = str;
    }

    @Override
    public void evaluateSetter(ObjectLabel objlabel, Str prop, Value value, State state) {

    }

    public Name getFname() {
        return fname;
    }

    public LVar getLVar() {
        return lvar;
    }

    @Override
    public String toString() {
        if (lvar != null && fname != null)
            return lvar + "." + fname.getName();
        else
            return string;
    }
}
