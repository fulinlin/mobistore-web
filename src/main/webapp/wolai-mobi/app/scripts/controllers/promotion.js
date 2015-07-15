'use strict';

/**
 * @ngdoc function
 * @name wolaiMobiApp.controller:PromotionCtrl
 * @description # PromotionCtrl Controller of the wolaiMobiApp
 */
angular.module('wolaiMobiApp')
  .controller('PromotionCtrl', ['$scope', '$http', 'Constant', function ($scope, $http, Constant) {
	var token = '9ddcb922-d235-4179-ad29-3b98dfe7cb8a';
	var id = '0AC9BA91-19B3-303E-B5B5-E578E1FAFAFA';
	$http({
		method:'POST',
		url: Constant.ApiUrl + 'promotion/detail',
		params:{
			'token': token,
			'id': id
		},
		data:  {id:id}
	}).success(function(json) {
	  console.log(json.data);
      $scope.promotion = json.data;
    });
	
	
	$scope.select = function() {
		console.log($scope.promotion.id);
	}
  }]);
