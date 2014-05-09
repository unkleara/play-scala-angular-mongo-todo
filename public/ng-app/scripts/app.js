'use strict';
var app = angular.module("app", ["ngRoute", "ngResource"])
    .constant("apiUrl", "http://locahost:9000/api")
    .config(["$routeProvider", function($routeProvider) {
    		$routeProvider.when('/', {
    			controller: 'TodoCtrl',
    			templateUrl: '/assets/ng-app/views/main.html'
    		}).otherwise({
    			redirectTo: '/'
    		})
    		}])
    .config(["$locationProvider", function($locationProvider) {
		return $locationProvider.html5Mode(true).hashPrefix("!");
	}
]);

app.controller("TodoCtrl", ["$scope", function($scope) {
    $scope.appHeader = "Todos";
    $scope.newTodo = {};
    $scope.todos = [];
    $scope.create = function() {
        var todo = $scope.newTodo;
        $scope.todos.push(todo);
        $scope.newTodo = {};
    };

    $scope.delete = function(idx) {
        console.log(idx);
        $scope.todos.splice(idx, true);
    };
}]);