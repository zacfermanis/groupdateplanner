(function() {
    'use strict';
    angular
        .module('groupdateplannerApp')
        .factory('PotentialEventDate', PotentialEventDate);

    PotentialEventDate.$inject = ['$resource', 'DateUtils'];

    function PotentialEventDate ($resource, DateUtils) {
        var resourceUrl =  'api/potential-event-dates/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.startDate = DateUtils.convertDateTimeFromServer(data.startDate);
                        data.endDate = DateUtils.convertDateTimeFromServer(data.endDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
