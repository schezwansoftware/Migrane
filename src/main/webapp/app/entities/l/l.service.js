(function() {
    'use strict';
    angular
        .module('gApp')
        .factory('L', L);

    L.$inject = ['$resource'];

    function L ($resource) {
        var resourceUrl =  'api/ls/:id';

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
