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
        .controller('NewEventFlowStep4Controller', NewEventFlowStep4Controller);

    NewEventFlowStep4Controller.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'savedLoc', 'potentialEventDate', 'Event', 'User', 'Location', 'PotentialEventDate', 'Submit'];

    function NewEventFlowStep4Controller ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, savedLoc, potentialEventDate, Event, User, Location, PotentialEventDate, Submit) {
        var vm = this;

        vm.event = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();
        vm.locations = Location.query({filter: 'event-is-null'});
        vm.location = savedLoc;
        console.log(vm.event.title);
        vm.potentialEventDate = potentialEventDate;
        vm.addDate = addDate;
        vm.isSaving = true;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
            vm.isSaving = false;
        }

        function save () {
            if (vm.event.id !== null) {
                Event.update(vm.event, onSaveSuccess, onSaveError);
            } else {
                Event.save(vm.event, onSaveSuccess, onSaveError);
            }
        }

        function addDate() {
            vm.isSaving = true;
            vm.potentialEventDate.event = vm.event;
            if (vm.potentialEventDate.id !== null){
                PotentialEventDate.update(vm.potentialEventDate, onDateSaveSuccess, onSaveError);
            } else {
                PotentialEventDate.save(vm.potentialEventDate, onDateSaveSuccess, onSaveError);
            }
        }

        function onDateSaveSuccess (result) {
            $scope.$emit('groupdateplannerApp:eventUpdate', result);
            vm.event.potentialEventDates.push(result);
            vm.isSaving = false;
        }

        function onSaveSuccess (result) {
            $scope.$emit('groupdateplannerApp:eventUpdate', result);
            Submit.submit(vm.event, onSubmitSuccess, onSaveError);
        }

        function onSubmitSuccess(result) {
            $scope.$emit('groupdateplannerApp:submitUpdate', result);
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
