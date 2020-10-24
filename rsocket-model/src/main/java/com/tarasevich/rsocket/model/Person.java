package com.tarasevich.rsocket.model;

import lombok.Builder;
import lombok.Value;

/**
 * @author Nikolai Tarasevich
 * @since 24.10.2020
 */
@Value
@Builder
public class Person {

    String id;

    String name;

}
