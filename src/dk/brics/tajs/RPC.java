package dk.brics.tajs;

import dk.brics.tajs.lattice.Value;
import dk.brics.tajs.xwidl.Name;
import dk.brics.tajs.xwidl.NameTemplate;
import dk.brics.tajs.xwidl.PrimType;
import dk.brics.tajs.xwidl.PrimTypeTemplate;
import dk.brics.tajs.xwidl.ValueTemplate;
import org.msgpack.rpc.Client;
import org.msgpack.rpc.loop.EventLoop;

/**
 * Created by zz on 16-10-21.
 */
public class RPC {
    private static class SpawnRequest {
        private SpawnRequest(final int clientCount, final int requestCount, final RPCInterface iface) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int rc = 1; rc <= requestCount; rc++) {
                        System.out.println("(" + clientCount + ":" + rc + ") 10 + 2 = " + iface.add(10, 2));
                        System.out.println("(" + clientCount + ":" + rc + ") 10 - 2 = " + iface.sub(10, 2));
                        System.out.println("(" + clientCount + ":" + rc + ") 10 * 2 = " + iface.mul(10, 2));
                        System.out.println("(" + clientCount + ":" + rc + ") 10 / 2 = " + iface.div(10, 2));
                        System.out.println(iface.unknown(Value.makeBool(true)));
                    }
                }
            }).start();
        }
    }

    public interface RPCInterface {
        int add(int a, int b);
        int sub(int a, int b);
        int mul(int a, int b);
        double div(int a, int b);
        int unknown(Value ty);
    }

    public static void run() throws Exception {
        EventLoop loop = EventLoop.defaultEventLoop();

        Client client = new Client("localhost", 8888, loop);

        loop.getMessagePack().register(PrimType.class, PrimTypeTemplate.getInstance());
        loop.getMessagePack().register(Name.class, NameTemplate.getInstance());
        loop.getMessagePack().register(Value.class, ValueTemplate.getInstance());

        RPCInterface iface = client.proxy(RPCInterface.class);

        final int CLIENT_COUNT = 1;
        final int REQ_COUNT = 1;

        for(int cc = 1; cc <= CLIENT_COUNT; cc++) {
            new SpawnRequest(cc, REQ_COUNT, iface);
        }
    }
}
