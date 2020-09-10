function callNativeFunction() {
  var dataMessage = document.getElementById("payload").value;
  var operatingSystem = getMobileOperatingSystem();
  if (operatingSystem === "iOS") {
    window.webkit.messageHandlers.triggerNative.postMessage(dataMessage);
  } else if (operatingSystem == "Android") {
    Android.triggerNative(dataMessage);
  } else {
  }
}

function getDataCallback(str) {
  document.getElementById("callbackDataTxt").value = str;
}

function getMobileOperatingSystem() {
  var userAgent = navigator.userAgent || navigator.vendor || window.opera;

  if (/android/i.test(userAgent)) {
    return "Android";
  }

  // iOS detection from: http://stackoverflow.com/a/9039885/177710
  if (/iPad|iPhone|iPod/.test(userAgent) && !window.MSStream) {
    return "iOS";
  }

  return "unknown";
}
