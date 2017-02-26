/**
 * Created by n0198863 on 2/26/17.
 */
(function() {
    'use strict';
    angular
        .module('groupdateplannerApp')
        .factory('Submit', Submit);

    Submit.$inject = ['$resource', 'DateUtils'];

    function Submit ($resource, DateUtils) {
        var resourceUrl =  'api/events/submit';

        return $resource(resourceUrl, {}, {
            'submit': { method: 'POST'}
        });
    }
})();
