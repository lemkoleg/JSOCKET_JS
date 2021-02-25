//import Fingerprint2 from "fingerprint2.min.js";
//require('fingerprintjs2')


// noinspection JSUnusedGlobalSymbols
export async function getBrowserFinger(finger){
// noinspection JSUnresolvedVariable,JSUnusedLocalSymbols
    let components = [
    { key: 'userAgent', getData: finger.UserAgent },
    //{ key: 'webdriver', getData: finger.webdriver },
    { key: 'language', getData: finger.languageKey },
    { key: 'colorDepth', getData: finger.colorDepthKey },
    { key: 'deviceMemory', getData: finger.deviceMemoryKey },
    { key: 'pixelRatio', getData: finger.pixelRatioKey },
    { key: 'hardwareConcurrency', getData: finger.hardwareConcurrencyKey },
    { key: 'screenResolution', getData: finger.screenResolutionKey },
    { key: 'availableScreenResolution', getData: finger.availableScreenResolutionKey },
    { key: 'timezoneOffset', getData: finger.timezoneOffset },
    { key: 'timezone', getData: finger.timezone },
    { key: 'sessionStorage', getData: finger.sessionStorageKey },
    { key: 'localStorage', getData: finger.localStorageKey },
    { key: 'indexedDb', getData: finger.indexedDbKey },
    { key: 'addBehavior', getData: finger.addBehaviorKey },
    //{ key: 'openDatabase', getData: finger.openDatabaseKey },
    { key: 'cpuClass', getData: finger.cpuClassKey },
    { key: 'platform', getData: finger.platformKey },
    { key: 'doNotTrack', getData: finger.doNotTrackKey },
    { key: 'plugins', getData: finger.pluginsComponent },
    //{ key: 'canvas', getData: finger.canvasKey },
    //{ key: 'webgl', getData: finger.webglKey },
    { key: 'webglVendorAndRenderer', getData: finger.webglVendorAndRendererKey },
    //{ key: 'adBlock', getData: finger.adBlockKey },
    { key: 'hasLiedLanguages', getData: finger.hasLiedLanguagesKey },
    { key: 'hasLiedResolution', getData: finger.hasLiedResolutionKey },
    { key: 'hasLiedOs', getData: finger.hasLiedOsKey },
    { key: 'hasLiedBrowser', getData: finger.hasLiedBrowserKey },
    //{ key: 'touchSupport', getData: finger.touchSupportKey },
    { key: 'fonts', getData: finger.jsFontsKey, pauseBefore: true },
    { key: 'fontsFlash', getData: finger.flashFontsKey, pauseBefore: true },
    //{ key: 'audio', getData: finger.audioKey },
    { key: 'enumerateDevices', getData: finger.enumerateDevicesKey }
];

let avaclubOptions = {
    preprocessor: null,
    audio: {
        timeout: 1000,
        // On iOS 11, audio context can only be used in response to user interaction.
        // We require users to explicitly enable audio fingerprinting on iOS 11.
        // See https://stackoverflow.com/questions/46363048/onaudioprocess-not-called-on-ios11#46534088
        excludeIOS11: true
    },
    fonts: {
        swfContainerId: 'fingerprintjs2',
        swfPath: 'flash/compiled/FontList.swf',
        userDefinedFonts: [],
        extendedJsFonts: false
    },
    screen: {
        // To ensure consistent fingerprints when users rotate their mobile devices
        detectScreenOrientation: true
    },
    plugins: {
        sortPluginsFor: [/palemoon/i],
        excludeIE: false
    },
    extraComponents: [],
    excludes: {
        // Unreliable on Windows, see https://github.com/Valve/fingerprintjs2/issue375
        //         's/enumerateDevices': true,
        // devicePixelRatio depends on browser zoom, and it's impossible to detect browser zoom
        'pixelRatio': true,
        // DNT depends on incognito mode for some browsers (Chrome) and it's impossible to detect incognito mode
        'doNotTrack': true,
        // uses js fonts already
        'fontsFlash': true
    },
    NOT_AVAILABLE: 'not available',
    ERROR: 'error',
    EXCLUDED: 'excluded'
};

    let murmur ="";
    await finger.getPromise(avaclubOptions).then(function (components) {
        let values = components.map(function (component) { return component.value });
        murmur = finger.x64hash128(values.join(''), 31);})
    return murmur;
}