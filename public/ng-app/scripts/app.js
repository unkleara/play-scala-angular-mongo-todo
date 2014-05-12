'use strict';
var app = angular.module("app", ["ngRoute", "ngResource"])
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

app.factory("Todo", ["$resource", function($resource) {
    return $resource("/todos/:id", {id: "@id"}, { update: { method: 'PUT' }})
}]);

app.controller("TodoCtrl", ["$scope", "Todo", function($scope, Todo) {

    $scope.appHeader = "Todos";
    $scope.newTodo = {};
    $scope.todos = Todo.query();

    $scope.createTodo = function() {
        Todo.save($scope.newTodo, function(resource) {
        console.log(resource);
        $scope.todos.push(resource);
        $scope.newTodo = {};

        }, function(response) {
            console.log("Error: " + response.error)
        });
    };

    $scope.removeTodo = function(idx, todoId) {
        console.log(todoId.$oid);
        Todo.delete({id: todoId.$oid}, function() {
            $scope.todos.splice(idx, true);
        }, function(response) {
            console.log("Error: " + response.error)
        });
    };
}]);