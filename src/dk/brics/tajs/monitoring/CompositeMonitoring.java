package dk.brics.tajs.monitoring;

import dk.brics.tajs.flowgraph.AbstractNode;
import dk.brics.tajs.flowgraph.BasicBlock;
import dk.brics.tajs.flowgraph.FlowGraph;
import dk.brics.tajs.flowgraph.Function;
import dk.brics.tajs.flowgraph.SourceLocation;
import dk.brics.tajs.flowgraph.jsnodes.IfNode;
import dk.brics.tajs.flowgraph.jsnodes.Node;
import dk.brics.tajs.flowgraph.jsnodes.ReadPropertyNode;
import dk.brics.tajs.flowgraph.jsnodes.ReadVariableNode;
import dk.brics.tajs.lattice.CallEdge;
import dk.brics.tajs.lattice.Context;
import dk.brics.tajs.lattice.HostObject;
import dk.brics.tajs.lattice.ObjectLabel;
import dk.brics.tajs.lattice.State;
import dk.brics.tajs.lattice.Str;
import dk.brics.tajs.lattice.Value;
import dk.brics.tajs.solver.CallGraph;
import dk.brics.tajs.solver.Message;
import dk.brics.tajs.util.AnalysisException;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static dk.brics.tajs.util.Collections.newMap;
import static org.mockito.internal.util.collections.Sets.newSet;

/**
 * Composite, delegating, implementation of IAnalysisMonitoring.
 * Enables multiple, independent IAnalysisMonitoring implementations to be used together.
 */
public class CompositeMonitoring implements IAnalysisMonitoring {

    private final IAnalysisMonitoring m1;

    private final IAnalysisMonitoring m2;

    public CompositeMonitoring(IAnalysisMonitoring m1, IAnalysisMonitoring m2) {
        this.m1 = m1;
        this.m2 = m2;
    }

    @SuppressWarnings("unused")
    public static IAnalysisMonitoring buildFromList(IAnalysisMonitoring... monitors) {
        return buildFromList(Arrays.asList(monitors));
    }

    public static IAnalysisMonitoring buildFromList(List<IAnalysisMonitoring> monitors) {
        LinkedList<IAnalysisMonitoring> linked = new LinkedList<>();
        linked.addAll(monitors);
        return buildFromList(linked, CompositeMonitoring::new);
    }

    protected static IAnalysisMonitoring buildFromList(LinkedList<IAnalysisMonitoring> monitors, Factory<IAnalysisMonitoring> f) {
        if (monitors.isEmpty()) {
            throw new AnalysisException("Trying to create empty composite monitor?!");
        }
        IAnalysisMonitoring head = monitors.removeFirst();
        if (monitors.isEmpty()) {
            return head;
        }
        return f.build(head, buildFromList(monitors, f));
    }

    @Override
    public void addMessage(AbstractNode n, Message.Severity severity, String msg) {
        m1.addMessage(n, severity, msg);
        m2.addMessage(n, severity, msg);
    }

    @Override
    public void addMessage(AbstractNode n, Message.Severity severity, String key, String msg) {
        m1.addMessage(n, severity, key, msg);
        m2.addMessage(n, severity, key, msg);
    }

    @Override
    public void addMessageInfo(AbstractNode n, Message.Severity severity, String msg) {
        m1.addMessageInfo(n, severity, msg);
        m2.addMessageInfo(n, severity, msg);
    }

    @Override
    public boolean allowNextIteration() {
        return m1.allowNextIteration() && m2.allowNextIteration();
    }

    @Override
    public void beginPhase(AnalysisPhase phase) {
        m1.beginPhase(phase);
        m2.beginPhase(phase);
    }

    @Override
    public void endPhase(AnalysisPhase phase) {
        m1.endPhase(phase);
        m2.endPhase(phase);
    }

    @Override
    public Set<Message> getMessages() {
        Set<Message> messages = newSet();
        if (m1.getMessages() != null) {
            messages.addAll(m1.getMessages());
        }
        if (m2.getMessages() != null) {
            messages.addAll(m2.getMessages());
        }
        return messages;
    }

    @Override
    public Map<TypeCollector.VariableSummary, Value> getTypeInformation() {
        Map<TypeCollector.VariableSummary, Value> map = newMap();
        if (m1.getTypeInformation() != null) {
            map.putAll(m1.getTypeInformation());
        }
        if (m2.getTypeInformation() != null) {
            map.putAll(m2.getTypeInformation());
        }
        return map;
    }

    @Override
    public void setCallGraph(CallGraph<State, Context, CallEdge> callGraph) {
        m1.setCallGraph(callGraph);
        m2.setCallGraph(callGraph);
    }

    @Override
    public void setFlowgraph(FlowGraph fg) {
        m1.setFlowgraph(fg);
        m2.setFlowgraph(fg);
    }

    @Override
    public void visitBlockTransfer(BasicBlock b, State s) {
        m1.visitBlockTransfer(b, s);
        m2.visitBlockTransfer(b, s);
    }

    @Override
    public void visitCall(AbstractNode n, boolean maybe_non_function, boolean maybe_function, State state) {
        m1.visitCall(n, maybe_non_function, maybe_function, state);
        m2.visitCall(n, maybe_non_function, maybe_function, state);
    }

    @Override
    public void visitEvalCall(AbstractNode n, Value v) {
        m1.visitEvalCall(n, v);
        m2.visitEvalCall(n, v);
    }

    @Override
    public void visitFunction(Function f, Collection<State> entry_states) {
        m1.visitFunction(f, entry_states);
        m2.visitFunction(f, entry_states);
    }

