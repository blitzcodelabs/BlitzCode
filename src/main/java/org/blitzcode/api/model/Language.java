package org.blitzcode.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Language {
    JAVA("Java", "Java"), PYTHON("py", "Python"), JAVASCRIPT("JS", "JavaScript");

    public final String shortName, fullName, id;

    Language(String shortName, String fullName) {
        this.shortName = shortName;
        this.fullName = fullName;
        id = name();
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public static Language fromString(@JsonProperty("id") String id) {
        return Language.valueOf(id);
    }
}
