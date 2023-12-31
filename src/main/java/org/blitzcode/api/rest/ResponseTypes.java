package org.blitzcode.api.rest;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.blitzcode.api.model.UserLessonProgress;

import java.util.List;
import java.util.Map;

/**
 * This class contains the response types for the REST API.
 * All these records are to be serialized to JSON. They are not used in the database.
 * <p>
 * There is a sample of each record in the class definition, for testing purposes.
 */
@SuppressWarnings("unused")
public class ResponseTypes {
    public record ModuleEntry(String name, String id, LessonEntry... lessons) {
        public static final ModuleEntry[] sample = new ModuleEntry[]{
                m("Essentials", l("Entry Point", 2, 2), l("Printing", 2, 2)),
                m("Variables", l("Creation", 2, 2), l("Types", 1, 2), l("Operations", 0, 2)),
                m("Decision Making", l("Conditionals", 0, 1), l("Control Flow", 1, 4)),
                m("Loops", l("For", 0, 1), l("Extras", 0, 1)),
                m("Strings", l("Handling", 1, 2), l("Manipulation", 1, 1), l("Parsing", 1, 4)),
                m("Data Structures I", l("Arrays", 1, 1), l("Lists", 1, 1), l("Sets", 1, 1), l("Stacks", 1, 2)),
                m("Data Structures II", l("Maps", 0, 1), l("Trees", 0, 1), l("Graphs", 0, 1))
        };
        private static ModuleEntry m(String name, LessonEntry... lessons) {
            return new ModuleEntry(name, "TBD", lessons);
        }
        private static LessonEntry l(String name, int sectionsCompleted, int sectionsTotal) {
            return new LessonEntry(name, "TBD", sectionsCompleted, sectionsTotal);
        }
    }

    public record LessonEntry(String name, String id, int sectionsCompleted, int sectionsTotal) {}

    public record QuestionJSON(String prompt, int answerIndex, String... choices) {
        public static final QuestionJSON[] sample = new QuestionJSON[]{
                new QuestionJSON("int x = 5;", 2, "const x = 5;", "int x = 5;", "let x = 5;"),
                new QuestionJSON("String message = \"Hello\";", 1, "String message = \"Hello\";", "let message = \"Hello\";", "var message = \"Hello\";"),
                new QuestionJSON("double price = 19.99;", 0, "let price = 19.99;", "const price = 19.99;", "double price = 19.99;"),
                new QuestionJSON("boolean isValid = true;", 2, "const isValid = true;", "boolean isValid = true;", "let isValid = true;")
        };
    }

    public record LoginInfo(@Email String email, @Size(min = 8) String password) {
        public Map<String, String> identityToolkitParams() {
            return Map.of("email", email, "password", password, "returnSecureToken", "true");
        }
    }

    public record UserData(String email, String baseLanguage, String targetLanguage, List<UserLessonProgress> userLessonProgressList){}
}
