(function() {
    'use strict';

    angular
        .module('gApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('ar', {
            parent: 'entity',
            url: '/ar',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gApp.ar.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ar/ars.html',
                    controller: 'ArController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ar');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('ar-detail', {
            parent: 'ar',
            url: '/ar/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gApp.ar.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ar/ar-detail.html',
                    controller: 'ArDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ar');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Ar', function($stateParams, Ar) {
                    return Ar.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'ar',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('ar-detail.edit', {
            parent: 'ar-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ar/ar-dialog.html',
                    controller: 'ArDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ar', function(Ar) {
                            return Ar.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ar.new', {
            parent: 'ar',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ar/ar-dialog.html',
                    controller: 'ArDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('ar', null, { reload: 'ar' });
                }, function() {
                    $state.go('ar');
                });
            }]
        })
        .state('ar.edit', {
            parent: 'ar',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ar/ar-dialog.html',
                    controller: 'ArDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ar', function(Ar) {
                            return Ar.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ar', null, { reload: 'ar' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ar.delete', {
            parent: 'ar',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ar/ar-delete-dialog.html',
                    controller: 'ArDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Ar', function(Ar) {
                            return Ar.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ar', null, { reload: 'ar' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
