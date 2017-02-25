(function() {
    'use strict';

    angular
        .module('groupdateplannerApp')
        .controller('PotentialEventDateDetailController', PotentialEventDateDetailController);

    PotentialEventDateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PotentialEventDate', 'User', 'Event'];

    function PotentialEventDateDetailController($scope, $rootScope, $stateParams, previousState, entity, PotentialEventDate, User, Event) {
        var vm = this;

        vm.potentialEventDate = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('groupdateplannerApp:potentialEventDateUpdate', function(event, result) {
            vm.potentialEventDate = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
