package dk.brics.tajs.analysis;

import dk.brics.tajs.lattice.State;
import dk.brics.tajs.lattice.Value;

import java.util.ArrayList;

/**
 * Created by zz on 16-10-23.
 */
public class XWIDLFunctions {
    /**
     * Evaluate the native function
     */
    public static Value evaluate(XWIDLObjects nativeObject, FunctionCalls.CallInfo call, Solver.SolverInterface c) {
        System.out.println("XWIDL evaluate: " + nativeObject + " || " + call);

        ArrayList<Value> args = new ArrayList<>();
        for(int i = 0; i < call.getNumberOfArgs(); i++) {
            args.add(call.getArg(i));
        }

        State.getRpcInterface().call(nativeObject.getLVar(), nativeObject.getFname(), args.toArray(new Value[0]));

        // TODO: Get value back
        return Value.makeNull();
    }
}
