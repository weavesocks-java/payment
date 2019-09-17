package com.oracle.coherence.weavesocks.payment;

import com.oracle.io.pof.annotation.Portable;
import com.oracle.io.pof.annotation.PortableType;

@PortableType(id = 4)
public class Card {
    @Portable String longNum;
    @Portable String expires;
    @Portable String ccv;

    @Override
    public String toString() {
        return "Card{" +
                "longNum='" + longNum + '\'' +
                ", expires='" + expires + '\'' +
                ", ccv='" + ccv + '\'' +
                '}';
    }
}
