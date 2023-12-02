package org.blitzcode.api.rest;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.util.Map;

public class ResponseTypes {
    public record ModuleEntry(String name, LessonEntry... lessons) {
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
            return new ModuleEntry(name, lessons);
        }
        private static LessonEntry l(String name, int sectionsCompleted, int sectionsTotal) {
            return new LessonEntry(name, sectionsCompleted, sectionsTotal);
        }
    }

    public record LessonEntry(String name, int sectionsCompleted, int sectionsTotal) {}

    public record Question(String prompt, int answerIndex, String... choices) {}

    public record ResetPasswordRequest(String oldPassword, @Size(min = 8) String newPassword) {}

    public record Language(String name, String imageFile) {}

    public record LoginInfo(@Email String email, @Size(min = 8) String password) {
        public Map<String, String> identityToolkitParams() {
            return Map.of("email", email, "password", password, "returnSecureToken", "true");
        }
    }
}
