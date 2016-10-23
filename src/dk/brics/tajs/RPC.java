package dk.brics.tajs;

import dk.brics.tajs.lattice.Value;
import dk.brics.tajs.xwidl.JRef;
import dk.brics.tajs.xwidl.JRefTemplate;
import dk.brics.tajs.xwidl.JsExpr;
import dk.brics.tajs.xwidl.JsExprTemplate;
import dk.brics.tajs.xwidl.LVar;
import dk.brics.tajs.xwidl.LVarTemplate;
import dk.brics.tajs.xwidl.Name;
import dk.brics.tajs.xwidl.NameTemplate;
import dk.brics.tajs.xwidl.Prim;
import dk.brics.tajs.xwidl.PrimTemplate;
import dk.brics.tajs.xwidl.PrimType;
import dk.brics.tajs.xwidl.PrimTypeTemplate;
import dk.brics.tajs.xwidl.RelBiOp;
import dk.brics.tajs.xwidl.RelBiOpTemplate;
import dk.brics.tajs.xwidl.ValueTemplate;
import org.msgpack.rpc.Client;
import org.msgpack.rpc.loop.EventLoop;

/**
 * Created by zz on 16-10-21.
 */
public class RPC {

    public interface RPCInterface {
        // TODO: Accept the reply

        void call(LVar lvar, Name fname, Value[] vals);
    }

    public static void run() throws Exception {
        EventLoop loop = EventLoop.defaultEventLoop();

        Client client = new Client("localhost", 8888, loop);

        loop.getMessagePack().register(PrimType.class, PrimTypeTemplate.getInstance());
        loop.getMessagePack().register(Name.class, NameTemplate.getInstance());
        loop.getMessagePack().register(Value.class, ValueTemplate.getInstance());
        loop.getMessagePack().register(JsExpr.class, JsExprTemplate.getInstance());
        loop.getMessagePack().register(RelBiOp.class, RelBiOpTemplate.getInstance());
        loop.getMessagePack().register(Prim.class, PrimTemplate.getInstance());
        loop.getMessagePack().register(LVar.class, LVarTemplate.getInstance());
        loop.getMessagePack().register(JRef.class, JRefTemplate.getInstance());

        RPCInterface iface = client.proxy(RPCInterface.class);

        iface.call(new LVar(new Name("x")), new Name("f"), new Value[0]);

    }
}
