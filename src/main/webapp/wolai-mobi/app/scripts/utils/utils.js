'use strict';

var client = angular.module('wolaiMobiApp');
client.factory('Util', ['Vari', function(Vari){
    return {
        getScreenSize: function() {
        	if (Var.ScreenSize) {
        		console.log("inited");
        		return Vari.ScreenSize;
        	}
            var sh = document.documentElement.clientHeight;
// if (document.body.clientHeight < sh) {
// sh = document.body.clientHeight;
// }

            var sw = document.documentElement.clientWidth;
// if (document.body.clientWidth < sw) {
// sw = document.body.clientWidth;
// }
            Vari.ScreenSize = {h: sh, w:sw};
            return Vari.ScreenSize;
        }
    }
}]);

client.factory('StringUtil', [function(){      // TODO:
    return {
        isEmpty: function (o){
            if (o === null || o === "null" || o === undefined || o === "undefined" || o === "") {
                return true;
            } else {
                return false;
            }
        },
        isNotEmpty: function (o){
        	
        }
    }
}]);

client.factory('UrlUtil', [function(){
	return {
		getParam: function(name) {
			var reg = new RegExp("(^|&|\\?)" + name + "=([^&]*)(&|$)", "i"); 
			var r = window.location.href.substr(1).match(reg);
			if (r != null) {
				return unescape(r[2]); 
			}
			return null; 
		}
	}
}]);