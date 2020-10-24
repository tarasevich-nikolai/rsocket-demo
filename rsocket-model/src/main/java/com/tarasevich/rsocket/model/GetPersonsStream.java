package com.tarasevich.rsocket.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

/**
 * @author Nikolai Tarasevich
 * @since 24.10.2020
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPersonsStream {

    private List<String> ids;

}
