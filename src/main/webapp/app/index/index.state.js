(function() {
    'use strict';

    angular
        .module('gApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('index', {
            parent: 'app',
            url: '/index',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/index/index.html',
                    controller: 'IndexController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('index');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
