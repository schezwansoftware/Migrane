(function() {
    'use strict';
    angular
        .module('gApp')
        .factory('Ar', Ar);

    Ar.$inject = ['$resource'];

    function Ar ($resource) {
        var resourceUrl =  'api/ars/:id';

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
