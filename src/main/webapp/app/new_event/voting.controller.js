/**
 * Created by n0198863 on 2/26/17.
 */
/**
 * Created by n0198863 on 2/25/17.
 */
(function() {
    'use strict';

    angular
        .module('groupdateplannerApp')
        .controller('VotingController', VotingController);

    VotingController.$inject = ['$timeout', '$scope', 'Principal', '$stateParams', '$state', '$uibModalInstance', '$q', 'entity', 'Event', 'User', 'Location', 'PotentialEventDate', '$http'];

    function VotingController ($timeout, $scope, Principal, $stateParams, $state, $uibModalInstance, $q, entity, Event, User, Location, PotentialEventDate, $http) {
        var vm = this;

        vm.event = entity;
        vm.potentialeventdates = vm.event.potentialEventDates;
        vm.location = vm.event.location;

        vm.clear = clear;
        vm.save = save;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            angular.forEach(vm.votedDates, function(value, key) {
                console.log(key + ': ' + value);
                Principal.identity().then(function(account) {
                    $http({
                        method: 'POST',
                        url: '/api/potential-event-dates/' + value.id + '/user/' + account.id
                    }).then(function successCallback(response) {
                        console.log("Vote Recorded.");
                        $uibModalInstance.close(response);

                    }, function errorCallback(error){
                        console.log(error);
                    });
                });
            });
            vm.isSaving = false;
        }
    }
})();
