package dk.brics.tajs.analysis;

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
public class XWIDLObjects implements HostObject {

    private LVar lvar;
    private Name fname;

    @Override
    public HostAPI getAPI() {
        return XWIDL_CUSTOM;
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
        return lvar + "." + fname.getName();
    }
}
