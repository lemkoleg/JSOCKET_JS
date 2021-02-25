//import Fingerprint2 from "fingerprint2.min.js";
//require('fingerprintjs2')


// noinspection JSUnusedGlobalSymbols
export async function DBrequest(ala, request){
    let result = null;
    await ala.promise(request).then(function(res){
        result = res
        console.log("request finished: " + result)
    }).catch(function(err){
        console.log("Error on AlaSQL: " + err)
    });
    return result;
    // res = ala.exec(request);

}