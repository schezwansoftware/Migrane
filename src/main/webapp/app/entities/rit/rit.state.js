(function() {
    'use strict';

    angular
        .module('gApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('rit', {
            parent: 'entity',
            url: '/rit',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gApp.rit.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/rit/rits.html',
                    controller: 'RitController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('rit');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('rit-detail', {
            parent: 'rit',
            url: '/rit/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gApp.rit.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/rit/rit-detail.html',
                    controller: 'RitDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('rit');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Rit', function($stateParams, Rit) {
                    return Rit.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'rit',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('rit-detail.edit', {
            parent: 'rit-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rit/rit-dialog.html',
                    controller: 'RitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Rit', function(Rit) {
                            return Rit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('rit.new', {
            parent: 'rit',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rit/rit-dialog.html',
                    controller: 'RitDialogController',
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
                    $state.go('rit', null, { reload: 'rit' });
                }, function() {
                    $state.go('rit');
                });
            }]
        })
        .state('rit.edit', {
            parent: 'rit',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rit/rit-dialog.html',
                    controller: 'RitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Rit', function(Rit) {
                            return Rit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('rit', null, { reload: 'rit' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('rit.delete', {
            parent: 'rit',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rit/rit-delete-dialog.html',
                    controller: 'RitDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Rit', function(Rit) {
                            return Rit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('rit', null, { reload: 'rit' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
