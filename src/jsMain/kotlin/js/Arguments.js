// noinspection JSUnusedGlobalSymbols
export  function createArguments(){
    let arr = [];
    for (let i = 0; i < arguments.length; i++) {
        arr[i] = arguments[i];
    }
    return arr;
}