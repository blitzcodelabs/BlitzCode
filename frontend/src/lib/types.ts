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
    choices: Choice[];
}

interface Choice {
    text: string;
    isCorrect: boolean;
}

export type { Module, Lesson, Question, Choice }