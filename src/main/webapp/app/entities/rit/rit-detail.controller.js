(function() {
    'use strict';

    angular
        .module('gApp')
        .controller('RitDetailController', RitDetailController);

    RitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Rit'];

    function RitDetailController($scope, $rootScope, $stateParams, previousState, entity, Rit) {
        var vm = this;

        vm.rit = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('gApp:ritUpdate', function(event, result) {
            vm.rit = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
