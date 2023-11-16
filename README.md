# PFCTI Weather API

## Construir y ejecutar la imagen de Docker
```shell
  docker build -t <NOMBRE_IMAGEN> .
  docker run -p 8080:8080 <NOMBRE_IMAGEN>
```

## Crear despliegue en Kubernetes
```shell
  kubectl apply -f deployment.yaml
  kubectl apply -f service.yaml
```

## Acceder a la aplicación
Esperamos a que se asigne la direccion IP externo al servicio
```shell
  kubectl get svc <namespace_app>
```
Una vez que la IP externa esté disponible, acceda a la aplicación utilizando la dirección IP y el puerto 80 de su navegador o cualquier cliente HTTP.