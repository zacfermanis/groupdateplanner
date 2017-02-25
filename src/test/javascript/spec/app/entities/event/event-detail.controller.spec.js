'use strict';

describe('Controller Tests', function() {

    describe('Event Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEvent, MockUser, MockLocation, MockPotentialEventDate;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEvent = jasmine.createSpy('MockEvent');
            MockUser = jasmine.createSpy('MockUser');
            MockLocation = jasmine.createSpy('MockLocation');
            MockPotentialEventDate = jasmine.createSpy('MockPotentialEventDate');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Event': MockEvent,
                'User': MockUser,
                'Location': MockLocation,
                'PotentialEventDate': MockPotentialEventDate
            };
            createController = function() {
                $injector.get('$controller')("EventDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'groupdateplannerApp:eventUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
