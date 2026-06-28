kubectl apply -f .\k8s\01_configuration.yaml
kubectl apply -f .\k8s\03_database.yaml
kubectl apply -f .\k8s\04_backend.yaml
kubectl apply -f .\k8s\02_frontend.yaml
kubectl apply -f .\k8s\05_ingress.yaml