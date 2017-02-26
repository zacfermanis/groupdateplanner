/**
 * Created by n0198863 on 2/26/17.
 */
(function() {
    'use strict';
    angular
        .module('groupdateplannerApp')
        .factory('Voting', Voting);

    Voting.$inject = ['$resource', 'DateUtils'];

    function Voting ($resource, DateUtils) {
        var resourceUrl =  '/potential-event-dates/:id/user/:userId';

        return $resource(resourceUrl, {}, {
            'vote': { method: 'POST'}
        });
    }
})();
