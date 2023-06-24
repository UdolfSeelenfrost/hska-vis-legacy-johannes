[![MIT license](http://img.shields.io/badge/license-MIT-brightgreen.svg)](http://opensource.org/licenses/MIT)
[![Travis Build Status](https://travis-ci.org/mavogel/hska-vis-legacy.svg?branch=master)](https://travis-ci.org/mavogel/hska-vis-legacy)

# Distributed Information Systems Laboratory aka vis-lab
This project is the quick setup of the legacy webshop of 
the masters course 'Distributed Information Systems' at the University of Applied Sciences (Karlsruhe).

## Table of Contents
- [Prerequisites](#prerequisites)
- [Usage](#usage)
    - [Quick Start](#quick-start)
    - [Build it on your own](#build-it-on-your-own)
    - [Istio Setup](#istio)
- [Database cleanup](#database-cleanup)
- [License](#license)

## <a name="prerequisites"></a>Prerequisites
- [docker](https://docker.com)
- with `docker-compose`

## <a name="usage"></a>Usage
You can run the images from `docker hub` which is preferred or built it on your own.
First: Start Docker daemon and check with `docker ps`

### <a name="quick-start"></a>Quick Start (docker-hub)
- Copy the `docker-compose.yml` locally in a desired folder and run
```bash
$ docker-compose up -d
# to follow the logs
$ docker-compose logs -tf
# to shutdown
$ docker-compose down
```

### <a name="built-it-on-your-own"></a>Build it on your own
- Run `docker-compose -f docker-compose-local.yml up -d` which will
    - It builds the web app `war` in a staged build, packs it into a docker tomcat8 container,
    and sets the user `tomcat` with password `admin` for the Management Console at http://localhost:8888/
    - Initializes the MySQL Database docker container with the db user defined in `hibernate.cfg.xml`
    - Sets up both containers and make the legacy webshop available under http://localhost:8888/EShop-1.0.0
- Follow the logs via `docker-compose -f docker-compose-local.yml logs -tf`
- To shutdown the containers run `docker-compose -f docker-compose-local.yml down`

### <a name="istio"></a>Istio Setup

* Start Docker Desktop, and start minikube with `minikube start`
* Install [Istio](https://istio.io/latest/docs/setup/getting-started/)
* Make sure that the Istio containers are automatically injected when the pods are started: `kubectl label namespace default istio-injection=enabled`
* Install the Istio addons (Kiali, Prometheus, Grafana). In the subdirectory `istio-addons`. Apply them with `kubectl apply -f  istio-addons`.
* Start the Webshop application:
* - Apply the microservice.yml file, which creates deployments and services: `kubectl apply -f microservices.yml`
* - Check that the pods are running and available (Ready 2/2): `kubectl get pods` 
* - Port forward to access the Webshop from your local browser: `kubectl port-forward service/legacywebshop 8888:8888`
* - Open your browser `http://localhost:8888/EShop-1.0.0/` to access the webshop. Use admin/admin to log in.

## Util
* Istio Dashboards are opened with `istioctl dashboard {serviceName}` e.g. "istioctl dashboard grafana"
+ Apache Proxy forward with `kubectl port-forward service/apache 8888:80`
+ Apache test requests are on url `http://localhost:8888/categories`
## <a name="database-cleanup"></a>Database Cleanup
If you change the user and password of the MySQL database, you should run
```bash
$ docker-compose rm -v
$ rm -rf .data
```
Details can be found [here](https://github.com/docker-library/mysql/issues/51)

## <a name="license"></a>License
Copyright (c) 2017-2018 Manuel Vogel
Source code is open source and released under the MIT license.
