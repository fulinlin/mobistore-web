'use strict';

/**
 * @ngdoc function
 * @name wolaiMobiApp.controller:PromotionCtrl
 * @description # PromotionCtrl Controller of the wolaiMobiApp
 */
angular.module('wolaiMobiApp')
  .controller('PromotionCtrl', ['$scope', '$http', function ($scope, $http) {
	var token = '48e51c1e-fd66-4b31-bb47-707f97e65822';
	var id = '0AC9BA91-19B3-303E-B5B5-E578E1FAFAFA';
	$http(
		{
			method:'POST',
			url:'http://localhost:8080/wolai/api/web/promotion/detail',
			params:{
				'token': token,
				'id': id
			},
			data:  {id:id}
		}
	).success(function(json) {
      $scope.promotion = json.data;
      
      console.log(json);
    });
  }]);
