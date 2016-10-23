package dk.brics.tajs.analysis;

import com.google.common.hash.HashCode;
import dk.brics.tajs.lattice.State;
import dk.brics.tajs.lattice.Value;
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

        switch (nativeObject) {
            case BLOB_CONSTRUCTOR:
                return State.getRpcInterface().construct(new Name("Blob"), argsArray, hash);// FIXME: ad-hoc
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
                State.getRpcInterface().call(nativeObject.getLVar(), nativeObject.getFname(), argsArray);
                break;
        }



        // TODO: Get value back
        return Value.makeNull();
    }
}
