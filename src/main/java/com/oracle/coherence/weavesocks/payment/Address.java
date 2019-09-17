package com.oracle.coherence.weavesocks.payment;

import com.oracle.io.pof.annotation.Portable;
import com.oracle.io.pof.annotation.PortableType;

@PortableType(id = 3)
public class Address {
    @Portable String number;
    @Portable String street;
    @Portable String city;
    @Portable String postcode;
    @Portable String country;

    @Override
    public String toString() {
        return "Address{" +
                "number='" + number + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", postcode='" + postcode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
