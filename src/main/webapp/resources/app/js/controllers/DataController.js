/**
 * Created by msahel on 8/15/2016.
 */
var DataController= function ($scope,$http,$routeParams) {
   // $scope.boArray=[];
    //alert($routeParams.track);
   // console.log('data:'+$scope.defect.track);
    //{fieldIndicator}/{criteria}
    if ($routeParams.criteria!=null) {
        var url='jsonData/'+$routeParams.track+'/'+$routeParams.fieldIndicator+'/'+$routeParams.criteria;
        //alert(url);
        $http.get('jsonData/'+$routeParams.track+'/'+$routeParams.fieldIndicator+'/'+$routeParams.criteria).success(function (response) {
            $scope.jsonArray = response;
        });
    }
    else if ($routeParams.track!=null) {
        $http.get('jsonData/'+$routeParams.track).success(function (response) {
            $scope.jsonArray = response;
        });
    }
    else{
        $http.get('jsonData').success(function (response) {
            $scope.jsonArray = response;
        });
    }
    $scope.logout = function()
    {
        alert("hii");
    }
    // $http.get('jsonData').success(function (response) {
    //     $scope.jsonArray = response;
    // });

    
}