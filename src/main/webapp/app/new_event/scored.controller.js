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
        .controller('ScoredController', ScoredController);

    ScoredController.$inject = ['$timeout', '$scope', 'Principal', '$stateParams', '$state', '$uibModalInstance', 'entity'];

    function ScoredController ($timeout, $scope, Principal, $stateParams, $state, $uibModalInstance, entity) {
        var vm = this;

        vm.event = entity;
        vm.potentialeventdates = vm.event.potentialEventDates;

        vm.location = vm.event.location;

        vm.clear = clear;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }
    }
})();
