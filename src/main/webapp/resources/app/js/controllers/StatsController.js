/**
 * Created by msahel on 8/15/2016.
 */
var StatsController= function ($scope,$http,FetchJSONDataService) {
    $scope.boArray=[];
    $scope.foArray=[];
    $scope.coArray=[];
    $scope.inArray=[];
    $scope.edbcArray=[];
    $scope.spArray=[];
    $scope.collection=[];
    $scope.defects=[];
    $scope.defect={trackName:'',sev5Defects: '', sev4Defects: '', sev3Defects: '',sev2Defects: '',noOfInProgressDefects: '',noOfAssignedDefects: '',noOfRLTDefects: '',noOfRTTDefects: '',noOfDevCompleteDefects: '',noOfNewDefects: ''};

    var promise=FetchJSONDataService.fetchJSONData();

    promise.then(function (response) {
            $scope.jsonArray = response;
            $scope.collection.push(response);

            var boArray = [];
            var foArray = [];
            var coArray = [];
            var spArray = [];
            var edbcArray = [];
            var inArray = [];
            var rpArray = [];

            for (var i = 0, j = response.length; i < j; i++) {
                if(response[i]['DES Track']=='Application Team/Back Office'){
                    boArray.push(response[i]);
                }
                if(response[i]['DES Track']=='Application Team/Correspondence'){
                    coArray.push(response[i]);
                }
                if(response[i]['DES Track']=='Application Team/EDBC'){
                    edbcArray.push(response[i]);
                }
                if(response[i]['DES Track']=='Application Team/Front Office'){
                    foArray.push(response[i]);
                }
                if(response[i]['DES Track']=='Application Team/Interfaces'){
                    inArray.push(response[i]);
                }
                if(response[i]['DES Track']=='Application Team/Support'){
                    spArray.push(response[i]);
                }
                if(response[i]['DES Track']=='Application Team/Reports'){
                    rpArray.push(response[i]);
                }

            }
            //$scope.boArray.push(boArray);

            //  console.log(edbcArray);
            returnStatistics(boArray,'BO');
            returnStatistics(foArray,'FO');
            returnStatistics(spArray,'SP');
            returnStatistics(inArray,'IN');
            returnStatistics(edbcArray,'EDBC');
            returnStatistics(coArray,'CO');
            returnStatistics(rpArray,'RP');

            function returnStatistics(array,trackName){
                var defect={};
                var sev5Defects=0;
                var sev4Defects=0;
                var sev3Defects=0;
                var sev2Defects=0;
                var noOfInProgressDefects=0;
                var noOfAssignedDefects=0;
                var noOfRLTDefects=0;
                var noOfRTTDefects=0;
                var noOfDevCompleteDefects=0;
                var noOfNewDefects=0;


                for (var i = 0, j = array.length; i < j; i++) {

                    //console.log('severity:' +array[i]['Defect Severity']);
                    if(array[i]['Defect Severity']==5){

                        sev5Defects=sev5Defects+1;
                    }
                    if(array[i]['Defect Severity']==4){

                        sev4Defects=sev4Defects+1;
                    }
                    if(array[i]['Defect Severity']==3){
                        sev3Defects=sev3Defects+1;
                    }
                    if(array[i]['Defect Severity']==2){
                        sev2Defects=sev2Defects+1;
                    }

                    if(array[i]['Status'].trim()=='In Progress'){
                        noOfInProgressDefects=noOfInProgressDefects+1;
                    }
                    if(array[i]['Status'].trim()=='Assigned'){
                        noOfAssignedDefects=noOfAssignedDefects+1;
                    }
                    if(array[i]['Status'].trim()=='Development Complete'){
                        noOfDevCompleteDefects=noOfDevCompleteDefects+1;
                    }
                    if(array[i]['Status'].trim()=='Return to Testing'){
                        noOfRTTDefects=noOfRTTDefects+1;
                    }
                    if(array[i]['Status'].trim()=='New'){
                        noOfNewDefects=noOfNewDefects+1;
                    }
                    if(array[i]['Status'].trim()=='Ready for Lead Testing'){
                        noOfRLTDefects=noOfRLTDefects+1;
                    }

                }
                defect.noOfInProgressDefects=noOfInProgressDefects;
                defect.noOfAssignedDefects=noOfAssignedDefects;
                defect.noOfDevCompleteDefects=noOfDevCompleteDefects;
                defect.noOfRTTDefects=noOfRTTDefects;
                defect.noOfNewDefects=noOfNewDefects;
                defect.noOfRLTDefects=noOfRLTDefects;

                defect.sev5Defects=sev5Defects;
                defect.sev4Defects=sev4Defects;
                defect.sev3Defects=sev3Defects;
                defect.sev2Defects=sev2Defects;

                defect.track=trackName;

                $scope.defects.push(defect);
                $scope.defect='';
                // console.log('noOfInProgressDefects:'+noOfInProgressDefects);
                // console.log('noOfAssignedDefects:'+noOfAssignedDefects);
                // console.log('noOfDevCompleteDefects:'+noOfDevCompleteDefects);
                // console.log('noOfRTTDefects:'+noOfRTTDefects);
                // console.log('noOfNewDefects:'+noOfNewDefects);
                // console.log('noOfRLTDefects:'+noOfRLTDefects);
            }
        },
        function (error) {
            alert(error);
            $scope.setError(error);
        });



    
}
