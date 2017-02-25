(function() {
    'use strict';

    angular
        .module('groupdateplannerApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('potential-event-date', {
            parent: 'entity',
            url: '/potential-event-date',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PotentialEventDates'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/potential-event-date/potential-event-dates.html',
                    controller: 'PotentialEventDateController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('potential-event-date-detail', {
            parent: 'potential-event-date',
            url: '/potential-event-date/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PotentialEventDate'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/potential-event-date/potential-event-date-detail.html',
                    controller: 'PotentialEventDateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'PotentialEventDate', function($stateParams, PotentialEventDate) {
                    return PotentialEventDate.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'potential-event-date',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('potential-event-date-detail.edit', {
            parent: 'potential-event-date-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/potential-event-date/potential-event-date-dialog.html',
                    controller: 'PotentialEventDateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PotentialEventDate', function(PotentialEventDate) {
                            return PotentialEventDate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('potential-event-date.new', {
            parent: 'potential-event-date',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/potential-event-date/potential-event-date-dialog.html',
                    controller: 'PotentialEventDateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                startDate: null,
                                endDate: null,
                                totalAccepted: null,
                                totalInvited: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('potential-event-date', null, { reload: 'potential-event-date' });
                }, function() {
                    $state.go('potential-event-date');
                });
            }]
        })
        .state('potential-event-date.edit', {
            parent: 'potential-event-date',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/potential-event-date/potential-event-date-dialog.html',
                    controller: 'PotentialEventDateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PotentialEventDate', function(PotentialEventDate) {
                            return PotentialEventDate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('potential-event-date', null, { reload: 'potential-event-date' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('potential-event-date.delete', {
            parent: 'potential-event-date',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/potential-event-date/potential-event-date-delete-dialog.html',
                    controller: 'PotentialEventDateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PotentialEventDate', function(PotentialEventDate) {
                            return PotentialEventDate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('potential-event-date', null, { reload: 'potential-event-date' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
