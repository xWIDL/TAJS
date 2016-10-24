package dk.brics.tajs.analysis;


import dk.brics.tajs.analysis.dom.FileAPI;
import dk.brics.tajs.lattice.ObjectLabel;
import dk.brics.tajs.lattice.State;
import dk.brics.tajs.lattice.Value;
import dk.brics.tajs.xwidl.JRef;
import dk.brics.tajs.xwidl.LVar;
import dk.brics.tajs.xwidl.Name;

import java.util.ArrayList;

/**
 * Created by zz on 16-10-23.
 */
public class XWIDLFunctions {
    /**
     * Evaluate the native function
     */
    public static Value evaluate(XWIDLObjects nativeObject, FunctionCalls.CallInfo call, Solver.SolverInterface c) {
        int hash = c.getState().hashCode();

        System.out.println("XWIDL evaluate: " + nativeObject + " || " + call);

        ArrayList<Value> args = new ArrayList<>();
        for(int i = 0; i < call.getNumberOfArgs(); i++) {
            args.add(call.getArg(i));
        }

        Value[] argsArray = args.toArray(new Value[0]);

        State state = c.getState();

        switch (nativeObject) {
            case BLOB_CONSTRUCTOR:
                JRef r = State.getRpcInterface().construct(new Name("Blob"), argsArray, hash);// FIXME: ad-hoc
                ObjectLabel obj = new ObjectLabel(call.getSourceNode(), r);
                state.newObject(obj);
                state.writeInternalPrototype(obj, Value.makeObject(FileAPI.PROTOTYPE));
                return Value.makeObject(obj);
            case BLOB_PROTOTYPE:
                break;
            case BLOB_INSTANCES:
                break;
            case BLOB_SIZE:
                break;
            case BLOB_TYPE:
                break;
            case BLOB_ISCLOSED:
                break;
            case BLOB_SLICE:
            case BLOB_CLOSE:
                JRef thisObj;
                for (ObjectLabel label : call.prepareThis(state, state)) {
                    if (label.getKind().equals(ObjectLabel.Kind.XWIDL)) {
                        thisObj = label.getJref();
                        System.out.println(State.getRpcInterface().call(new LVar(thisObj), nativeObject.getFname(), argsArray));
                        break;
                    }
                }
        }



        // TODO: Get value back
        return Value.makeNull();
    }
}
