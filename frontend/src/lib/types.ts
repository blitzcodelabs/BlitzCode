interface Module {
    name: string;
    id: string;
    lessons: Lesson[]
}

interface Lesson {
    name: string;
    id: string;
    sectionsCompleted: number;
    sectionsTotal: number;
}

interface Question {
    prompt: string;
    answerIndex: number;
    choices: string[];
}

interface Language {
    fullName: string;
    shortName: string;
    id: string;
}

export type { Module, Lesson, Question, Language }