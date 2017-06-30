(function() {
    'use strict';

    angular
        .module('gApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('harsh', {
            parent: 'entity',
            url: '/harsh',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gApp.harsh.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/harsh/harshes.html',
                    controller: 'HarshController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('harsh');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('harsh-detail', {
            parent: 'harsh',
            url: '/harsh/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gApp.harsh.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/harsh/harsh-detail.html',
                    controller: 'HarshDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('harsh');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Harsh', function($stateParams, Harsh) {
                    return Harsh.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'harsh',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('harsh-detail.edit', {
            parent: 'harsh-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/harsh/harsh-dialog.html',
                    controller: 'HarshDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Harsh', function(Harsh) {
                            return Harsh.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('harsh.new', {
            parent: 'harsh',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/harsh/harsh-dialog.html',
                    controller: 'HarshDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                arc: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('harsh', null, { reload: 'harsh' });
                }, function() {
                    $state.go('harsh');
                });
            }]
        })
        .state('harsh.edit', {
            parent: 'harsh',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/harsh/harsh-dialog.html',
                    controller: 'HarshDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Harsh', function(Harsh) {
                            return Harsh.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('harsh', null, { reload: 'harsh' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('harsh.delete', {
            parent: 'harsh',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/harsh/harsh-delete-dialog.html',
                    controller: 'HarshDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Harsh', function(Harsh) {
                            return Harsh.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('harsh', null, { reload: 'harsh' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
