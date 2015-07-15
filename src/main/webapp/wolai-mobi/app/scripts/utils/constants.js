var client = angular.module('wolaiMobiApp');

client.factory('Constant', ['$location', function($location) {
	var ApiServer = 'http://localhost:8080/wolai/';
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