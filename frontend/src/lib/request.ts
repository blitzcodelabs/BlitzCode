const url = "http://localhost:8080"

const getPaths = ["/languages"] as const;
const postPaths = ["/signin", "/signup"] as const;
type GetPath = typeof getPaths[number];
type PostPath = typeof postPaths[number]

export const get = async (path: GetPath) => {
    return await (await fetch(url + path)).json();
}

export const post = async (path: PostPath, data: BodyInit) => {
    const res = await fetch(url + path, {
        method: "POST",
        body: data,
        headers: {
            "Content-Type": "application/json"
        }
    })
    return res;
}