'use strict';

var client = angular.module('wolaiMobiApp');

client.filter('deviceThumb', ['Constant', function(Constant) {
    return function(url) {
    	var ret = "upload/equipment/icon/" + url
        return ret;
    }
}]);

client.filter('remotePath', ['Constant', function(Constant) {
    return function(url) {
    	var ret = Constant.ApiServer + url
        return ret;
    }
}]);
