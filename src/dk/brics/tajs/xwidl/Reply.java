package dk.brics.tajs.xwidl;

import dk.brics.tajs.lattice.Value;

/**
 * Created by zz on 16-10-24.
 */
public class Reply {
    public enum ReplyKind {
        Sat("Sat"), Unsat("Unsat"), InvalidRequest("InvalidRequest");
        private String name;
        ReplyKind(String name) {
            this.name = name;
        }
    }

    private ReplyKind kind;

    // Sat
    private Value retVal;

    // Unsat/Invalid
    private String reason;

    private Reply() {}

    public Reply(Value v) {
        this.kind = ReplyKind.Sat;
        this.retVal = v;
    }

    public static Reply makeUnsatReply(String s) {
        Reply r = new Reply();
        r.kind = ReplyKind.Unsat;
        r.reason = s;
        return r;
    }

    public static Reply makeInvalidReply(String s) {
        Reply r = new Reply();
        r.kind = ReplyKind.InvalidRequest;
        r.reason = s;
        return r;
    }

    @Override
    public String toString() {
        switch (this.kind) {
            case Sat:
                return "Sat: " + this.retVal;
            case Unsat:
                return "Unsat: " + this.reason;
            case InvalidRequest:
                return "Invalid request: " + this.reason;
            default:
                return super.toString();
        }
    }
}
