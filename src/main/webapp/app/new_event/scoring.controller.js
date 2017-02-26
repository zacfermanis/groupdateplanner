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
        .controller('ScoringController', ScoringController);

    ScoringController.$inject = ['$timeout', '$scope', 'Principal', '$stateParams', '$state', '$uibModalInstance', 'entity'];

    function ScoringController ($timeout, $scope, Principal, $stateParams, $state, $uibModalInstance, entity) {
        var vm = this;

        vm.event = entity;
        vm.potentialeventdates = vm.event.potentialEventDates;

        // angular.forEach(vm.potentialeventdates, function(date) {
        //     console.log(date);
        //     if(date != undefined) {
        //         if(date.acceptedUsers != null) {
        //             var keys = Object.keys(date.acceptedUsers);
        //             var len = keys.length;
        //             date.totalAccepted = len;
        //         } else {
        //             date.totalAccepted = 0;
        //         }
        //     }
        // });
        vm.location = vm.event.location;

        vm.clear = clear;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }
    }
})();
