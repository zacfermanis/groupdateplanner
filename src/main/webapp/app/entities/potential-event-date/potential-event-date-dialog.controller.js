(function() {
    'use strict';

    angular
        .module('groupdateplannerApp')
        .controller('PotentialEventDateDialogController', PotentialEventDateDialogController);

    PotentialEventDateDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PotentialEventDate', 'User', 'Event'];

    function PotentialEventDateDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PotentialEventDate, User, Event) {
        var vm = this;

        vm.potentialEventDate = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();
        vm.events = Event.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.potentialEventDate.id !== null) {
                PotentialEventDate.update(vm.potentialEventDate, onSaveSuccess, onSaveError);
            } else {
                PotentialEventDate.save(vm.potentialEventDate, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('groupdateplannerApp:potentialEventDateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.startDate = false;
        vm.datePickerOpenStatus.endDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
