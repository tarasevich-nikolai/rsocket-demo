package com.tarasevich.rsocket.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Value;

/**
 * @author Nikolai Tarasevich
 * @since 24.10.2020
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPersonsChannel {

    private String id;

}
