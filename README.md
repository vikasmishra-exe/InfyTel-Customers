# InfyTel-Customers
A microservice for accessing Customers Details For InfyTel
This Microservice is a part of InfyTel Application which includes below microservices:-
1. InfyTel-CallDetails
2. InfyTel-Friends
3. InfyTel-Plan
4. InfyTel-Gateway

This InfyTel application based on Spring Cloud Technologies Such as:

a. Spring Cloud Consul for service discovery
----------------------------------------
b. Spring Cloud Load Balancer
----------------------------------------
c. Resilience4J for fault tolerance
----------------------------------------
d. Spring Cloud Gateway
----------------------------------------
e. Prometheus For monitoring.
----------------------------------------


You need to install below two software in order to run the InfyTel applications.
1. Consul
   --------------
   Open the command prompt window from the folder and start the consul server using the command as below:
    ```sh
   consul agent -server -bootstrap-expect=1 -data-dir=consul-data2 -ui -bind=192.168.100.13
   ```
    <p>
      <b>-server</b>: it is acting as consul server </br>
      <b>-bootstrap-expect=1</b>: expecting only one server instance. No cluster. </br>
      <b>-data-dir</b>: creates a folder with this name, where the consul data must be stored. </br>
      <b>-bind=192.168.100.13</b>:  ipv4 address of the machine where the consul server is running
    </p>
    
Login to localhost:8500/ui the port where consul will run by default
------------------------------------------------------------------------
3. Prometheus
   ------------------
   <ul>
     <li>Download ​​​​​​​the Prometheus based on the software requirements from <a href="https://prometheus.io/download/">Prometheus</a></li>
     <li>Extract the zip file. It contains a Prometheus.exe file and a Prometheus.yml file.</li>
     <li>Run the Prometheus.exe, it will use the Prometheus.yml file config data to listen to the microservices and extract the metrics data. And finally shows the metrics information in the dashboard.</li>
     <li>Modify the Prometheus.yml file, to extract the metrics of the microservices and to show them in the dashboard.</li>
   </ul>
   <p>Sample config data in Prometheus.yml file:</p>
   
```
#Global configurations
global:
  scrape_interval:     5s # Set the scrape interval to every 5 seconds.
  evaluation_interval: 5s # Evaluate rules every 5 seconds.
scrape_configs:
  - job_name: 'PlanMS'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['localhost:8400']
  - job_name: 'FriendFamilyMS'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['localhost:9300']
  - job_name: 'CustomerMS'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['localhost:8100']
```
