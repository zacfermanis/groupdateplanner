(function() {
    'use strict';
    angular
        .module('groupdateplannerApp')
        .factory('Event', Event);

    Event.$inject = ['$resource', 'DateUtils'];

    function Event ($resource, DateUtils) {
        var resourceUrl =  'api/events/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.selectedStartDate = DateUtils.convertDateTimeFromServer(data.selectedStartDate);
                        data.selectedEndDate = DateUtils.convertDateTimeFromServer(data.selectedEndDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
