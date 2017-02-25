'use strict';

describe('Controller Tests', function() {

    describe('PotentialEventDate Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPotentialEventDate, MockUser, MockEvent;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPotentialEventDate = jasmine.createSpy('MockPotentialEventDate');
            MockUser = jasmine.createSpy('MockUser');
            MockEvent = jasmine.createSpy('MockEvent');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PotentialEventDate': MockPotentialEventDate,
                'User': MockUser,
                'Event': MockEvent
            };
            createController = function() {
                $injector.get('$controller')("PotentialEventDateDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'groupdateplannerApp:potentialEventDateUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
