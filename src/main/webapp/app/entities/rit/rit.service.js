(function() {
    'use strict';
    angular
        .module('gApp')
        .factory('Rit', Rit);

    Rit.$inject = ['$resource'];

    function Rit ($resource) {
        var resourceUrl =  'api/rits/:id';

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
