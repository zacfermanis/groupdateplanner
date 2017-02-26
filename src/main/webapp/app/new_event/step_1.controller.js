/**
 * Created by n0198863 on 2/25/17.
 */
(function() {
    'use strict';

    angular
        .module('groupdateplannerApp')
        .controller('NewEventFlowStep1Controller', NewEventFlowStep1Controller);

    NewEventFlowStep1Controller.$inject = ['$timeout', '$scope', '$stateParams', '$state', '$uibModalInstance', '$q', 'entity', 'Event', 'User', 'Location', 'PotentialEventDate'];

    function NewEventFlowStep1Controller ($timeout, $scope, $stateParams, $state, $uibModalInstance, $q, entity, Event, User, Location, PotentialEventDate) {
        var vm = this;

        vm.event = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();
        vm.locations = Location.query({filter: 'event-is-null'});
        $q.all([vm.event.$promise, vm.locations.$promise]).then(function() {
            if (!vm.event.location || !vm.event.location.id) {
                return $q.reject();
            }
            return Location.get({id : vm.event.location.id}).$promise;
        }).then(function(location) {
            vm.locations.push(location);
        });
        vm.potentialeventdates = PotentialEventDate.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.event.id !== null) {
                Event.update(vm.event, onSaveSuccess, onSaveError);
            } else {
                Event.save(vm.event, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('groupdateplannerApp:eventUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.selectedStartDate = false;
        vm.datePickerOpenStatus.selectedEndDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