    @Override
    public void visitIf(IfNode n, Value v) {
        m1.visitIf(n, v);
        m2.visitIf(n, v);
    }

    @Override
    public void visitIn(AbstractNode n, boolean maybe_v2_object, boolean maybe_v2_nonobject) {
        m1.visitIn(n, maybe_v2_object, maybe_v2_nonobject);
        m2.visitIn(n, maybe_v2_object, maybe_v2_nonobject);
    }

    @Override
    public void visitInnerHTMLWrite(Node n, Value v) {
        m1.visitInnerHTMLWrite(n, v);
        m2.visitInnerHTMLWrite(n, v);
    }

    @Override
    public void visitInstanceof(AbstractNode n, boolean maybe_v2_non_function, boolean maybe_v2_function, boolean maybe_v2_prototype_primitive, boolean maybe_v2_prototype_nonprimitive) {
        m1.visitInstanceof(n, maybe_v2_non_function, maybe_v2_function, maybe_v2_prototype_primitive, maybe_v2_prototype_nonprimitive);
        m2.visitInstanceof(n, maybe_v2_non_function, maybe_v2_function, maybe_v2_prototype_primitive, maybe_v2_prototype_nonprimitive);
    }

    @Override
    public void visitJoin() {
        m1.visitJoin();
        m2.visitJoin();
    }

    @Override
    public void visitPostBlockTransfer(BasicBlock b, State state) {
        m1.visitPostBlockTransfer(b, state);
        m2.visitPostBlockTransfer(b, state);
    }

    @Override
    public void visitNativeFunctionCall(AbstractNode n, HostObject hostobject, boolean num_actuals_unknown, int num_actuals, int min, int max) {
        m1.visitNativeFunctionCall(n, hostobject, num_actuals_unknown, num_actuals, min, max);
        m2.visitNativeFunctionCall(n, hostobject, num_actuals_unknown, num_actuals, min, max);
    }

    @Override
    public void visitNewFlow(BasicBlock b, Context c, State s, String diff, String info) {
        m1.visitNewFlow(b, c, s, diff, info);
        m2.visitNewFlow(b, c, s, diff, info);
    }

    @Override
    public void visitNodeTransfer(AbstractNode n) {
        m1.visitNodeTransfer(n);
        m2.visitNodeTransfer(n);
    }

    @Override
    public void visitPropertyAccess(Node n, Value baseval) {
        m1.visitPropertyAccess(n, baseval);
        m2.visitPropertyAccess(n, baseval);
    }

    @Override
    public void visitPropertyRead(AbstractNode n, Set<ObjectLabel> objs, Str propertystr, State state, boolean check_unknown) {
        m1.visitPropertyRead(n, objs, propertystr, state, check_unknown);
        m2.visitPropertyRead(n, objs, propertystr, state, check_unknown);
    }

    @Override
    public void visitPropertyWrite(Node n, Set<ObjectLabel> objs, Str propertystr) {
        m1.visitPropertyWrite(n, objs, propertystr);
        m2.visitPropertyWrite(n, objs, propertystr);
    }

    @Override
    public void visitReachableNode(AbstractNode n) {
        m1.visitReachableNode(n);
        m2.visitReachableNode(n);
    }

    @Override
    public void visitRead(Node n, Value v, State state) {
        m1.visitRead(n, v, state);
        m2.visitRead(n, v, state);
    }

    @Override
    public void visitReadNonThisVariable(ReadVariableNode n, Value v) {
        m1.visitReadNonThisVariable(n, v);
        m2.visitReadNonThisVariable(n, v);
    }

    @Override
    public void visitReadProperty(ReadPropertyNode n, Set<ObjectLabel> objlabels, Str propertystr, boolean maybe, State state, Value v) {
        m1.visitReadProperty(n, objlabels, propertystr, maybe, state, v);
        m2.visitReadProperty(n, objlabels, propertystr, maybe, state, v);
    }

    @Override
    public void visitReadThis(ReadVariableNode n, Value v, State state, ObjectLabel global_obj) {
        m1.visitReadThis(n, v, state, global_obj);
        m2.visitReadThis(n, v, state, global_obj);
    }

    @Override
    public void visitReadVariable(ReadVariableNode n, Value v, State state) {
        m1.visitReadVariable(n, v, state);
        m2.visitReadVariable(n, v, state);
    }

    @Override
    public void visitRecoveryGraph(int size) {
        m1.visitRecoveryGraph(size);
        m2.visitRecoveryGraph(size);
    }

    @Override
    public void visitUnknownValueResolve(boolean partial, boolean scanning) {
        m1.visitUnknownValueResolve(partial, scanning);
        m2.visitUnknownValueResolve(partial, scanning);
    }

    @Override
    public void visitUserFunctionCall(Function f, AbstractNode call, boolean constructor) {
        m1.visitUserFunctionCall(f, call, constructor);
        m2.visitUserFunctionCall(f, call, constructor);
    }

    @Override
    public void visitVariableAsRead(ReadVariableNode n, Value v, State state) {
        m1.visitVariableAsRead(n, v, state);
        m2.visitVariableAsRead(n, v, state);
    }

    @Override
    public void visitVariableOrProperty(String var, SourceLocation loc, Value value, Context context, State state) {
        m1.visitVariableOrProperty(var, loc, value, context, state);
        m2.visitVariableOrProperty(var, loc, value, context, state);
    }

    protected interface Factory<T> {

        T build(T e1, T e2);
    }
}
