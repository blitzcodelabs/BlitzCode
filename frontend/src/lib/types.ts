export interface Module {
    name: string;
    lessons: Lesson[]
}

export interface Lesson {
    name: string;
    progress: number;
}