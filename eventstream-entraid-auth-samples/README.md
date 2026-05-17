# Sample code for Eventstream Custom Endpoint using Entra ID authentication

## Prerequisites

### Install Java and Maven

``` powershell
# https://scoop.sh/
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
Invoke-RestMethod -Uri https://get.scoop.sh | Invoke-Expression

scoop bucket add java
scoop install oraclejdk-lts # The latest LTS is Java 21
scoop install maven
```

### Build the project

``` powershell
mvn clean package
```

### Prepare the environment variables for a service principal using a certificate

``` powershell
$env:AZURE_CLIENT_ID="aaaaaaaa-0000-1111-2222-bbbbbbbbbbbb"
$env:AZURE_TENANT_ID="bbbb1111-cc22-3333-44dd-555555eeeeee"
$env:AZURE_CLIENT_CERTIFICATE_PATH="C:\Users\Contoso\Downloads\xxxxxxxx.pfx"
```

### Run the producer and consumer

``` powershell
java -cp .\target\EventHubOAuth-1.0-SNAPSHOT-jar-with-dependencies.jar com.microsoft.ProducerSample
java -cp .\target\EventHubOAuth-1.0-SNAPSHOT-jar-with-dependencies.jar com.microsoft.ConsumerSample
```
