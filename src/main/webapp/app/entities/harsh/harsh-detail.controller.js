(function() {
    'use strict';

    angular
        .module('gApp')
        .controller('HarshDetailController', HarshDetailController);

    HarshDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Harsh'];

    function HarshDetailController($scope, $rootScope, $stateParams, previousState, entity, Harsh) {
        var vm = this;

        vm.harsh = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('gApp:harshUpdate', function(event, result) {
            vm.harsh = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
