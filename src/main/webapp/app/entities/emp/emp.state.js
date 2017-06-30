(function() {
    'use strict';

    angular
        .module('gApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('emp', {
            parent: 'entity',
            url: '/emp',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gApp.emp.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/emp/emps.html',
                    controller: 'EmpController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('emp');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('emp-detail', {
            parent: 'emp',
            url: '/emp/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gApp.emp.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/emp/emp-detail.html',
                    controller: 'EmpDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('emp');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Emp', function($stateParams, Emp) {
                    return Emp.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'emp',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('emp-detail.edit', {
            parent: 'emp-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/emp/emp-dialog.html',
                    controller: 'EmpDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Emp', function(Emp) {
                            return Emp.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('emp.new', {
            parent: 'emp',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/emp/emp-dialog.html',
                    controller: 'EmpDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                age: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('emp', null, { reload: 'emp' });
                }, function() {
                    $state.go('emp');
                });
            }]
        })
        .state('emp.edit', {
            parent: 'emp',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/emp/emp-dialog.html',
                    controller: 'EmpDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Emp', function(Emp) {
                            return Emp.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('emp', null, { reload: 'emp' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('emp.delete', {
            parent: 'emp',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/emp/emp-delete-dialog.html',
                    controller: 'EmpDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Emp', function(Emp) {
                            return Emp.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('emp', null, { reload: 'emp' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
