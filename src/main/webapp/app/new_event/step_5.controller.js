/**
 * Created by n0198863 on 2/25/17.
 */
/**
 * Created by n0198863 on 2/25/17.
 */
(function() {
    'use strict';

    angular
        .module('groupdateplannerApp')
        .controller('NewEventFlowStep5Controller', NewEventFlowStep5Controller);

    NewEventFlowStep5Controller.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'savedLoc', 'potentialEventDate', 'Event', 'User', 'Location', 'PotentialEventDate', 'Submit'];

    function NewEventFlowStep5Controller ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, savedLoc, potentialEventDate, Event, User, Location, PotentialEventDate, Submit) {
        var vm = this;

        vm.event = entity;
        vm.clear = clear;
        vm.location = entity.location;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

    }
})();
