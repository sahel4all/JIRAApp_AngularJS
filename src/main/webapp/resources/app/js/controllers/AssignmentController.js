/**
 * Created by msahel on 12/11/2016.
 */
var AssignmentController= function ($scope,$http,$routeParams) {



   if ($routeParams.track!=null) {
      // alert('hiiii');
        $http.get('iedssJsonData/'+$routeParams.track).success(function (response) {
            $scope.jsonArray = response;
        });
    }
}