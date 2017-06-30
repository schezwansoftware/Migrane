(function() {
    'use strict';
    angular
        .module('gApp')
        .factory('Emp', Emp);

    Emp.$inject = ['$resource'];

    function Emp ($resource) {
        var resourceUrl =  'api/emps/:id';

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
