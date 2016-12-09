/**
 * Created by msahel on 12/1/2016.
 */
'use strict';
var mainApp=angular.module('JIRARptApp',['ngRoute']);

mainApp.config(['$routeProvider','$httpProvider', function ($routeProvider, $httpProvider) {
    $routeProvider.
    when('/reports',{
        templateUrl:'reports',
        controller: DataController
    }).
    when('/reports/:track',{
        templateUrl:'reports',
        controller: DataController
    }).
    when('/reports/:track/:fieldIndicator/:criteria',{
        templateUrl:'reports',
        controller: DataController
    }).
    when('/statistics',{
        templateUrl: 'statistics',
        controller: StatsController
    }).
    when('/refreshDashboard',{
        templateUrl:'reports',
        controller:DataController
    }).
    otherwise('/BMS');
}]).filter('searchFilter',function () {
    return function (arr,key) {
        if(!key){
            return arr;
        }
        var returnArr=[];
        key=key.toLowerCase();
        angular.forEach(arr,function (jsonArray) {
            if(jsonArray['DES Track']!=null && jsonArray['DES Track'].toLowerCase().indexOf(key) !==-1){
                returnArr.push(jsonArray);
            }
            if(jsonArray['Key']!=null && jsonArray['Key'].toLowerCase().indexOf(key) !==-1){
                returnArr.push(jsonArray);
            }
        });
        return returnArr;
    }

}).service('FetchJSONDataService',['$http','$q',function ($http,$q) {
    var deferred=$q.defer();

    this.fetchJSONData=function () {
        $http.get('jsonData').success(function (response) {
            deferred.resolve(response);
        }).error(function (message) {
            deferred.reject(message);
        });
        return deferred.promise;
    };
}]);