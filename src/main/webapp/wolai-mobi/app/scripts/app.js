'use strict';

/**
 * @ngdoc overview
 * @name wolaiMobiApp
 * @description
 * # wolaiMobiApp
 *
 * Main module of the application.
 */
angular
  .module('wolaiMobiApp', [
    'ngAnimate',
    'ngAria',
    'ngCookies',
    'ngMessages',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl',
        controllerAs: 'main'
      })
      .when('/promotion', {
        templateUrl: 'views/promotion/index.html',
        controller: 'PromotionCtrl'
      })
      .when('/promotion/points', {
        templateUrl: 'views/promotion/points.html',
        controller: 'PointsExchangeCtrl'
      })
      .when('/promotion/snapup', {
        templateUrl: 'views/promotion/snapup.html',
        controller: 'SnapupCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  });

