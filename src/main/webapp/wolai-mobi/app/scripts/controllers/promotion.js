'use strict';

/**
 * @ngdoc function
 * @name wolaiMobiApp.controller:PromotionCtrl
 * @description # PromotionCtrl Controller of the wolaiMobiApp
 */
angular.module('wolaiMobiApp')
  .controller('PromotionCtrl', ['$scope', '$http', 'Constant', 'UrlUtil', function ($scope, $http, Constant, UrlUtil) {
	// http://localhost:9000/#/promotion?token=0658673a-c421-4980-bbd4-35374aefb094&promotionId=0AC9BA91-19B3-303E-B5B5-E578E1FAFAFA
	var token = UrlUtil.getParam('token');
	var promotionId = UrlUtil.getParam('promotionId');;
	$http({
		method:'POST',
		url: Constant.ApiUrl + 'promotion/detail',
		params:{
			'token': token
		},
		data:  {id:promotionId}
	}).success(function(json) {
	  console.log(json);
      $scope.promotion = json.data;
    });
	
	
	$scope.select = function() {
		console.log($scope.promotion.id);
	}
  }]);
