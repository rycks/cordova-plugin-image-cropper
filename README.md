# cordova-plugin-image-cropper

> Crop an image in a Ionic/Cordova app


## Install Cordova

```
& cordova plugin add https://github.com/Riyaz0001/cordova-plugin-image-cropper.git
```

## Install Ionic v3 & v4

```
& ionic cordova plugin add https://github.com/Riyaz0001/cordova-plugin-image-cropper.git
```

NOTE: Make sure your Cordova CLI version is 5.0.0+ (check with cordova -v). Cordova 4.x and below uses the now deprecated Cordova Plugin Registry as its plugin repository, so using a version of Cordova 4.x or below will result in installing an old version of this plugin.

## Usage

```js
 // callback function
ImageCropper.cropImage(imgUrl,
	// success callback
	function (imgPath) {
	    // saved cropted image path
	    console.log(imgPath);

	    // error callback
   }, function (err) { console.error(err); });
```

or, if you are running on an environment that supports Promise in Ionic v3 & v4:

```js
// callback function
   (<any>window).ImageCropper.cropImage(imgUrl,
	// success callback
	function (imgPath) {
	    // saved cropted image path
	    console.log(imgPath);

	    // error callback
   }, function (err) { console.error(err); });
```

## Plugin Working Demo:
<img src="preview.gif"  width="800" height="600" style="max-width:100%;">



### Libraries used

 * Android: [Android-Image-Cropper](https://github.com/ArthurHub/Android-Image-Cropper)

## License

MIT © [Riyaz](https://github.com/Riyaz0001)
