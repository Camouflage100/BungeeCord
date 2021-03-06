package net.md_5.bungee.api.score;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents an objective entry.
 */
@Data @AllArgsConstructor public class Objective {

    /**
     * Name of the objective.
     */
    private final String name;
    /**
     * Type; integer or hearts
     */
    private final String type;
    /**
     * Value of the objective.
     */
    private String value;
}
