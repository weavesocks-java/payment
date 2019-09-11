package com.oracle.coherence.weavesocks.payment;

import java.io.Serializable;

import com.oracle.io.pof.annotation.Portable;
import com.oracle.io.pof.annotation.PortableType;

@PortableType(id = 2)
public class Authorization implements Serializable {
    @Portable private boolean authorised = false;
    @Portable private String message;

    public Authorization() {
    }

    public Authorization(boolean authorised, String message) {
        this.authorised = authorised;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Authorization{" +
                "authorised=" + authorised +
                ", message=" + message +
                '}';
    }

    public boolean isAuthorised() {
        return authorised;
    }

    public void setAuthorised(boolean authorised) {
        this.authorised = authorised;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
