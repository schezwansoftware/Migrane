(function() {
    'use strict';

    angular
        .module('gApp')
        .controller('LDetailController', LDetailController);

    LDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'L'];

    function LDetailController($scope, $rootScope, $stateParams, previousState, entity, L) {
        var vm = this;

        vm.l = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('gApp:lUpdate', function(event, result) {
            vm.l = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
