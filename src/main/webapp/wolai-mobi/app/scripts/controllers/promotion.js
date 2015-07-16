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
	$rootScope.token = UrlUtil.getParam('token');
	var promotionId = UrlUtil.getParam('promotionId');;
	$http({
		method:'POST',
		url: Constant.ApiUrl + 'promotion/detail',
		params:{
			'token': $rootScope.token
		},
		data:  {id:promotionId}
	}).success(function(json) {
	  
      $rootScope.promotion = json.data.promotion;
      $rootScope.rewardPoints = json.data.rewardPoints;
    });
	
	$scope.enter = function() {
		if ($rootScope.promotion.code === "POINTS_EXCHANGE") {
			$location.path("/promotion/points");
		} else if ($rootScope.promotion.code === "SNAPUP_FREE") {
			$location.path("/promotion/snapup");
		}
	}
  }]);

angular.module('wolaiMobiApp')
	.controller('PointsExchangeCtrl', ['$rootScope', '$scope', '$http', 'Constant', 'UrlUtil', 
	           function ($rootScope, $scope, $http, Constant, UrlUtil) {
		
	$scope.exchangePlan = $rootScope.promotion.exchangePlanList[0];
	$scope.remain = $scope.exchangePlan.number;
	$scope.exNumber = 1;
		
	$scope.change = function() {
		if ($scope.exNumber > $scope.max) {
			$scope.exNumber = $scope.max;
		}
		if ($scope.exNumber > $scope.remain) {
			$scope.exNumber = $scope.remain;
		}
	}	
	$scope.select = function() {
		var price = $scope.exchangePlan.price;
		$scope.max = Math.floor($rootScope.rewardPoints / price);
		
		// 获取剩余个数
		$http({
			method:'POST',
			url: Constant.ApiUrl + 'promotion/exchangePlan',
			params:{
				'token': $rootScope.token
			},
			data:  {
				exchangePlanId: $scope.exchangePlan.id
			}
		}).success(function(json) {
			if (json.data.number) {
				$scope.remain = json.data.number;
			}
	    });
	}
	$scope.submit = function() {
		$http({
			method:'POST',
			url: Constant.ApiUrl + 'promotion/pointsExchange',
			params:{
				'token': $rootScope.token
			},
			data:  {
				exchangePlanId: $scope.exchangePlan.id,
				exNumber: $scope.exNumber
			}
		}).success(function(json) {
			if (json.balance) {
				$scope.rewardPoints = json.balance;
			}
			if (json.remain) {
				$scope.remain = json.remain;
			}
	    });
	}
	
	$scope.select();
}]);

angular.module('wolaiMobiApp')
	.controller('SnapupCtrl', ['$rootScope', '$scope', '$http', 'Constant', 'UrlUtil', 
	            function ($rootScope, $scope, $http, Constant, UrlUtil) {
		$scope.exchangePlan = $rootScope.promotion.exchangePlanList[0];

		$scope.submit = function() {
			$http({
				method:'POST',
				url: Constant.ApiUrl + 'promotion/snapup',
				params:{
					'token': $rootScope.token
				},
				data:  {
					exchangePlanId: $scope.exchangePlan.id
				}
			}).success(function(json) {
				console.log(json);
				if (json.remain) {
					$scope.remain = json.remain;
				}
		    });
		}
		
		$scope.remain = $scope.exchangePlan.number;
}]);
