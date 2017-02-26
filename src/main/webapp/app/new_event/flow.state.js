(function() {
    'use strict';

    angular
        .module('groupdateplannerApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('new_event-flow1', {
            parent: 'app',
            url: '/new_event/step_1',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/new_event/step_1.html',
                    controller: 'NewEventFlowStep1Controller',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                title: null,
                                description: null,
                                selectedStartDate: null,
                                selectedEndDate: null,
                                cost: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function(result) {
                    $state.go('new_event-flow2', { event: result});
                }, function() {
                    $state.go('home');
                });
            }]
        })
        .state('new_event-flow2', {
            parent: 'app',
            url: '/new_event/step_2',
            params: {
                event: ""
            },
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/new_event/step_2.html',
                    controller: 'NewEventFlowStep2Controller',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function() {
                            return $stateParams.event;
                        },
                        newLoc: function() {
                            return {
                                streetAddress1: null,
                                streetAddress2: null,
                                city: null,
                                state: null,
                                zipcode: null,
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function(result) {
                    $state.go('new_event-flow3', {event : result, savedLoc : $stateParams.newLoc});
                }, function() {
                    $state.go('home');
                });
            }]
        })
        .state('new_event-flow3', {
            parent: 'app',
            url: '/new_event/step_3',
            params: {
                event: "",
                savedLoc: ""
            },
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/new_event/step_3.html',
                    controller: 'NewEventFlowStep3Controller',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function() {
                            return $stateParams.event;
                        },
                        savedLoc: function() {
                            return $stateParams.newLoc
                        }
                    }
                }).result.then(function() {
                    $state.go('new_event-flow3');
                }, function() {
                    $state.go('home');
                });
            }]
        })
    }
})();
