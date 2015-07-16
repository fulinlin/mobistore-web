'use strict';

/**
 * @ngdoc function
 * @name wolaiMobiApp.controller:PromotionCtrl
 * @description # PromotionCtrl Controller of the wolaiMobiApp
 */
angular.module('wolaiMobiApp')
  .controller('PromotionCtrl', ['$rootScope', '$scope', '$location', '$http', 'Constant', 'UrlUtil', 
                       function ($rootScope, $scope, $location, $http, Constant, UrlUtil) {
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
      $rootScope.promotion = json.data.promotion;
      $rootScope.rewardPoints = json.data.rewardPoints;
    });
	
	$scope.enter = function() {
		if ($rootScope.promotion.code === "POINTS_EXCHANGE") {
			$location.path("/promotion/points");
		}
	}
  }]);

angular.module('wolaiMobiApp')
	.controller('PointsExchangeCtrl', ['$rootScope', '$scope', '$http', 'Constant', 'UrlUtil', 
	           function ($rootScope, $scope, $http, Constant, UrlUtil) {
	$scope.change = function() {
		if ($scope.number > $scope.max) {
			$scope.number = $scope.max;
		}
	}	
	$scope.select = function() {
		var price = $scope.exchangePlan.price;
		$scope.max = Math.floor($rootScope.rewardPoints / price);
	}
	$scope.submit = function() {
		$http({
			method:'POST',
			url: Constant.ApiUrl + 'promotion/pointsExchange',
			params:{
				'token': token
			},
			data:  {
				exchangePlanId: $scope.exchangePlan.id,
				number: $scope.number
			}
		}).success(function(json) {
		  console.log(json);
	      $rootScope.promotion = json.data.promotion;
	      $rootScope.rewardPoints = json.data.rewardPoints;
	    });
	}
	
	$scope.exchangePlan = $rootScope.promotion.exchangePlanList[0];
	$scope.number = 1;
	$scope.select();
}]);

angular.module('wolaiMobiApp')
	.controller('SnaupCtrl', ['$rootScope', '$scope', '$http', 'Constant', 'UrlUtil', 
	            function ($rootScope, $scope, $http, Constant, UrlUtil) {
	
	$scope.exchangePlan = {};
	$scope.select = function() {
		console.log($scope.promotion.id);
	}
}]);
