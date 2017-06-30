(function() {
    'use strict';

    angular
        .module('gApp')
        .controller('EmpDetailController', EmpDetailController);

    EmpDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Emp'];

    function EmpDetailController($scope, $rootScope, $stateParams, previousState, entity, Emp) {
        var vm = this;

        vm.emp = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('gApp:empUpdate', function(event, result) {
            vm.emp = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
