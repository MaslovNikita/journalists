/**
 * Created by homie on 24.10.16.
 */
function newXMLHttpRequest() {
    var xmlreq = false;
    if (window.XMLHttpRequest) {
    } else if (window.ActiveXObject) {
        try {
            xmlreq = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (e1) {
            try {
                xmlreq = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (e2) {
            }
        }
    }
    return xmlreq;
}