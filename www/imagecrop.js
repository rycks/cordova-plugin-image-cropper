module.exports = function cropImage (image, successCallback, errorCallback) {
    return cordova.exec(successCallback, errorCallback, "ImageCropperPlugin", "cropImage", [image]);
};

