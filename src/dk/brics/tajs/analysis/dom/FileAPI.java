package dk.brics.tajs.analysis.dom;

import dk.brics.tajs.analysis.InitialStateBuilder;
import dk.brics.tajs.analysis.PropVarOperations;
import dk.brics.tajs.analysis.Solver;
import dk.brics.tajs.lattice.ObjectLabel;
import dk.brics.tajs.lattice.State;
import dk.brics.tajs.lattice.Value;

import static dk.brics.tajs.analysis.dom.DOMFunctions.createDOMFunction;
import static dk.brics.tajs.analysis.dom.DOMFunctions.createDOMProperty;

/**
 * Created by zz on 16-10-23.
 */
public class FileAPI {

    public static ObjectLabel CONSTRUCTOR;

    public static ObjectLabel PROTOTYPE;

    public static ObjectLabel INSTANCES;

    public static void build(Solver.SolverInterface c) {
        State s = c.getState();
        PropVarOperations pv = c.getAnalysis().getPropVarOperations();
        CONSTRUCTOR = new ObjectLabel(DOMObjects.BLOB_CONSTRUCTOR, ObjectLabel.Kind.FUNCTION);
        PROTOTYPE = new ObjectLabel(DOMObjects.BLOB_PROTOTYPE, ObjectLabel.Kind.OBJECT);
        INSTANCES = new ObjectLabel(DOMObjects.BLOB_INSTANCES, ObjectLabel.Kind.OBJECT);

        // Constructor Object
        s.newObject(CONSTRUCTOR);
        pv.writePropertyWithAttributes(CONSTRUCTOR, "length", Value.makeNum(0).setAttributes(true, true, true));
        pv.writePropertyWithAttributes(CONSTRUCTOR, "prototype", Value.makeObject(PROTOTYPE).setAttributes(true, true, true));
        s.writeInternalPrototype(CONSTRUCTOR, Value.makeObject(InitialStateBuilder.OBJECT_PROTOTYPE));
        pv.writePropertyWithAttributes(InitialStateBuilder.GLOBAL, "Blob", Value.makeObject(CONSTRUCTOR).setAttributes(true, false, false));

        // Prototype object.
        s.newObject(PROTOTYPE);
        s.writeInternalPrototype(PROTOTYPE, Value.makeObject(InitialStateBuilder.OBJECT_PROTOTYPE));

        // Multiplied object.
        s.newObject(INSTANCES);
        s.writeInternalPrototype(INSTANCES, Value.makeObject(PROTOTYPE));

        /*
         * Properties.
         */

        createDOMProperty(INSTANCES, "size", Value.makeAnyNum(), c);
        createDOMProperty(INSTANCES, "type", Value.makeAnyStr(), c);
        createDOMProperty(INSTANCES, "isClosed", Value.makeAnyBool(), c);

        /*
         * Functions.
         */

        createDOMFunction(PROTOTYPE, DOMObjects.BLOB_SLICE, "slice", 3, c);
        createDOMFunction(PROTOTYPE, DOMObjects.BLOB_CLOSE, "close", 0, c);
    }
}