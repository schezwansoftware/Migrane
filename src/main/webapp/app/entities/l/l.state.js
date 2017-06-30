(function() {
    'use strict';

    angular
        .module('gApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('l', {
            parent: 'entity',
            url: '/l',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gApp.l.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/l/ls.html',
                    controller: 'LController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('l');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('l-detail', {
            parent: 'l',
            url: '/l/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gApp.l.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/l/l-detail.html',
                    controller: 'LDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('l');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'L', function($stateParams, L) {
                    return L.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'l',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('l-detail.edit', {
            parent: 'l-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l/l-dialog.html',
                    controller: 'LDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['L', function(L) {
                            return L.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('l.new', {
            parent: 'l',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l/l-dialog.html',
                    controller: 'LDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                length: null,
                                breadth: null,
                                area: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('l', null, { reload: 'l' });
                }, function() {
                    $state.go('l');
                });
            }]
        })
        .state('l.edit', {
            parent: 'l',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l/l-dialog.html',
                    controller: 'LDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['L', function(L) {
                            return L.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('l', null, { reload: 'l' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('l.delete', {
            parent: 'l',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l/l-delete-dialog.html',
                    controller: 'LDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['L', function(L) {
                            return L.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('l', null, { reload: 'l' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
