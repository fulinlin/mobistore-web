var client = angular.module('wolaiMobiApp');

client.factory('Constant', ['$location', function($location) {
	var ApiServer = 'http://192.168.0.98:8080/wolai-web/';
	var ApiUrl = ApiServer + 'api/web/';
	
    return {
    	ApiServer: ApiServer,
        ApiUrl: ApiUrl
    };
}]);

client.factory('Vari', [function() {
    return {
        CurrentModule: null
    };
}]);
