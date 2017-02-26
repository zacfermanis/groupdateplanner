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
                    $state.go('new_event-flow3', {event : result, savedLoc : result.location});
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
                            return $stateParams.savedLoc;
                        }
                    }
                }).result.then(function(result) {
                    $state.go('new_event-flow4', {event : result, savedLoc : result.location});
                }, function() {
                    $state.go('home');
                });
            }]
        })
        .state('new_event-flow4', {
            parent: 'app',
            url: '/new_event/step_4',
            params: {
                event: "",
                savedLoc: ""
            },
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/new_event/step_4.html',
                    controller: 'NewEventFlowStep4Controller',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function() {
                            return $stateParams.event;
                        },
                        savedLoc: function() {
                            return $stateParams.savedLoc;
                        },
                        potentialEventDate: function() {
                            return {
                                startDate: null,
                                endDate: null,
                                totalAccepted: null,
                                totalInvited: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function(result) {
                    $state.go('new_event-flow5', {event : result, savedLoc : result.location, potentialEventDate : $stateParams.potentialEventDate});
                }, function() {
                    $state.go('home');
                });
            }]
        })
        .state('new_event-flow5', {
            parent: 'app',
            url: '/new_event/step_5',
            params: {
                event: "",
                savedLoc: ""
            },
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/new_event/step_5.html',
                    controller: 'NewEventFlowStep5Controller',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function() {
                            return $stateParams.event;
                        },
                        savedLoc: function() {
                            return $stateParams.savedLoc;
                        },
                        potentialEventDate: function() {
                            return {
                                startDate: null,
                                endDate: null,
                                totalAccepted: null,
                                totalInvited: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function(result) {
                    $state.go('home');
                }, function() {
                    $state.go('home');
                });
            }]
        })
        .state('new_event-voting', {
            parent: 'app',
            url: '/event/{id}/availability',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Event Availability'
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/new_event/voting.html',
                    controller: 'VotingController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Event', function(Event) {
                            return Event.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function(result) {
                    $state.go('scored' , { id : $stateParams.id});
                }, function() {
                    $state.go('home');
                });
            }]
        })
        .state('scoring', {
            parent: 'app',
            url: '/event/{id}/scoring',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Event Status'
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/new_event/scoring.html',
                    controller: 'ScoringController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Event', function(Event) {
                            return Event.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function(result) {
                    $state.go('home');
                }, function() {
                    $state.go('home');
                });
            }]
        })
        .state('scored', {
            parent: 'app',
            url: '/event/{id}/scored',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Event Availability'
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/new_event/scored.html',
                    controller: 'ScoredController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Event', function(Event) {
                            return Event.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function(result) {
                    $state.go('home');
                }, function() {
                    $state.go('home');
                });
            }]
        });
    }
})();
