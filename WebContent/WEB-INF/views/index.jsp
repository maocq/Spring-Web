<!DOCTYPE html>
<html lang="es" ng-app="MyApp">
<head>
  <meta charset="UTF-8">
  <title>Document</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.2/angular.min.js"></script>
  <script src="res/js/controller.js"></script>
</head>
<body>
  <br><br><br><br><br>
  <div class="container" ng-controller="IndexController">

    <div class="col-md-6">
        <form>
          <div class="form-group">
            <label for="name">Nombre:</label>
            <input type="text" class="form-control" id="name" ng-model="newUsuario.name">
          </div>
          <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" class="form-control" id="email" ng-model="newUsuario.email">
          </div>
          <button class="btn btn-default" ng-click="addUsuario()">Submit</button>
        </form>
    </div>
    <div class="col-md-6">

      <ul class="media-list">
        <li class="media" ng-repeat="usuario in usuarios">
          <div class="media-left">
              <img class="media-object" src="http://placehold.it/64x64">
          </div>
          <div class="media-body">
            <h4 class="media-heading">{{usuario.name}}</h4>
            <p>{{usuario.email}} <small>({{usuario.id}})</small></p>
          </div>
        </li>
      </ul>

    </div>

  </div>
</body>
</html>