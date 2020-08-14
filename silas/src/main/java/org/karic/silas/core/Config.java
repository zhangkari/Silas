package org.karic.silas.core;

import androidx.annotation.NonNull;

import org.fish.silas.Rune;

class Config {
    String id;
    String nextMatchId;
    String nextMismatchId;
    String nextExceptionId;
    int sequence;
    int groupSequence;

    public Config(@NonNull Rune rune) {
        id = rune.id();
        nextMatchId = rune.childMatchedId();
        nextMismatchId = rune.childMismatchId();
        nextExceptionId = rune.childExceptionId();
        sequence = rune.sequence();
        groupSequence = rune.groupSequence();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Config)) {
            return false;
        }

        Config node = (Config) object;
        return id.equals(node.id);
    }
}
