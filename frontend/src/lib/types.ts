interface Module {
    name: string;
    lessons: Lesson[]
}

interface Lesson {
    name: string;
    progress: number;
}

interface Question {
    prompt: string;
    answerIndex: number;
    choices: string[];
}

interface Language {
    name: string;
    imageFile: string;
}

export type { Module, Lesson, Question, Language }