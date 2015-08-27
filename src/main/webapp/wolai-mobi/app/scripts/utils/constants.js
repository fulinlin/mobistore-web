var client = angular.module('wolaiMobiApp');

client.factory('Constant', ['$location', function($location) {
	var ApiServer = 'http://101.200.189.57:9090/wolai-web/';
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
