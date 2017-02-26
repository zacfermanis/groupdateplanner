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
        .controller('NewEventFlowStep2Controller', NewEventFlowStep2Controller);

    NewEventFlowStep2Controller.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'newLoc', 'Event', 'User', 'Location'];

    function NewEventFlowStep2Controller ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, newLoc, Event, User, Location) {
        var vm = this;

        vm.event = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();
        vm.locations = Location.query({filter: 'event-is-null'});
        vm.location = newLoc;
        console.log(vm.event.title);

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            vm.location.event = vm.event;
            if (vm.location.id !== null) {
                Location.update(vm.location, onLocationSaveSuccess, onSaveError);
            } else {
                Location.save(vm.location, onLocationSaveSuccess, onSaveError);
            }
        }

        function onLocationSaveSuccess (result) {
            $scope.$emit('groupdateplannerApp:locationUpdate', result);
            vm.event.location = result;
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
