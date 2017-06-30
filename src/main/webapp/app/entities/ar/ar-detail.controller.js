(function() {
    'use strict';

    angular
        .module('gApp')
        .controller('ArDetailController', ArDetailController);

    ArDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Ar'];

    function ArDetailController($scope, $rootScope, $stateParams, previousState, entity, Ar) {
        var vm = this;

        vm.ar = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('gApp:arUpdate', function(event, result) {
            vm.ar = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
