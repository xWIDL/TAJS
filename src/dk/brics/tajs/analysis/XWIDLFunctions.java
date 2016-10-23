package dk.brics.tajs.analysis;

import dk.brics.tajs.lattice.Value;

/**
 * Created by zz on 16-10-23.
 */
public class XWIDLFunctions {
    /**
     * Evaluate the native function
     */
    public static Value evaluate(XWIDLObjects nativeObject, FunctionCalls.CallInfo call, Solver.SolverInterface c) {
        System.out.println("XWIDL evaluate: " + nativeObject + " || " + call);

        // TODO: Get value back
        return Value.makeNull();
    }
}
