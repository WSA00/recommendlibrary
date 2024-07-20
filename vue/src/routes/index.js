import home from "./home"
import login from "./login"
import space from "./space"
import notice from "./notice"
import search from "./search"

export default () => {
    return [
        ...home,
        login,
        space,
        notice,
        search
    ]
}