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
	$rootScope.loadData = function() {
		$rootScope.token = UrlUtil.getParam('token');
		$rootScope.promotionId = UrlUtil.getParam('promotionId');;
		$http({
			method:'POST',
			url: Constant.ApiUrl + 'promotion/detail',
			params:{
				'token': $rootScope.token
			},
			data:  {id:$rootScope.promotionId}
		}).success(function(json) {
			console.log(json.data);
		    $rootScope.promotion = json.data.promotion;
		    $rootScope.rewardPoints = json.data.rewardPoints;
		    
		    var promotionCode = json.data.promotion.code;
		    if (promotionCode == 'POINTS_EXCHANGE') {
		    	$location.path('/promotion/points');
		    } else if (promotionCode == 'SNAPUP_FREE') {
		    	$location.path('/promotion/snapup');
		    }
		});
    }
	
	if (!$rootScope.promotion) {
		$rootScope.loadData();
	}
}]);

angular.module('wolaiMobiApp')
	.controller('PointsExchangeCtrl', ['$rootScope', '$scope', '$http', 'Constant', 'UrlUtil', 
	           function ($rootScope, $scope, $http, Constant, UrlUtil) {
	$scope.complete = false;
	$scope.initData = function() {
		$scope.exchangePlan = $rootScope.promotion.exchangePlanList[0];
		$scope.faceValue = $scope.exchangePlan.faceValue;
		console.log($scope.exchangePlan);
		$scope.remain = $scope.exchangePlan.qty;
		$scope.price = $scope.exchangePlan.price;
		$scope.exNumber = '';
		
		$scope.max = Math.floor($rootScope.rewardPoints / $scope.price);
		if ($scope.max > $scope.remain) {
			$scope.max = $scope.remain;
		}
	}
	
	if (!$rootScope.promotion) {
		$rootScope.token = UrlUtil.getParam('token');
		$rootScope.promotionId = UrlUtil.getParam('promotionId');;
		$http({
			method:'POST',
			url: Constant.ApiUrl + 'promotion/detail',
			params:{
				'token': $rootScope.token
			},
			data:  {id:$rootScope.promotionId}
		}).success(function(json) {
			console.log(json.data);
		    $rootScope.promotion = json.data.promotion;
		    $rootScope.rewardPoints = json.data.rewardPoints; 
		    
		    $scope.initData();
		});
	} else {
		$scope.initData();
	}
		
	$scope.change = function() {
		if ($scope.exNumber > $scope.max) {
			$scope.exNumber = $scope.max;
		}
	}
	$scope.submit = function() {
		if (!$scope.exNumber) {
			return;
		}
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
			$scope.max = Math.floor($rootScope.rewardPoints / $scope.price);
			if ($scope.max > $scope.remain) {
				$scope.max = $scope.remain;
			}
			$scope.complete = true;
	    });
	}
}]);

angular.module('wolaiMobiApp')
	.controller('SnapupCtrl', ['$rootScope', '$scope', '$http', 'Constant', 'UrlUtil', 
	            function ($rootScope, $scope, $http, Constant, UrlUtil) {
		$scope.complete = false;
		
		$scope.initData = function() {
			$scope.exchangePlan = $rootScope.promotion.exchangePlanList[0];
			$scope.faceValue = $scope.exchangePlan.faceValue;
			$scope.remain = $scope.exchangePlan.qty;
		}
		
		if (!$rootScope.promotion) {
			$rootScope.token = UrlUtil.getParam('token');
			$rootScope.promotionId = UrlUtil.getParam('promotionId');;
			$http({
				method:'POST',
				url: Constant.ApiUrl + 'promotion/detail',
				params:{
					'token': $rootScope.token
				},
				data:  {id:$rootScope.promotionId}
			}).success(function(json) {
				console.log(json.data);
			    $rootScope.promotion = json.data.promotion;
			    $rootScope.rewardPoints = json.data.rewardPoints;
			    
			    $scope.initData();
			});
		} else {
			$scope.initData();
		}
		

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
				
				if (json.code < 0) {
					alert('每人只有' + json.timeLimit + '次领取机会！');
					return;
				}
				if (json.remain) {
					$scope.remain = json.remain;
				}
				$scope.complete = true;
		    });
		}
}]);
