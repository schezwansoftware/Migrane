(function() {
    'use strict';

    angular
        .module('gApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('doctor', {
            parent: 'app',
            url: '/doctor',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/doctor/doctor.html',
                    controller: 'DoctorController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('doctor');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
