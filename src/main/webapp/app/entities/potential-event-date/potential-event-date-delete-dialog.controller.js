(function() {
    'use strict';

    angular
        .module('groupdateplannerApp')
        .controller('PotentialEventDateDeleteController',PotentialEventDateDeleteController);

    PotentialEventDateDeleteController.$inject = ['$uibModalInstance', 'entity', 'PotentialEventDate'];

    function PotentialEventDateDeleteController($uibModalInstance, entity, PotentialEventDate) {
        var vm = this;

        vm.potentialEventDate = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PotentialEventDate.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
