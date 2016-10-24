/**
 * Created by homie on 19.10.16.
 */
function hidePopup() {
    var background = document.getElementById('shadow-background');
    var popupPhoto = document.getElementById('popup-upload-photo');
    var popupMessage = document.getElementById('popup-message-div');

    background.style.visibility = 'hidden';
    popupPhoto.style.visibility = 'hidden';
    popupMessage.style.visibility = 'hidden';
}

function showPhotoUpload() {
    var background = document.getElementById('shadow-background');
    var popup = document.getElementById('popup-upload-photo');

    background.style.visibility = 'visible';
    popup.style.visibility = 'visible';
}

function showMessageDiv() {
    var background = document.getElementById('shadow-background');
    var popup = document.getElementById('popup-message-div');

    background.style.visibility = 'visible';
    popup.style.visibility = 'visible';
}