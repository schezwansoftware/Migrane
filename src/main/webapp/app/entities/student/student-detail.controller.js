(function() {
    'use strict';

    angular
        .module('gApp')
        .controller('StudentDetailController', StudentDetailController);

    StudentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Student'];

    function StudentDetailController($scope, $rootScope, $stateParams, previousState, entity, Student) {
        var vm = this;

        vm.student = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('gApp:studentUpdate', function(event, result) {
            vm.student = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
