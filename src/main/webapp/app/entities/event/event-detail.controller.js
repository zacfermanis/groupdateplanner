(function() {
    'use strict';

    angular
        .module('groupdateplannerApp')
        .controller('EventDetailController', EventDetailController);

    EventDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Event', 'User', 'Location', 'PotentialEventDate'];

    function EventDetailController($scope, $rootScope, $stateParams, previousState, entity, Event, User, Location, PotentialEventDate) {
        var vm = this;

        vm.event = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('groupdateplannerApp:eventUpdate', function(event, result) {
            vm.event = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
