import { notify } from 'react-notify-toast';
import jQuery from 'jquery';

var appServerAddress = "http://localhost:8080";
function sendRequest(uri, method, parameters, callback) {
    console.log("sending request")
    return jQuery.ajax({
        url: appServerAddress + uri,
        type: method,
        contentType: "application/json",
        data: parameters,
        success: function (result) {
            callback(result);
            //no error
            // if (result[0] === 0) {
            //     // handle the response with callback function
            //     callback(result[1]);
            // }
            // else {
            //     //alert the error
            //     notify.show('','error', 2000);
            //     console.log("error")
        //     }
        }
    });
} 

export default sendRequest;