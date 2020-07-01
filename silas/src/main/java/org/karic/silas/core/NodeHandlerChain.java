package org.karic.silas.core;

import org.fish.silas.Rune;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NodeHandlerChain implements HandlerChain {
    private List<NodeWrapper> mNodes;
    private List<Handler> mHandlers;
    private Handler mNext;

    public NodeHandlerChain() {
        mHandlers = new ArrayList<>(32);
    }

    @Override
    public void addNode(Node... arg) {
        List<NodeWrapper> nodes = new ArrayList<>(32);
        mNodes = nodes;
        for (int i = 0; i < arg.length; i++) {
            Node node = arg[i];
            Rune rune = node.getClass().getAnnotation(Rune.class);
            if (rune == null) {
                continue;
            }

            NodeWrapper wrapper = new NodeWrapper(node);
            nodes.add(wrapper);
            wrapper.id = rune.id();
            wrapper.nextMatchId = rune.childMatchedId();
            wrapper.nextErrorId = rune.childMismatchId();
            wrapper.nextExceptionId = rune.childExceptionId();
            wrapper.groupSequence = rune.groupSequence();
            if (rune.sequence() == -1) {
                wrapper.sequence = i;
            } else {
                wrapper.sequence = rune.sequence();
            }
        }

        Collections.sort(nodes, new Comparator<NodeWrapper>() {
            @Override
            public int compare(NodeWrapper o1, NodeWrapper o2) {
                if (o1.groupSequence != o2.groupSequence) {
                    return o1.groupSequence - o2.groupSequence;
                } else {
                    return o1.sequence - o2.sequence;
                }
            }
        });

        resolveNodeWrapper();
        mHandlers.get(0).dispose();
    }

    @Override
    public void dispose() {
        for (Handler handler : mHandlers) {
            handler.dispose();
        }
    }

    @Override
    public Handler getNext() {
        return mNext;
    }

    @Override
    public void setNext(Handler handler) {
        mNext = handler;
    }

    private void resolveNodeWrapper() {
        // todo
    }
}
