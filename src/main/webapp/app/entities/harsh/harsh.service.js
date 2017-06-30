(function() {
    'use strict';
    angular
        .module('gApp')
        .factory('Harsh', Harsh);

    Harsh.$inject = ['$resource'];

    function Harsh ($resource) {
        var resourceUrl =  'api/harshes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
